//
// Created by Mateusz Sokol on 06.03.19.
//

#ifndef SR_SOCKETS_CONTRACT_H
#define SR_SOCKETS_CONTRACT_H

#define TCP 0
#define UDP 1

#define FREE 1
#define TAKEN 0

#define CONNECT 10
#define TOKEN 11

typedef struct message {
    char msg[100];
} message;

typedef struct access_record {
    int idx;
    int arr[100];
} access_record;

typedef struct token {
    int type; // 10 - connect, 11 - token
    int usage; // 0 - free, 1 - taken
    char recipient_name[100]; // client ID
    message msg;
    access_record ac_rec;
    int port;
} token;

//typedef struct log_response {
//    char address[100];
//    int port;
//} log_response;

#endif //SR_SOCKETS_CONTRACT_H
