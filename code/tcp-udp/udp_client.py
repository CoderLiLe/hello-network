import socket


def main():
    host = '127.0.0.1'
    port = 8888

    print('UDP 客户端启动, 无需连接, 直接发送数据')
    print('UDP 没有 3 次握手, 也没有 4 次挥手')

    client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

    msg = 'Hello UDP!'
    client.sendto(msg.encode(), (host, port))
    print(f'发送: {msg}')

    data, _ = client.recvfrom(1024)
    print(f'收到: {data.decode()}')

    client.close()


if __name__ == '__main__':
    main()
