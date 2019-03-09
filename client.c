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
#include "tcp_utils.h"
#include "udp_utils.h"

#define LOGGER_PORT 19009

token init_token() {
    message m;

    m.msg[1] = 'a';

    access_record ac;

    ac.idx = 0;

    token t;

    t.msg = m;
    t.ac_rec = ac;

    t.type = CONNECT;

    return t;
}

int main(int argc, char **argv) {

    char name[100];
    int protocol;
    //char address[100];
    int port;
    char neigh_address[100];
    int neigh_port;
    int token_flag;
    int socket_in;
    int socket_out;
    int access_idx = -1;
    int logger_fd;

    if (argc == 1 || argv[1] == NULL || argv[2] == NULL || argv[3] == NULL || argv[4] == NULL || argv[5] == NULL) {
        printf("Execute args:\n 1. clientID\n 2. port\n 3. neighbour address\n 4. neighbour port\n"
               " 6. have token\n 6. protocol tcp/udp\n");
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

        int socket_cli;
        token read_token;

        if (protocol == TCP) {

            init_tcp_socket_server(&socket_in, port);

            accept_tcp_connection(socket_in, &socket_cli);

            read(socket_cli, &read_token, sizeof(token));

            printf("%d\n", read_token.port);

            neigh_port = read_token.port;

            write(socket_cli, &port, sizeof(port));

        } else if (protocol == UDP) {

            init_udp_socket_server(&socket_in, port);

            struct sockaddr_in cli;
            socklen_t len;

            recvfrom(socket_in, &read_token, sizeof(token), MSG_WAITALL,
                     (struct sockaddr *) &cli, &len);

            printf("%d\n", read_token.port);

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

            printf("%d\n", response_port);

            neigh_port = response_port;

            init_tcp_socket_server(&socket_in, port);

        } else if (protocol == UDP) {

            init_udp_socket_client(&socket_out);

            struct sockaddr_in cli;
            cli.sin_family = AF_INET;
            cli.sin_port = htons((uint16_t) neigh_port);
            cli.sin_addr.s_addr = htonl(INADDR_ANY);
            socklen_t len = sizeof(cli);
            sendto(socket_out, &token1, sizeof(token1), MSG_CONFIRM,
                   (struct sockaddr *) &cli, len);

            recvfrom(socket_out, &response_port, sizeof(response_port), MSG_WAITALL,
                     (struct sockaddr *) &cli, &len);

            neigh_port = response_port;

            init_udp_socket_server(&socket_in, port);

        } else {
            printf("invalid protocol\n");
            exit(1);
        }

        printf("server established\n");
    }

    // network loop

    while (1) {

        if (token_flag == 0) {

            printf("0 %d\n", port);

            struct token tk;

            if (protocol == TCP) {

                int socket_cli;
                accept_tcp_connection(socket_in, &socket_cli);

                read(socket_cli, &tk, sizeof(tk));

                // ---handling new requests------
                if (tk.type == CONNECT) {

                    write(socket_cli, &neigh_port, sizeof(neigh_port));

                    neigh_port = tk.port;

                    sleep(1);

                    continue;
                }
            } else if (protocol == UDP) {

                struct sockaddr_in cli;
                socklen_t len;

                recvfrom(socket_in, &tk, sizeof(tk), MSG_WAITALL,
                         (struct sockaddr *) &cli, &len);

                if (tk.type == CONNECT) {

                    sendto(socket_in, &neigh_port, sizeof(neigh_port), MSG_CONFIRM,
                           (struct sockaddr *) &cli, len);

                    neigh_port = tk.port;

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

            //TODO send on multicast
            struct sockaddr_in addr_mul;
            addr_mul.sin_family = AF_INET;
            addr_mul.sin_port = htons(LOGGER_PORT);
            addr_mul.sin_addr.s_addr = inet_addr("127.0.0.1");
            sendto(logger_fd, name, strlen(name), MSG_DONTWAIT,
                   (const struct sockaddr *) &addr_mul, sizeof(addr_mul));
            printf("1 %d\n", port);

            token tk;
            tk.type = TOKEN;
            printf("trying to connect to %d\n", neigh_port);
            struct sockaddr_in addr;

            if (protocol == TCP) {

                init_tcp_socket_client(&socket_out, neigh_port, &addr);

                connect_tcp_connection(socket_out, addr);

                write(socket_out, &tk, sizeof(tk));

                token_flag = 0;

                printf("TOKEN PASSED!\n");

            } else if (protocol == UDP) {

                init_udp_socket_client(&socket_out);

                struct sockaddr_in cli_xd;
                cli_xd.sin_family = AF_INET;
                cli_xd.sin_port = htons((uint16_t) neigh_port);
                cli_xd.sin_addr.s_addr = htonl(INADDR_ANY);
                socklen_t len = sizeof(cli_xd);

                sendto(socket_out, &tk, sizeof(tk), MSG_CONFIRM,
                       (const struct sockaddr *) &cli_xd, len);

                token_flag = 0;

                printf("TOKEN PASSED!\n");

            } else {
                printf("invalid protocol\n");
                exit(1);
            }

        }

        sleep(1);

    }

}
