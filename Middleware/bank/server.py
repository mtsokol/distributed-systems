import sys
import os
import Ice
import signal
import grpc

import exchange_pb2
import exchange_pb2_grpc

sys.path.append(os.path.abspath("./utils/out"))

from BankSystem import *

#class Exchange(exchange_pb2_grpc.ExchangeServicer):
#  def subscribeExchangeRate(self, request, context):

def run():
  channel = grpc.insecure_channel('localhost:50051')
  stub = exchange_pb2_grpc.ExchangeStub(channel)
  for response in stub.subscribeExchangeRate(exchange_pb2.ExchangeRequest()):
    print("Greeter client received: " + str(response.currency))

class BankI(Bank):
  def createAccount(self, name, surname, pesel, income, current):
    print("XD server")
    return AccountCreated(Password("wed3rgeo"))
    

with Ice.initialize(sys.argv, "./bank/config.server") as communicator:
  
  run()
  print("xd")
  signal.signal(signal.SIGINT, lambda signum, frame: communicator.shutdown())
  adapter = communicator.createObjectAdapter("Bank")
  adapter.add(BankI(), Ice.stringToIdentity("bank"))
  adapter.activate()
  communicator.waitForShutdown()

