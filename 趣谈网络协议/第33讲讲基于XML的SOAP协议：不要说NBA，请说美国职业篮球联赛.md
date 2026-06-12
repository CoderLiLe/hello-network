# 第33讲 SOAP 协议（XML）

## ONC RPC 的问题

二进制压缩方式的问题：
- 压缩格式必须完全一致，多一位少一位都失败
- 协议修改不灵活，加字段需双方同时升级
- 版本管理困难，50 个客户端因一个需求全部需改动
- 面向函数而非面向对象

根源：二进制压缩像说缩略语"NBA"，陌生人听不懂。文本方式像说"美国职业篮球赛"，谁都能懂。

## XML → SOAP

SOAP（简单对象访问协议）用 XML 编写请求和回复，HTTP 传输。信封模式：

```xml
<soap:Envelope>
  <soap:Header>  <!-- 头部信息（如事务ID） --> </soap:Header>
  <soap:Body>    <!-- 正文（业务数据） --> </soap:Body>
</soap:Envelope>
```

优势：格式不必完全一致（字段顺序随意），加字段只需加一行，旧客户端不解析即可，面向对象表述。

## 三问题解决

**协议约定** — WSDL（Web Service 描述语言）：XML 格式，定义类型、message、portType、binding、service。通过 `?wsdl` 获取，可用工具生成客户端 Stub。

**传输** — HTTP POST 发送 SOAP XML 正文，Content-Type: application/soap+xml。

**服务发现** — UDDI 注册中心：服务提供方发布 WSDL，使用方查找并封装本地客户端。
