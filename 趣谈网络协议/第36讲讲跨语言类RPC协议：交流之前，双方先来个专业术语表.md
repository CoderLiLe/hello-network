# 第36讲 跨语言 RPC：gRPC

理想 RPC：二进制高性能 + 跨语言 + 协议文件严谨但可灵活更新 + 服务治理。

gRPC = Protocol Buffers（序列化）+ HTTP/2（传输）+ Envoy（服务治理）。

## Protocol Buffers

定义 .proto 文件，每个字段有唯一数字标识，压缩时只传数字不传字段名：

```protobuf
syntax = "proto3";
message Order {
  required string date = 1;
  required string classname = 2;
  required string author = 3;
  required int price = 4;
}
service PurchaseOrder {
  rpc Purchase (Order) returns (OrderResponse) {}
}
```

### 序列化技巧

- **变长整数**：每字节最高位 = 1 表示未完，= 0 表示结束，低 7 位存数据。小于 128 只需 1 字节
- **TLV 格式**：Tag = (field_num << 3) | wire_type，然后 Length，然后 Value

### 兼容性

| 修饰符 | 含义 |
|--------|------|
| required | 必须有，不能增删 |
| optional | 可选，可增删 |
| repeated | 可重复 0-N 次，可增删 |

升级策略：新增字段用 optional，先升服务端（默认值），或先升客户端（服务端忽略）。

## HTTP/2 传输

Netty Channel + HTTP/2 Stream。多路复用，多个请求分不同流，帧可乱序发送按流 ID 重组。

四种调用模式：
- **一元 RPC**：一问一答
- **服务端流式**：一问多答
- **客户端流式**：多问一答
- **双向流式**：双向独立读写

## 服务治理（Envoy / Service Mesh）

Envoy：高性能 C++ Proxy，配置四要素：
- **listener**：监听端口
- **endpoint**：目标 IP:Port
- **cluster**：相同行为的 endpoint 集合，负载均衡
- **route**：cluster 间的路由规则（如灰度 99%:1%）

动态配置通过 Discovery Service（gRPC 通信），支持热加载。

**Service Mesh**：所有服务间调用由 Envoy 代理，服务治理下沉到平台层，应用无感知。
