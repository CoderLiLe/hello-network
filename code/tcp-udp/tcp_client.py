import socket


def main():
    host = '127.0.0.1'
    port = 8080

    print(f'正在连接服务器 {host}:{port}...')
    print('--- 3 次握手进行中 ---')

    client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    client.connect((host, port))

    print('--- 3 次握手完成 ---')
    print('已连接到服务器, 输入消息 (输入 quit 退出):')

    try:
        while True:
            msg = input('> ')
            if msg.lower() == 'quit':
                break
            client.sendall(msg.encode())
            resp = client.recv(1024).decode()
            print(f'服务器响应: {resp}')
    except KeyboardInterrupt:
        pass
    finally:
        client.close()
        print('--- 4 次挥手完成 ---')


if __name__ == '__main__':
    main()
