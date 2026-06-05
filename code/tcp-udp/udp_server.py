import socket


def main():
    host = '127.0.0.1'
    port = 8888

    server = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    server.bind((host, port))

    print(f'UDP Server 启动, 监听端口: {port}')
    print('UDP 是无连接的, 不需要 3 次握手')

    data, addr = server.recvfrom(1024)
    msg = data.decode()
    print(f'收到: {msg} 来自: {addr[0]}:{addr[1]}')

    resp = f'ECHO: {msg}'.encode()
    server.sendto(resp, addr)

    server.close()


if __name__ == '__main__':
    main()
