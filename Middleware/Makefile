.PHONY: run-server run-client run-exchange

bank-ice:
	mkdir -p ./utils/out/ice; \
	slice2py --output-dir ./utils/out/ice ./utils/bank.ice

proto-exchange-java:
	cd exchange; \
	./gradlew build

proto-exchange-python:
	mkdir -p ./utils/out/proto; \
	python3 -m grpc_tools.protoc -I./utils --python_out=./utils/out/proto --grpc_python_out=./utils/out/proto exchange.proto

run-server:
	python3 ./bank/server.py 1 2 3

run-client:
	python3 ./client/client.py

run-exchange:
	cd exchange; \
	./gradlew run

