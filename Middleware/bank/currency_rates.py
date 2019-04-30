import sys
import os

sys.path.append(os.path.abspath("./utils/out/proto"))

from exchange_pb2 import *
import exchange_pb2_grpc

currency_rates = {
    PLN: 1,
    GBP: 3,
    USD: 2,
    CHF: 5,
    EUR: 4
}
