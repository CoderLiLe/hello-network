import socket


def handle_request(request):
    lines = request.split('\r\n')
    if not lines:
        return 'HTTP/1.1 400 Bad Request\r\n\r\n'

    method, path, _ = lines[0].split(' ', 2)
    print(f'  {method} {path}')

    body = f'''<!DOCTYPE html>
<html>
<head><title>Hello Network</title></head>
<body>
<h1>Hello Network!</h1>
<p>方法: {method}</p>
<p>路径: {path}</p>
<hr>
<h2>请求头</h2>
<pre>'''
    for line in lines[1:]:
        if line == '':
            break
        body += line + '\n'

    body += '''</pre>
</body>
</html>'''

    response = (
        'HTTP/1.1 200 OK\r\n'
        'Content-Type: text/html; charset=utf-8\r\n'
        f'Content-Length: {len(body.encode())}\r\n'
        'Connection: close\r\n'
        '\r\n'
        f'{body}'
    )
    return response


def main():
    host = '127.0.0.1'
    port = 8888

    server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    server.bind((host, port))
    server.listen(5)

    print(f'HTTP Server 启动于 http://{host}:{port}')
    print('按 Ctrl+C 停止\n')

    try:
        while True:
            conn, addr = server.accept()
            print(f'新连接: {addr[0]}:{addr[1]}')
            data = conn.recv(4096)
            if data:
                request = data.decode()
                response = handle_request(request)
                conn.sendall(response.encode())
            conn.close()
            print(f'关闭连接: {addr[0]}:{addr[1]}\n')
    except KeyboardInterrupt:
        print('\n服务器停止')
    finally:
        server.close()


if __name__ == '__main__':
    main()
