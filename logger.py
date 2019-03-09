import socket

serverPort = 19009
serverSocket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
serverSocket.bind(('', serverPort))
buff = []

print('PYTHON LOGGER IS RUNNING')

while True:

    buff, address = serverSocket.recvfrom(1024)
    print("python udp server received msg: " + str(buff, 'cp1250'))


