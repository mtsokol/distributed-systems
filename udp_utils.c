//
// Created by Mateusz Sokol on 09.03.19.
//

#include "udp_utils.h"
#include "contract.h"
#include <netinet/in.h>
#include <stdlib.h>
#include <stdio.h>
#include <arpa/inet.h>
#include <string.h>

void init_udp_socket_server(int *socket_fd, int port) {

    struct sockaddr_in addr;

    if ((*socket_fd = socket(AF_INET, SOCK_DGRAM, 0)) < 0 ) {
        exit(1);
    }

    addr.sin_family = AF_INET;
    addr.sin_addr.s_addr = htonl(INADDR_ANY);
    addr.sin_port = htons((uint16_t) port);

    if ((bind(*socket_fd, (struct sockaddr *) &addr, sizeof(addr))) != 0) {
        printf("ERROR: socket bind failed\n");
        exit(1);
    }

}

void init_udp_socket_client(int *socket_fd) {

    if ((*socket_fd = socket(AF_INET, SOCK_DGRAM, 0)) < 0 ) {
        printf("ERROR: socket udp failed\n");
        exit(1);
    }

}

struct sockaddr_in init_udp_send(int socket_fd, int port, in_addr_t addr, void *buff, int flag) {
    struct sockaddr_in socket_addr;
    socket_addr.sin_family = AF_INET;
    socket_addr.sin_port = htons((uint16_t) port);
    socket_addr.sin_addr.s_addr = addr;
    sendto(socket_fd, buff, sizeof(token), flag,
           (const struct sockaddr *) &socket_addr, sizeof(socket_addr));

    return socket_addr;
}
