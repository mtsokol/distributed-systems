import socket
import datetime
import time

serverPort = 19009
UDP_IP = "224.0.0.1"
serverSocket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
serverSocket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

serverSocket.bind((UDP_IP, serverPort))
buff = []

print('PYTHON LOGGER IS RUNNING')

while True:
    buff = serverSocket.recv(1024)
    log_time = datetime.datetime.fromtimestamp(time.time()).strftime('%Y-%m-%d %H:%M:%S')
    print("python udp logger received client: " + buff + " at time: " + log_time)
