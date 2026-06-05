import socket


def main():
    host = 'httpbin.org'
    port = 80

    client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    print(f'正在连接 {host}:{port}...')
    client.connect((host, port))
    print('--- TCP 连接已建立 ---')

    request = (
        'GET /get HTTP/1.1\r\n'
        f'Host: {host}\r\n'
        'User-Agent: hello-network/1.0\r\n'
        'Accept: */*\r\n'
        'Connection: close\r\n'
        '\r\n'
    )

    print(f'\n发送请求:\n{request}')
    client.sendall(request.encode())

    response = b''
    while True:
        chunk = client.recv(4096)
        if not chunk:
            break
        response += chunk

    print(f'收到响应 ({len(response)} 字节):')
    print(response.decode(errors='replace'))

    client.close()
    print('\n--- 连接已关闭 ---')


if __name__ == '__main__':
    main()
