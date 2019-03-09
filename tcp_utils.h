//
// Created by Mateusz Sokol on 09.03.19.
//

#ifndef SR_SOCKETS_TCP_UTILS_H
#define SR_SOCKETS_TCP_UTILS_H

struct sockaddr_in init_tcp_socket_server(int *socket_fd, int port);

void init_tcp_socket_client(int *socket_ds, int port, struct sockaddr_in *addr);

void accept_tcp_connection(int socket_in, int *socket_cli);

void connect_tcp_connection(int socket_out, struct sockaddr_in addr);

#endif //SR_SOCKETS_TCP_UTILS_H

