# 第14讲 HTTP协议

## HTTP 请求与响应

### 请求报文

```
GET /index.html HTTP/1.1        ← 请求行（方法 + URL + 版本）
Host: www.163.com               ← 首部字段
Accept-Charset: utf-8
Cache-Control: max-age=0
                                ← 空行
（正文实体，GET通常为空）
```

常用方法：
- **GET**：获取资源
- **POST**：提交数据（创建资源）
- **PUT**：修改资源
- **DELETE**：删除资源

### 响应报文

```
HTTP/1.1 200 OK                  ← 状态行（版本 + 状态码 + 短语）
Content-Type: text/html
Cache-Control: max-age=3600
                                ← 空行
<html>...</html>                 ← 正文
```

常见状态码：200 OK、301/302 重定向、304 Not Modified、404 Not Found、503 Service Unavailable。

### 缓存控制

- **Cache-Control: max-age=N**：缓存有效期
- **If-Modified-Since**：条件请求，资源未修改返回 304
- 架构：客户端 → Nginx/Varnish 缓存层 → Tomcat 应用集群

## 完整请求过程

1. 浏览器解析 URL，DNS 获取 IP
2. 建立 TCP 连接（HTTP/1.1 默认 Keep-Alive 复用连接）
3. 发送 HTTP 请求报文
4. TCP 层分段发送，IP 层路由，MAC 层逐跳转发
5. 服务端解封装 → HTTP 服务器处理 → 返回响应
6. 客户端渲染页面

## HTTP/2.0

改进：
- **头部压缩**：两端建立索引表，相同头只发索引
- **多路复用**：一个 TCP 连接切分为多个 stream，每个 stream 独立传输，解决队首阻塞
- **二进制分帧**：传输内容拆为帧，乱序发送，按流 ID 重组
- **优先级**：stream 有优先级，可优先处理关键资源（CSS > 图片）

## QUIC：基于 UDP 的下一代协议

HTTP/2.0 仍基于 TCP，TCP 层面的丢包会导致所有 stream 阻塞。QUIC 在 UDP 上实现：

1. **自定义连接**：64 位随机数作为连接 ID，IP/端口变化无需重新握手（支持移动网络切换）
2. **精确重传**：单调递增序列号 + offset 偏移量，RTT 计算更准确
3. **无阻塞多路复用**：各 stream 独立，一个 stream 丢包不影响其他
4. **自定义流量控制**：基于 offset 的窗口更新，比 TCP 滑动窗口更灵活
