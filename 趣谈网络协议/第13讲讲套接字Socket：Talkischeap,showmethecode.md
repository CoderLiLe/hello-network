# 第13讲 Socket编程

## Socket 参数

```c
socket(AF_INET, SOCK_STREAM, IPPROTO_TCP)   // TCP
socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP)    // UDP
```

AF_INET = IPv4，SOCK_STREAM = 数据流，SOCK_DGRAM = 数据报。

## TCP Socket 流程

```
服务端                              客户端
socket() → bind() → listen()         socket()
accept() ← 阻塞等待 ←------           connect() → 三次握手
read()/write() ←→ ESTABLISHED ←→ read()/write()
close()                              close()
```

accept 返回新的已连接 Socket，与监听 Socket 不同。内核维护两个队列：已完成握手（established）和未完成（syn_rcvd）。

## UDP Socket 流程

无需 listen/connect/accept，bind 后直接用 sendto/recvfrom 收发。一个 Socket 可与多客户端通信。

## 内核实现

Socket 是文件（有 fd）。task_struct → 文件描述符数组 → 内核打开文件列表。Socket inode 含发送/接收队列（sk_buff 缓存）。

## 高并发方案

TCP 连接四元组理论上限 2^48，实际受限 fd 上限（ulimit）和内存。

| 方案 | 特点 | 问题 |
|------|------|------|
| 多进程 fork() | 每连接一进程，复制 fd 和内存 | 开销大 |
| 多线程 pthread | 共享进程空间，轻量 | C10K：万连接 = 万线程 |
| select | 单线程监听 fd_set，轮询 | 轮询低效，受 FD_SETSIZE 限制 |
| epoll | 事件通知，红黑树 + 回调 | - |

epoll 三步：epoll_create（创建 epoll 对象）→ epoll_ctl（注册 Socket + 回调）→ epoll_wait（等待事件）。监听上限 = 系统最大 fd 数，解决 C10K。
