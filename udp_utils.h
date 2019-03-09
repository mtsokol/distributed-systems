//
// Created by Mateusz Sokol on 09.03.19.
//

#ifndef SR_SOCKETS_UDP_UTILS_H
#define SR_SOCKETS_UDP_UTILS_H

void init_udp_socket_server(int *sockfd, int port);

void init_udp_socket_client(int *socket_fd);

void send_udp();

#endif //SR_SOCKETS_UDP_UTILS_H
