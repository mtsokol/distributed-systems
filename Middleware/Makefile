.PHONY: bank_ice proto_exchange

bank_ice:
	slice2py --output-dir ./utils/out ./utils/bank.ice

proto_exchange:
	python3 -m grpc_tools.protoc ..

run_server:
	python3 ./bank/server.py

run_client:
	python3 ./client/client.py

