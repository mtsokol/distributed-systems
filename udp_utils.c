//
// Created by Mateusz Sokol on 09.03.19.
//

#include "udp_utils.h"
#include <netinet/in.h>
#include <stdlib.h>
#include <stdio.h>

void init_udp_socket_server(int *sockfd, int port) {

    struct sockaddr_in addr;

    if ((*sockfd = socket(AF_INET, SOCK_DGRAM, 0)) < 0 ) {
        exit(1);
    }

    addr.sin_family = AF_INET;
    addr.sin_addr.s_addr = htonl(INADDR_ANY);
    addr.sin_port = htons((uint16_t) port);

    if ((bind(*sockfd, (struct sockaddr *) &addr, sizeof(addr))) != 0) {
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
