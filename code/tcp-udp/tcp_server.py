import socket


def main():
    host = '127.0.0.1'
    port = 8080

    server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    server.bind((host, port))
    server.listen(1)

    print(f'TCP Server 启动, 监听端口: {port}')
    print('等待客户端连接... (3 次握手)')

    conn, addr = server.accept()
    print(f'客户端已连接: {addr[0]}:{addr[1]}')
    print('--- 3 次握手完成 ---')

    with conn:
        while True:
            data = conn.recv(1024)
            if not data:
                break
            msg = data.decode()
            print(f'收到: {msg}')
            conn.sendall(f'ECHO: {msg}'.encode())

    print('客户端断开连接')
    print('--- 4 次挥手完成 ---')
    server.close()


if __name__ == '__main__':
    main()
