# HTTP 协议

> 讲师: 李明杰 (小码哥教育)

## 概述

HTTP（Hyper Text Transfer Protocol）：超文本传输协议，是互联网中应用最广泛的应用层协议之一。

- 设计目的：提供一种发布和接收 HTML 页面的方法
- 由 URI 标识具体的资源
- 现在传输的数据格式不仅仅是 HTML

## 版本历史

| 版本 | 年份 | 主要特性 |
|------|------|----------|
| HTTP/0.9 | 1991 | 只支持 GET，只能获取文本数据 |
| HTTP/1.0 | 1996 | 支持 POST、HEAD，支持请求头/响应头，请求完成后立即断开连接 |
| HTTP/1.1 | 1997 | 支持 PUT、DELETE，持久连接 (keep-alive) |
| HTTP/2.0 | 2015 | 二进制格式、多路复用、头部压缩、服务器推送 |
| HTTP/3.0 | 2018 | 基于 QUIC (UDP) |

## HTTP 报文格式

### 整体结构
```
HTTP-message = start-line
               *(header-field CRLF)
               CRLF
               [message-body]
```

### 请求行 (Request-Line)
```
request-line = method SP request-target SP HTTP-version CRLF
```
示例：`GET /index.html HTTP/1.1`

### 状态行 (Status-Line)
```
status-line = HTTP-version SP status-code SP reason-phrase CRLF
```
示例：`HTTP/1.1 200 OK`

## URL 编码

URL 中出现特殊字符（如中文、空格）时需要进行编码。
- 浏览器地址栏输入 URL 时采用 UTF-8 编码

示例：
- 编码前：`https://www.baidu.com/s?wd=百度`
- 编码后：`https://www.baidu.com/s?wd=%E7%99%BE%E5%BA%A6`

## 请求方法

| 方法 | 说明 |
|------|------|
| GET | 读取操作，参数拼接在 URL 后面（有长度限制） |
| POST | 添加/修改/删除，参数放到请求体中（无大小限制） |
| HEAD | 与 GET 相同但没有响应体，用于获取资源信息 |
| PUT | 对已存在的资源进行整体覆盖 |
| PATCH | 对资源进行部分修改 |
| DELETE | 删除指定资源 |
| OPTIONS | 获取目的资源所支持的通信选项 |
| TRACE | 回显收到的请求，用于测试或诊断 |
| CONNECT | 创建隧道，用于 HTTPS 等 |

## 头部字段 (Header Fields)

### 请求头字段
| 字段 | 说明 |
|------|------|
| User-Agent | 浏览器的身份标识 |
| Host | 服务器的域名和端口号 |
| Referer | 前一个页面的 URL |
| Content-Type | 请求体的类型 |
| Content-Length | 请求体的长度 |
| Accept | 能够接受的响应内容类型 |
| Accept-Charset | 能够接受的字符集 |
| Accept-Encoding | 能够接受的编码方式 |
| Accept-Language | 能够接受的语言 |
| Cookie | 客户端的 Cookie 信息 |
| Range | 请求资源的部分内容 |
| Origin | 跨域请求的来源 |

### 响应头字段
| 字段 | 说明 |
|------|------|
| Server | 服务器名称 |
| Set-Cookie | 设置 Cookie |
| Content-Type | 响应体的类型 |
| Content-Length | 响应体的长度 |
| Content-Encoding | 内容编码方式 |
| Location | 重定向地址 |
| Last-Modified | 资源的最后修改时间 |
| ETag | 资源的唯一标识 |
| Cache-Control | 缓存策略 |
| Expires | 缓存过期时间 |

## 状态码 (Status Code)

### 分类
| 范围 | 类别 | 说明 |
|------|------|------|
| 100~199 | 信息响应 | 请求已接收，继续处理 |
| 200~299 | 成功响应 | 请求已成功 |
| 300~399 | 重定向 | 需要进一步操作 |
| 400~499 | 客户端错误 | 客户端请求有误 |
| 500~599 | 服务器错误 | 服务器处理出错 |

### 常见状态码
| 状态码 | 说明 |
|--------|------|
| 100 Continue | 请求的初始部分已被接收，继续发送剩余请求 |
| 200 OK | 请求成功 |
| 302 Found | 资源被临时移动，使用 Location 指定新 URL |
| 304 Not Modified | 资源未修改，使用缓存 |
| 400 Bad Request | 语法无效，无法理解请求 |
| 401 Unauthorized | 缺乏身份验证凭证 |
| 403 Forbidden | 服务器拒绝授权访问 |
| 404 Not Found | 找不到资源 |
| 405 Method Not Allowed | 请求方法不被允许 |
| 500 Internal Server Error | 服务器内部错误 |
| 502 Bad Gateway | 网关或代理收到无效响应 |
| 503 Service Unavailable | 服务器超载或停机维护 |

## Session 与 Cookie

### Cookie 机制
- 服务器通过 `Set-Cookie` 响应头设置 Cookie
- 客户端在后续请求中通过 `Cookie` 请求头携带 Cookie
- Cookie 包含 domain、path 等信息

### Session 机制
- 服务器为每个用户创建 Session 对象
- Session ID 通常通过 Cookie 传递给客户端
- 服务器根据 Session ID 找到对应的 Session 数据

## 表单提交

### enctype 属性
| 值 | 说明 |
|------|------|
| application/x-www-form-urlencoded | 默认值，用 & 分隔参数，用 = 分隔键值 |
| multipart/form-data | 文件上传时必须使用 |

## 代理服务器

### 特点
- 本身不生产内容
- 处于中间位置转发请求和响应

### 正向代理
代理对象是**客户端**。作用：
- 隐藏客户端身份
- 绕过防火墙（突破访问限制）
- Internet 访问控制
- 数据过滤

### 反向代理
代理对象是**服务器**。作用：
- 隐藏服务器身份
- 安全防护
- 负载均衡

### 代理相关的头部字段
| 字段 | 说明 |
|------|------|
| Via | 追加经过的每一台代理服务器的主机名 |
| X-Forwarded-For | 追加请求方的 IP 地址 |
| X-Real-IP | 客户端的真实 IP 地址 |

## CDN (内容分发网络)

CDN（Content Delivery Network）：利用最靠近每位用户的服务器，更快更可靠地将静态资源文件传递给用户。

- CDN 运营商在各大枢纽城市建立机房
- 部署大量高存储高带宽的节点
- 构建跨运营商、跨地域的专用网络

## HTTP 缓存

### 缓存相关响应头
| 头部 | 说明 |
|------|------|
| Pragma | HTTP/1.0 产物 |
| Expires | 缓存过期时间 (HTTP/1.0) |
| Cache-Control | 缓存策略 (HTTP/1.1) |
| Last-Modified | 资源最后修改时间 |
| ETag | 资源唯一标识（内容摘要） |

### Cache-Control 指令
| 值 | 说明 |
|------|------|
| no-storage | 不缓存数据 |
| public | 允许用户和代理服务器缓存 |
| private | 只允许用户缓存 |
| max-age | 有效时间（秒） |
| no-cache | 每次请求询问服务器 |

### 缓存相关请求头
| 头部 | 说明 |
|------|------|
| If-None-Match | ETag 的值，用于验证缓存 |
| If-Modified-Since | Last-Modified 的值，用于验证缓存 |

### 优先级
- Pragma > Cache-Control > Expires
- ETag > Last-Modified

### Ctrl + F5
强制刷新缓存。

---
