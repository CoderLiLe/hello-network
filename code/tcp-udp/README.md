# TCP/UDP Socket 编程示例

## TCP

演示 TCP 三次握手、数据传输、四次挥手。

```bash
# 终端 1 - 启动服务器
javac TcpServer.java && java TcpServer
# 或 Python 版
python tcp_server.py

# 终端 2 - 启动客户端
javac TcpClient.java && java TcpClient
# 或 Python 版
python tcp_client.py
```

## UDP

演示 UDP 无连接通信。

```bash
# 终端 1 - 启动服务器
javac UdpServer.java && java UdpServer
# 或 Python 版
python udp_server.py

# 终端 2 - 启动客户端
javac UdpClient.java && java UdpClient
# 或 Python 版
python udp_client.py
```

## 对比

| 特性 | TCP | UDP |
|------|-----|-----|
| 连接 | 面向连接 (3 次握手) | 无连接 |
| 可靠性 | 可靠 | 不可靠 |
| 顺序 | 保证 | 不保证 |
| 流量控制 | 有 | 无 |
| 拥塞控制 | 有 | 无 |
| 首部大小 | 20+ 字节 | 8 字节 |
| 速度 | 较慢 | 较快 |
| 适用场景 | 网页、文件传输、邮件 | 音视频、直播、DNS |
