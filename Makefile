.PHONY: all clean run

CC=gcc -Wall

all: client

client: client.c contract.h
	$(CC) $^ -o $@

clean:
	rm client

run-client-1:
	./client CLIENT1 18080 127.0.0.1 -1 1 1

run-client-2:
	./client CLIENT2 18081 127.0.0.1 18080 0 1

run-client-3:
    ./client CLIENT3 18082 127.0.0.1 18080 0 1
