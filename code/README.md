# 代码示例

本目录包含计算机网络课程的配套代码示例，涵盖传输层、应用层、安全、云网络等主题。

## 目录

| 目录 | 语言 | 说明 |
|------|------|------|
| `tcp-udp/` | Java + Python | TCP/UDP Socket 编程示例 |
| `http/` | Java + Python | HTTP 客户端/服务器实现 |
| `dns/` | Java + Python | DNS 域名解析示例 |
| `tls/` | Python | TLS/HTTPS 安全连接示例 |
| `java-web/` | Java (Maven) | Servlet Web 应用 |
| `network-topology/` | YAML + Shell | Docker 容器网络拓扑 |

## 快速开始

### TCP/UDP Socket

```bash
# Java (先编译)
cd tcp-udp
javac TcpServer.java && java TcpServer    # 终端 1
javac TcpClient.java && java TcpClient    # 终端 2

# Python
python tcp_server.py                       # 终端 1
python tcp_client.py                       # 终端 2
```

### HTTP

```bash
# Python HTTP 服务器
cd http && python http_server.py
# 浏览器访问 http://127.0.0.1:8888

# Python HTTP 客户端
python http_client.py
```

### DNS

```bash
cd dns && python dns_lookup.py
```

### TLS/HTTPS

```bash
cd tls && python tls_client.py
```

### Docker 网络拓扑

```bash
cd network-topology
docker compose up -d
bash test_network.sh
docker compose down
```

## 学习顺序建议

1. **tcp-udp/** - 理解 Socket 编程基础，TCP vs UDP 差异
2. **http/** - 理解 HTTP 协议报文格式
3. **dns/** - 理解域名解析过程
4. **tls/** - 理解 HTTPS/TLS 握手过程
5. **java-web/** - 理解 Web 服务器工作原理
6. **network-topology/** - 理解容器网络隔离与通信
