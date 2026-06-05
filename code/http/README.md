# HTTP 示例

## HTTP 客户端

演示通过 TCP Socket 发送 HTTP/1.1 请求并解析响应。

```bash
# Python
python http_client.py

# Java
javac HttpClient.java && java HttpClient
```

## HTTP 服务器

演示通过 TCP Socket 解析 HTTP 请求并返回响应。

```bash
# Python
python http_server.py
# 浏览器访问 http://127.0.0.1:8888

# Java
javac HttpServer.java && java HttpServer
# 浏览器访问 http://127.0.0.1:9999
```

## HTTP/1.1 请求-响应流程

```
客户端                         服务器
  |                              |
  | -- TCP 三次握手 ----------> |
  | <-------------------------- |
  |                              |
  | -- GET / HTTP/1.1 --------> |
  |    Host: example.com        |
  |                              |
  | <-- HTTP/1.1 200 OK ----- |
  |    Content-Type: text/html  |
  |    Content-Length: 1234     |
  |                              |
  | <body data>                 |
  |                              |
  | -- TCP 四次挥手 ----------> |
  | <-------------------------- |
```

## 关键观察

1. HTTP 请求和响应都是纯文本格式
2. 请求行包含方法、路径、版本
3. 请求头以空行结束
4. Content-Length 告诉接收方响应体大小
5. HTTP 基于 TCP 传输
