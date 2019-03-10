//
// Created by Mateusz Sokol on 06.03.19.
//

#include "contract.h"
#include <stdlib.h>
#include <stdio.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <string.h>
#include <netdb.h>
#include <sys/types.h>
#include <signal.h>
#include "tcp_utils.h"
#include "udp_utils.h"

#define LOGGER_PORT 19009

int socket_in;
int socket_out;

void graceful_exit(int signum) {
    printf("\nexiting\n");
    close(socket_in);
    close(socket_out);
    exit(0);
}

int main(int argc, char **argv) {

    char name[100];
    int protocol;
    //char address[100];
    int port;
    char neigh_address[100];
    int neigh_port;
    int token_flag;
    int access_idx = -1;
    int logger_fd;
    token ring_token;

    signal(SIGINT, graceful_exit);

    if (argc == 1 || argv[1] == NULL || argv[2] == NULL ||
        argv[3] == NULL || argv[4] == NULL || argv[5] == NULL) {
        printf("Execute args:\n 1. clientID\n 2. port\n 3. neighbour address"
               "\n 4. neighbour port\n 5. have token\n 6. protocol tcp/udp\n");
        exit(1);
    }

    sscanf(argv[1], "%s", name);
    sscanf(argv[2], "%d", &port);
    sscanf(argv[3], "%s", neigh_address);
    sscanf(argv[4], "%d", &neigh_port);
    sscanf(argv[5], "%d", &token_flag);
    sscanf(argv[6], "%d", &protocol);

    //socket to logger
    init_udp_socket_client(&logger_fd);

    // initial opening socket

    if (token_flag == 1) {

        ring_token.usage = FREE;
        ring_token.ac_rec.idx = 0;
        for (int i = 0; i < 100; ++i) {
            ring_token.ac_rec.arr[i] = 0;
        }

        int socket_cli;
        token read_token;

        if (protocol == TCP) {

            init_tcp_socket_server(&socket_in, port);

            accept_tcp_connection(socket_in, &socket_cli);

            read(socket_cli, &read_token, sizeof(token));

            neigh_port = read_token.port;

            write(socket_cli, &port, sizeof(port));

        } else if (protocol == UDP) {

            init_udp_socket_server(&socket_in, port);

            struct sockaddr_in cli;
            socklen_t len;

            recvfrom(socket_in, &read_token, sizeof(token), MSG_WAITALL,
                     (struct sockaddr *) &cli, &len);

            neigh_port = read_token.port;

            sendto(socket_in, &port, sizeof(port), MSG_CONFIRM,
                   (struct sockaddr *) &cli, len);

        } else {
            printf("invalid protocol\n");
            exit(1);
        }

        sleep(1);

    } else {

        struct sockaddr_in addr;

        token token1;
        token1.type = CONNECT;
        token1.port = port;
        int response_port;

        if (protocol == TCP) {

            init_tcp_socket_client(&socket_out, neigh_port, &addr);

            connect_tcp_connection(socket_out, addr);

            if (write(socket_out, &token1, sizeof(token)) < 0) {
                printf("can't log on server\n");
            }

            read(socket_out, &response_port, sizeof(response_port));

            neigh_port = response_port;

            init_tcp_socket_server(&socket_in, port);

        } else if (protocol == UDP) {

            init_udp_socket_client(&socket_out);

            struct sockaddr_in cli = init_udp_send(socket_out,
                    neigh_port, htonl(INADDR_ANY), &token1, MSG_CONFIRM);
            socklen_t len = sizeof(cli);

            recvfrom(socket_out, &response_port, sizeof(response_port), MSG_WAITALL,
                     (struct sockaddr *) &cli, &len);

            neigh_port = response_port;

            init_udp_socket_server(&socket_in, port);

        } else {
            printf("invalid protocol\n");
            exit(1);
        }

        printf("connection established\n");
    }

    // network loop

    while (1) {

        if (token_flag == 0) {

            if (protocol == TCP) {

                int socket_cli;
                accept_tcp_connection(socket_in, &socket_cli);

                read(socket_cli, &ring_token, sizeof(ring_token));

                // ---handling new requests------
                if (ring_token.type == CONNECT) {

                    write(socket_cli, &neigh_port, sizeof(neigh_port));

                    neigh_port = ring_token.port;

                    sleep(1);

                    continue;
                }

                close(socket_cli);

            } else if (protocol == UDP) {

                struct sockaddr_in cli;
                socklen_t len;

                recvfrom(socket_in, &ring_token, sizeof(ring_token), MSG_WAITALL,
                         (struct sockaddr *) &cli, &len);

                if (ring_token.type == CONNECT) {

                    sendto(socket_in, &neigh_port, sizeof(neigh_port), MSG_CONFIRM,
                           (struct sockaddr *) &cli, len);

                    neigh_port = ring_token.port;

                    sleep(1);

                    continue;
                }

            } else {
                printf("invalid protocol\n");
                exit(1);
            }
            //----------------------------------

            token_flag = 1;
            printf("MINE TIME!\n");

            sleep(1);

        } else {

            // MULTICAST
            init_udp_send(logger_fd, LOGGER_PORT, inet_addr("224.0.0.1"),
                          name, MSG_DONTWAIT);

            //TODO fun with token
            int r = rand() % 10;

            ring_token.usage = TAKEN;
            if (access_idx == -1) {
                ring_token.ac_rec.idx++;
                access_idx = ring_token.ac_rec.idx;
            }

            int a = ring_token.ac_rec.arr[access_idx]++;

            printf("%d\n", a);

            //--------------------

            ring_token.type = TOKEN;
            printf("trying to connect to %d\n", neigh_port);
            struct sockaddr_in addr;

            if (protocol == TCP) {

                init_tcp_socket_client(&socket_out, neigh_port, &addr);

                connect_tcp_connection(socket_out, addr);

                write(socket_out, &ring_token, sizeof(ring_token));

                close(socket_out);

            } else if (protocol == UDP) {

                init_udp_socket_client(&socket_out);

                init_udp_send(socket_out, neigh_port, htonl(INADDR_ANY),
                              &ring_token, MSG_CONFIRM);

            } else {
                printf("invalid protocol\n");
                exit(1);
            }

            token_flag = 0;

            printf("TOKEN PASSED!\n");
        }

        sleep(1);
    }

}
