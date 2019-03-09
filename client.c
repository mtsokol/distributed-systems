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

#define LOGGGER_PORT 19009

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

        init_tcp_socket_server(&socket_in, port);

        int socket_cli;

        accept_tcp_connection(socket_in, &socket_cli);

        token read_token;

        read(socket_cli, &read_token, sizeof(token));

        printf("%d\n", read_token.port);

        neigh_port = read_token.port;

        write(socket_cli, &port, sizeof(port));

        sleep(1);

    } else {

        struct sockaddr_in addr;

        init_tcp_socket_client(&socket_out, neigh_port, &addr);

        connect_tcp_connection(socket_out, addr);

        token token1;
        token1.type = CONNECT;
        token1.port = port;

        if (write(socket_out, &token1, sizeof(token)) < 0) {
            printf("can't log on server\n");
        }

        int response_port;

        read(socket_out, &response_port, sizeof(response_port));


        printf("%d\n", response_port);

        neigh_port = response_port;

        init_tcp_socket_server(&socket_in, port);

        printf("SERVER established\n");
    }

    // network loop

    while (1) {

        if (token_flag == 0) {

            printf("0 %d\n", port);

            struct token tk;

            if (tk.type == 343233) {
                printf("zagraj w totka\n");
                exit(1);
            }

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
            //----------------------------------

            token_flag = 1;
            printf("MINE TIME!\n");

            sleep(1);

        } else {

            //TODO send on multicast
            struct sockaddr_in addr_mul;
            addr_mul.sin_family = AF_INET;
            addr_mul.sin_port = htons(LOGGGER_PORT);
            addr_mul.sin_addr.s_addr = inet_addr("127.0.0.1");
            sendto(logger_fd, name, strlen(name), MSG_CONFIRM,
                    (const struct sockaddr*) &addr_mul, sizeof(addr_mul));

            printf("1 %d\n", port);

            token tk;
            tk.type = TOKEN;

            printf("trying to connect to %d\n", neigh_port);

            struct sockaddr_in addr;

            init_tcp_socket_client(&socket_out, neigh_port, &addr);

            connect_tcp_connection(socket_out, addr);

            write(socket_out, &tk, sizeof(tk));

            token_flag = 0;

            printf("TOKEN PASSED!\n");

        }

        sleep(1);

    }

}
