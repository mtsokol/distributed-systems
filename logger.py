import socket
import struct

serverPort = 19009
UDP_IP = "224.0.0.1"
serverSocket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM, socket.IPPROTO_UDP)
serverSocket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

serverSocket.bind((UDP_IP, serverPort))
buff = []

mreq = struct.pack("4sl", socket.inet_aton(UDP_IP), socket.INADDR_ANY)
serverSocket.setsockopt(socket.IPPROTO_IP, socket.IP_ADD_MEMBERSHIP, mreq)

print('PYTHON LOGGER IS RUNNING')

while True:
    buff = serverSocket.recv(1024)
    print("python udp logger received client: " + buff)
