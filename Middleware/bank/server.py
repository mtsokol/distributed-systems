import sys
import os
import Ice
import signal
import grpc

sys.path.append(os.path.abspath("./utils/out/proto"))

import exchange_pb2
import exchange_pb2_grpc

sys.path.append(os.path.abspath("./utils/out/ice"))

from BankSystem import *

def run():
  channel = grpc.insecure_channel('localhost:50051')
  stub = exchange_pb2_grpc.ExchangeStub(channel)
  for response in stub.subscribeExchangeRate(exchange_pb2.ExchangeRequest()):
    print("Greeter client received: " + str(response.currency))


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
    self.balance = income + 100

  def getAccountType(self, current):
    return self.accountType

  def getAccountBalance(self, current):
    return self.balance

  def applyForCredit(self, currency, amount, period, current):
    if self.accountType == AccountType.STANDARD:
      raise Exception('blalba')
    return CreditEstimate(Balance(1000), Balance(100))


class AccountFactoryI(AccountFactory):
  def createAccount(self, name, surname, pesel, income, current):
    accType = AccountType.STANDARD
    if income.value > 1000:
      accType = AccountType.PREMIUM
    return AccountCreated(Password("wed3rgeo"), accType)
  def obtainAccess(self, credentials, current):
    #TODO validation bla bla... adapter goes up
    return AccountPrx.checkedCast(current.adapter.add(AccountI(), Ice.stringToIdentity(str(pesel))))
    

with Ice.initialize(sys.argv, "./bank/config.server") as communicator:
  
  #run()
  signal.signal(signal.SIGINT, lambda signum, frame: communicator.shutdown())
  adapter = communicator.createObjectAdapter("AccountFactory")
  adapter.add(AccountFactoryI(), Ice.stringToIdentity("accountFactory"))
  adapter.activate()
  communicator.waitForShutdown()


