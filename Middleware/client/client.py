import sys
import os
import Ice

sys.path.append(os.path.abspath("./utils/out/ice"))

from BankSystem import *

with Ice.initialize(sys.argv, "./client/config.client") as communicator:
    twoway = AccountFactoryPrx.checkedCast(
        communicator.propertyToProxy('AccountFactory.Proxy').ice_twoway().ice_secure(False))
    if not twoway:
        print("invalid proxy")
        sys.exit(1)
    xd = twoway.createAccount(Name("xd"), Surname("xd"), Pesel(12), Balance(12))
    sth = twoway.obtainAccess(Credentials(Pesel(12), xd.password))
    print(xd)
