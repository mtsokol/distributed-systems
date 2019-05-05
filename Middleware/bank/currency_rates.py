import sys
import os

sys.path.append(os.path.abspath("./utils/out/proto"))

from exchange_pb2 import *
import exchange_pb2_grpc

currency_rates = {
    PLN: 0,
    GBP: 0,
    USD: 0,
    CHF: 0,
    EUR: 0
}


def currency_rates_print():
    print('{{ PLN: {0:.2f}, GBP: {1:.2f}, USD: {2:.2f}, CHF: {3:.2f}, EUR: {4:.2f} }}'.format(currency_rates[PLN],
                                                                                              currency_rates[GBP],
                                                                                              currency_rates[USD],
                                                                                              currency_rates[CHF],
                                                                                              currency_rates[EUR]))
