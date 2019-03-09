import socket

serverPort = 19009
UDP_IP = "127.0.0.1"
serverSocket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
serverSocket.bind((UDP_IP, serverPort))
buff = []

print('PYTHON LOGGER IS RUNNING')

while True:

    buff, address = serverSocket.recvfrom(1024)
    print("python udp logger received client: " + buff)
