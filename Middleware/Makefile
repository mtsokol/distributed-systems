.PHONY: bank_ice run_server run_client

bank_ice:
	slice2py --output-dir ./utils/out ./utils/bank.ice

proto_exchange_java:
	cd exchange; \
	./gradlew build

proto_exchange_python:
	python3 -m grpc_tools.protoc -I. --python_out=. --grpc_python_out=. exchange.proto

run_server:
	python3 ./bank/server.py

run_client:
	python3 ./client/client.py

