import sys
import os
import Ice
import signal
import grpc
from threading import Thread
from time import sleep

sys.path.append(os.path.abspath("./utils/out/proto"))

import exchange_pb2
import exchange_pb2_grpc

sys.path.append(os.path.abspath("./utils/out/ice"))

from BankSystem import *
from currency_rates import currency_rates

def run_exchange_conn(arg):
    channel = grpc.insecure_channel('localhost:50051')
    stub = exchange_pb2_grpc.ExchangeStub(channel)

    request = exchange_pb2.ExchangeRequest(origin_currency=exchange_pb2.PLN, currency_rates=[arg])

    for response in stub.subscribeExchangeRate(request):
        print(currency_rates)
        currency_rates[response.currency] = response.ExchangeRate


class InvalidAccountTypeExceptionI(InvalidAccountTypeException):
    pass


class InvalidCredentialsExceptionI(InvalidCredentialsException):
    pass


class AccountI(Account):
    def __init__(self, accountType, name, surname, pesel, password, income):
        self.accountType = accountType
        self.name = name
        self.surname = surname
        self.pesel = pesel
        self.password = password
        self.income = income
        self.balance = Balance(income.value + 100)

    def getAccountType(self, current):
        return self.accountType

    def getAccountBalance(self, current):
        return self.balance

    def applyForCredit(self, currency, amount, period, current):
        if self.accountType == AccountType.STANDARD:
            raise InvalidAccountTypeExceptionI

        credit_value = currency_rates[currency.value] * amount.value
        return CreditEstimate(amount, Balance(credit_value))


class AccountFactoryI(AccountFactory):
    def createAccount(self, name, surname, pesel, income, current):
        acc_type = AccountType.STANDARD
        if income.value > 1000:
            acc_type = AccountType.PREMIUM
        password = Password("wed3")
        account = AccountI(acc_type, name, surname, pesel, password, income)
        current.adapter.add(account, Ice.stringToIdentity(str(pesel.value)))
        return AccountCreated(password, acc_type)

    def obtainAccess(self, credentials, current):
        # TODO validation bla bla...
        try:
            obj = AccountPrx.checkedCast(current.adapter.createProxy(Ice.stringToIdentity(str(credentials.pesel.value))))
        except Exception:
            raise InvalidCredentialsExceptionI
        else:
            return obj


with Ice.initialize(sys.argv, "./bank/config.server") as communicator:
    if __name__ == "__main__":
        exchange_thread = Thread(target=run_exchange_conn, args=(exchange_pb2.USD, ))
        exchange_thread.start()

    signal.signal(signal.SIGINT, lambda signum, frame: communicator.shutdown())
    adapter = communicator.createObjectAdapter("AccountFactory")
    adapter.add(AccountFactoryI(), Ice.stringToIdentity("accountFactory"))
    adapter.activate()
    communicator.waitForShutdown()
