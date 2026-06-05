import socket


def dns_lookup(hostname):
    print(f'查询: {hostname}')
    print(f'{"类型":>8} {"IP 地址":<20} {"别名":<20} {"地址族":<10}')
    print('-' * 60)

    try:
        result = socket.getaddrinfo(hostname, None)
        seen = set()
        for res in result:
            family, _, _, host, addr = res
            ip = addr[0]
            if ip not in seen:
                seen.add(ip)
                family_name = {socket.AF_INET: 'IPv4', socket.AF_INET6: 'IPv6'}.get(family, str(family))
                canonname = host if host else '-'
                print(f'{family_name:>8} {ip:<20} {canonname:<20} {family:<10}')
    except socket.gaierror as e:
        print(f'  解析失败: {e}')


def reverse_lookup(ip):
    try:
        hostname, _, _ = socket.gethostbyaddr(ip)
        print(f'\n反向查询 {ip} → {hostname}')
    except socket.herror:
        print(f'\n反向查询 {ip} → 无记录')


def main():
    domains = ['www.google.com', 'www.baidu.com', 'github.com']

    for domain in domains:
        dns_lookup(domain)
        print()

    dns_lookup('www.example.com')
    reverse_lookup('93.184.216.34')

    print('\n用 nslookup 验证:')
    print('  nslookup www.example.com')
    print('  nslookup -type=MX gmail.com')


if __name__ == '__main__':
    main()
