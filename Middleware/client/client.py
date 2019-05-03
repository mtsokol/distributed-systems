import sys
import os
import Ice

sys.path.append(os.path.abspath("./utils/out/ice"))

from BankSystem import *

def get_currency_symbol(str):
    switch = {
        'PLN': Currency.PLN,
        'GBP': Currency.GBP ,
        'USD': Currency.USD,
        'CHF': Currency.CHF,
        'EUR': Currency.EUR
    }
    return switch.get(str.upper(), 'invalid currency')

def cli_wr_rd(msg):
    sys.stdout.write(msg)
    sys.stdout.flush()
    return sys.stdin.readline().strip()

def run(communicator):

    server = AccountFactoryPrx.checkedCast(
        communicator.propertyToProxy('AccountFactory.Proxy').ice_twoway().ice_secure(False))
    if not server:
        print('invalid server proxy')
        sys.exit(1)

    account_proxy = None

    while True:
        if account_proxy is None:
            command = cli_wr_rd('bank-welcome$ ')
            if command == 'help':
                print('there is no help')
            elif command == 'signup':
                name = cli_wr_rd('your-name$ ')
                surname = cli_wr_rd('your-surname$ ')
                pesel = cli_wr_rd('your-pesel$ ')
                balance = cli_wr_rd('your-balance$ ')
                acc_create_resp = server.createAccount(Name(name),
                                                       Surname(surname),
                                                       Pesel(int(pesel)),
                                                       Balance(int(balance)))
                print(acc_create_resp)
            elif command == 'signin':
                pesel = cli_wr_rd('your-pesel$ ')
                password = cli_wr_rd('your-password$ ')
                ctx = {'password': password}
                try:
                    account_proxy = server.obtainAccess(Pesel(int(pesel)), ctx)
                except Exception as error:
                    print(error)
                else:
                    print(account_proxy)
            else:
                print('invalid command, use \'help\'')
        else:
            sys.stdout.write(f'bank-{account_proxy.getAccountType()}$ ')
            sys.stdout.flush()
            command = sys.stdin.readline().strip()
            if command == 'help':
                print('there is no help')
            elif command == 'signout':
                account_proxy = None
            elif command == 'balance':
                print(f'your balance is: {account_proxy.getAccountBalance()}')
            elif command == 'credit':
                credit_currency = cli_wr_rd('credit-currency$ ')
                credit_amount = cli_wr_rd('credit-amount$ ')
                credit_period = cli_wr_rd('credit-period$ ')
                try:
                    credit_estimate = account_proxy.applyForCredit(get_currency_symbol(credit_currency),
                                                                   Balance(int(credit_amount)),
                                                                   Period(credit_period))
                except InvalidAccountTypeException as error:
                    print(error)
                else:
                    print(credit_estimate)
            elif command == 'accounttype':
                print(f'your account type is: {account_proxy.getAccountType()}')
            else:
                print('invalid command, use \'help\'')


with Ice.initialize(sys.argv, "./client/config.client") as communicator:
    run(communicator)
