# 第34讲 RESTful 接口（JSON）

SOAP 的 S（Simple）名不副实。HTTP 本身有 GET/POST/PUT/DELETE 对应 CRUD，不必全用 POST。

## 从 XML 到 JSON

```json
{ "order": { "date": "2018-07-01", "className": "趣谈网络协议", "Author": "刘超", "price": "68" } }
```

RESTful 不仅是 JSON API，而是一种**架构风格**（Representational State Transfer）。

## 核心设计原则

### 无状态

- **服务端维护资源状态**（订单、库存等持久化数据）
- **客户端维护会话状态**（浏览到哪一页、当前目录等）

好处：服务端可横向扩展，资源状态不变时可缓存到 CDN 边缘节点。

### 以资源为核心（非过程）

客户端告诉服务端资源最终状态，而非过程/动作。例如：设置目标库存数，而非"减去 N 个库存"。

### 幂等

网络不稳定需重试，多次调用结果应相同。不能支付一次变三次。

### 对比

| | SOAP | RESTful |
|----|------|----|
| 正文 | XML，可放任意动作 | JSON，描述资源状态 |
| 动作 | POST 包办一切 | GET/POST/PUT/DELETE |
| 状态 | 服务端维护 | 客户端维护会话，服务端维护资源 |

## Spring Cloud + Eureka

Eureka 做注册中心，服务提供方注册，消费方获取列表。RestTemplate 封装 REST 调用。
