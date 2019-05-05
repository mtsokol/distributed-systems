# Middleware

Banking system created with ICE and gRPC, implemented in Python and Java.

## Usage 

Basic usage covers running bank app, exchange and client app.

###  Generating code

After installing `slice2py` and `grpc_tools.protoc` execute:

```sh
$ make bank-ice
$ make proto-exchange-java
$ make proto-exchange-python
```

### Running project

1. Run `make run-exchange`
2. Run `make run-server`
3. In separate shell run `make run-client`

Example client cli interaction:

```sh
bank-welcome$ signup
your-name$ Jan
your-surname$ Smith
your-pesel$ 1243534
your-balance$ 12000

{
    password = 
    {
        value = wed-X
    }
    accountType = PREMIUM
}

bank-welcome$ signin
your-pesel$ 1243534
your-password$ wed-X

1243534_PREMIUM -t -e 1.1:tcp -h localhost -p 10000 -t 60000

bank-PREMIUM$ balance

your balance is: 
{
    value = 12100.0
}
```
  
## References

- https://github.com/zeroc-ice/ice-demos/tree/3.7/python

- https://github.com/grpc/grpc-java

- https://github.com/grpc/grpc/tree/master/examples/python
