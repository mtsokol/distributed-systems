import sys
import os
import Ice
import signal

sys.path.append(os.path.abspath("./utils/out"))

from BankSystem import *


class BankI(Bank):
  def createAccount(self, name, surname, pesel, income, current):
    print("XD server")
    return AccountCreated(Password("wed3rgeo"))
    

with Ice.initialize(sys.argv, "./bank/config.server") as communicator:
  signal.signal(signal.SIGINT, lambda signum, frame: communicator.shutdown())
  adapter = communicator.createObjectAdapter("Bank")
  adapter.add(BankI(), Ice.stringToIdentity("bank"))
  adapter.activate()
  communicator.waitForShutdown()

