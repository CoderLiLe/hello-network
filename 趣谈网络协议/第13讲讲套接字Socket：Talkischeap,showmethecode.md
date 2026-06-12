# 第11讲 Socket编程

## Socket 参数

```c
// TCP Socket
socket(AF_INET, SOCK_STREAM, IPPROTO_TCP)
// UDP Socket
socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP)
```

- AF_INET：IPv4，AF_INET6：IPv6
- SOCK_STREAM：数据流（TCP），SOCK_DGRAM：数据报（UDP）

## TCP Socket 调用流程

```
服务端                              客户端
socket()                            socket()
bind()    ← 绑定IP和端口
listen()  ← 进入LISTEN状态
accept()  ← 阻塞等待连接             connect() → 发起三次握手
  ↓                                   ↓
read()/write() ←→ ESTABLISHED ←→ read()/write()
  ↓                                   ↓
close()                             close()
```

**监听 Socket 和已连接 Socket 是两个不同的 Socket**，accept 返回的是新的已连接 Socket。

内核为每个 Socket 维护两个队列：已完成三次握手的（established）和未完成的（syn_rcvd）。

## UDP Socket 调用流程

UDP 无连接，不需要 listen/connect/accept，`bind` 后直接用 `sendto/recvfrom` 收发（需传入对端地址）。一个 Socket 可与多个客户端通信。

## Socket 内核实现

Socket 在 Linux 中是文件，有文件描述符。每个进程的 task_struct 中有文件描述符数组，指向内核打开文件列表。Socket 的 inode 在内存中，包含发送队列和接收队列（sk_buff 缓存）。

## 高并发方案

### 理论最大连接数

TCP 连接由四元组 `{源IP, 源端口, 目标IP, 目标端口}` 标识。理论上限 = 客户端 IP 数 × 端口数 = 2^48。

实际受限：文件描述符上限（ulimit）、内存。

### 多进程

fork() 创建子进程，复制文件描述符列表和内存空间。父子进程通过 fork 返回值区分（子进程返回 0）。子进程通过已连接 Socket 与客户端通信。

弊端：每个连接一个进程，开销大。

### 多线程

pthread_create 创建线程，共享文件描述符列表和进程空间，比进程轻量。

弊端：C10K 问题——1 万个连接需要 1 万个线程，操作系统承受不了。

### IO 多路复用（select）

一个线程维护一个文件描述符集合 fd_set，通过 select 监听。有变化时轮询所有文件描述符找到变化的。

弊端：轮询效率低，select 监听数量受 FD_SETSIZE 限制。

### IO 多路复用（epoll）

事件通知机制。epoll_create 创建 epoll 对象（也是文件），epoll_ctl 将 Socket 加入红黑树，并将回调注册到 Socket 事件列表上。事件发生时通过回调通知 epoll，无需轮询。

epoll 解决了 C10K 问题，监听数量上限为系统最大文件描述符数。
