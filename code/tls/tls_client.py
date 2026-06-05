import ssl
import socket


def inspect_cert(cert):
    print(f'  主体: {cert.get("subject", [])}')
    print(f'  签发者: {cert.get("issuer", [])}')
    print(f'  版本: {cert.get("version", "N/A")}')
    serial = cert.get('serialNumber', 'N/A')
    print(f'  序列号: {serial}')
    print(f'  算法: {cert.get("signatureAlgorithm", "N/A")}')
    print(f'  生效: {cert.get("notBefore", "N/A")}')
    print(f'  到期: {cert.get("notAfter", "N/A")}')
    for sub in cert.get('subject', []):
        for k, v in sub:
            if k == 'commonName':
                print(f'  CN: {v}')
            if k == 'organizationName':
                print(f'  组织: {v}')


def main():
    host = 'www.baidu.com'
    port = 443

    print(f'正在连接 {host}:{port} (TLS)...')

    context = ssl.create_default_context()

    with socket.create_connection((host, port), timeout=10) as sock:
        with context.wrap_socket(sock, server_hostname=host) as tls:
            print(f'--- TLS 握手完成 ---')
            print(f'  版本: {tls.version()}')
            print(f'  加密套件: {tls.cipher()}')
            print(f'  对端证书:\n')
            cert = tls.getpeercert()
            if cert:
                inspect_cert(cert)

            print(f'\n发送 HTTPS 请求...')
            request = (
                'GET / HTTP/1.1\r\n'
                f'Host: {host}\r\n'
                'User-Agent: hello-network/1.0\r\n'
                'Accept: */*\r\n'
                'Connection: close\r\n'
                '\r\n'
            )
            tls.sendall(request.encode())

            response = b''
            while True:
                chunk = tls.recv(4096)
                if not chunk:
                    break
                response += chunk

            header, _, body = response.decode(errors='replace').partition('\r\n\r\n')
            print(f'\n响应头:\n{header[:500]}')
            print(f'\n响应体: {len(body)} 字节 (前 200 字符):')
            print(body[:200])

    print('\n--- TLS 连接已关闭 ---')


if __name__ == '__main__':
    main()
