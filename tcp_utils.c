//
// Created by Mateusz Sokol on 09.03.19.
//

#include "tcp_utils.h"
#include "contract.h"
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <netinet/in.h>

struct sockaddr_in init_tcp_socket_server(int *socket_fd, int port) {

    *socket_fd = socket(AF_INET, SOCK_STREAM, 0);
    if (*socket_fd == -1) {
        printf("ERROR: can't create socket\n");
        exit(1);
    }

    struct sockaddr_in addr;
    addr.sin_family = AF_INET;
    addr.sin_port = htons((uint16_t) port);
    addr.sin_addr.s_addr = htonl(INADDR_ANY);

    if ((bind(*socket_fd, (struct sockaddr *) &addr, sizeof(addr))) != 0) {
        printf("ERROR: socket bind failed\n");
        exit(1);
    }

    if ((listen(*socket_fd, 10)) != 0) {
        printf("ERROR: listen failed\n");
        exit(1);
    }

    return addr;
}

void init_tcp_socket_client(int *socket_ds, int port, struct sockaddr_in *addr) {

    *socket_ds = socket(AF_INET, SOCK_STREAM, 0);
    if (*socket_ds == -1) {
        printf("inet: can't create socket\n");
        exit(1);
    }

    addr->sin_family = AF_INET;
    addr->sin_port = htons((uint16_t) port);
    addr->sin_addr.s_addr = htonl(INADDR_ANY);

}

void accept_tcp_connection(int socket_in, int *socket_cli) {

    struct sockaddr_in client;
    socklen_t len = sizeof(client);
    *socket_cli = accept(socket_in, (struct sockaddr *) &client, &len);
    if (socket_cli < 0) {
        printf("server acccept failed\n");
        exit(1);
    }

}

void connect_tcp_connection(int socket_out, struct sockaddr_in addr) {

    while (connect(socket_out, (const struct sockaddr *) &addr, sizeof(addr)) == -1) {
        printf("waiting to connect\n");
        usleep(500000);
    }

}
