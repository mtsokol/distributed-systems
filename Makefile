.PHONY: all clean run

CC=gcc -Wall

all: tcp_utils.o tcp_utils.a udp_utils.o udp_utils.a client.o client

tcp_utils.o: tcp_utils.c
	$(CC) -c $^

tcp_utils.a: tcp_utils.o
	ar rcs $@ $^

udp_utils.o: udp_utils.c
	$(CC) -c $^

udp_utils.a: udp_utils.o
	ar rcs $@ $^

client.o: client.c
	$(CC) -c $^

client: client.o tcp_utils.a udp_utils.a
	$(CC) $^ -o $@

clean:
	rm client client.o tcp_utils.a tcp_utils.o udp_utils.a udp_utils.o

run-client-1:
	./client CLIENT1 18080 127.0.0.1 -1 1 1

run-client-2:
	./client CLIENT2 18081 127.0.0.1 18080 0 1

run-client-3:
	./client CLIENT3 18082 127.0.0.1 18080 0 1
