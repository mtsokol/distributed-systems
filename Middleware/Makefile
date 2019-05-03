.PHONY: bank_ice run_server run_client

bank_ice:
	mkdir -p ./utils/out/ice; \
	slice2py --output-dir ./utils/out/ice ./utils/bank.ice

proto_exchange_java:
	cd exchange; \
	./gradlew build

proto_exchange_python:
	mkdir -p ./utils/out/proto; \
	python3 -m grpc_tools.protoc -I./utils --python_out=./utils/out/proto --grpc_python_out=./utils/out/proto exchange.proto

run_server:
	python3 ./bank/server.py 1 2 3

run_client:
	python3 ./client/client.py

