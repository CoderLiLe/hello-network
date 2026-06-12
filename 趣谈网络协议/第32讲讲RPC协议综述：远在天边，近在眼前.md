# 第29讲 RPC 协议综述

远程调用（Remote Procedure Call）让调用远端服务像调用本地函数一样。Bruce Jay Nelson 的论文 *Implementing Remote Procedure Calls* 定义了 RPC 标准模型。

## RPC 五问题

| 问题 | 说明 |
|------|------|
| 协议约定 | 如何规定语法？add 用字符串还是整数表示？返回值是什么？ |
| 参数传递 | 参数顺序？TCP 流如何分界？ |
| 数据表示 | 变长类型、结构体怎么办？大端小端？ |
| 服务发现 | 服务端监听的随机端口如何找到？ |
| 传输可靠性 | 丢包、重传、崩溃重启怎么办？ |

## RPC 三层模型

```
客户端应用 → Stub(编码/解码) → RPCRuntime(传输) → 网络
                                                      ↓
服务端逻辑 ← Stub(解码/编码) ← RPCRuntime(传输) ← 网络
```

- **用户层**：专注业务逻辑
- **Stub 层**：处理语法、语义、封装、解封装
- **RPCRuntime 层**：高性能传输、网络错误处理

## ONC RPC（Sun RPC）

最早商业化 RPC 实现，用于 NFS（网络文件系统）。

**XDR**（外部数据表示法）：标准数据压缩格式，封装基本类型和结构体。

RPC 调用格式：XID（唯一标识请求/回复对）→ RPC 版本号 → 程序编号 → 程序版本 → 方法编号 → 认证鉴权 → 参数列表。

匹配失败依次返回：RPC_MISMATCH、PROG_UNAVAIL、PROG_MISMATCH、PROC_UNAVAIL、GARBAGE_ARGS。

协议定义文件 → 工具生成客户端/服务端 Stub 程序。

**传输层**：ONC RPC 类库实现状态机，处理连接失败、重试、超时等场景，支持异步模型。

**服务发现**：portmapper 监听知名端口，RPC 程序启动时注册，客户端先查 portmapper 获取端口再建立连接。
