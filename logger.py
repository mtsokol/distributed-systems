import socket

serverPort = 19009
UDP_IP = "224.0.0.1"
serverSocket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM, socket.IPPROTO_UDP)
serverSocket.bind((UDP_IP, serverPort))
buff = []

print('PYTHON LOGGER IS RUNNING')

while True:

    buff = serverSocket.recv(1024)
    print("python udp logger received client: " + buff)
