# HTTPS

> 讲师: 李明杰 (小码哥教育)

## 概述

HTTPS（HyperText Transfer Protocol Secure）：超文本传输安全协议。

- 常称为 HTTP over TLS、HTTP over SSL、HTTP Secure
- 由网景公司于 1994 年首次提出
- 默认端口号：**443**（HTTP 是 80）
- 在 HTTP 基础上使用 SSL/TLS 加密报文

## SSL / TLS

TLS（Transport Layer Security）：传输层安全性协议，前身是 SSL（Secure Sockets Layer，安全套接层）。

### 版本历史
| 版本 | 年份 | 状态 |
|------|------|------|
| SSL 1.0 | - | 因严重漏洞，从未公开 |
| SSL 2.0 | 1995 | 2011 年弃用 |
| SSL 3.0 | 1996 | 2015 年弃用 |
| TLS 1.0 | 1999 | RFC 2246 |
| TLS 1.1 | 2006 | RFC 4346 |
| TLS 1.2 | 2008 | RFC 5246 |
| TLS 1.3 | 2018 | RFC 8446 |

### 工作层次
SSL/TLS 位于传输层和应用层之间，为应用层提供加密传输服务。

## OpenSSL

OpenSSL 是 SSL/TLS 协议的开源实现，始于 1998 年。

### 常用命令
| 命令 | 说明 |
|------|------|
| `openssl genrsa -out mj.key` | 生成私钥 |
| `openssl rsa -in mj.key -pubout -out mj.pem` | 生成公钥 |
| 自签名证书 | 使用 OpenSSL 构建自己的 CA |

## HTTPS 的通信过程

### 三大阶段
1. **TCP 的 3 次握手**
2. **TLS 的连接**（约 10 个步骤）
3. **HTTP 请求和响应**

### TLS 连接详细步骤

**① Client Hello**
- TLS 版本号
- 支持的加密组件列表
- 客户端随机数 (Client Random)

**② Server Hello**
- TLS 版本号
- 选择的加密组件
- 服务器随机数 (Server Random)

**③ Certificate**
- 服务器的公钥证书（被 CA 签名）

**④ Server Key Exchange**
- ECDHE 算法的参数 (Server Params)，经过服务器私钥签名

**⑤ Server Hello Done**
- 告知客户端协商部分结束

**⑥ Client Key Exchange**
- ECDHE 算法的参数 (Client Params)

此时，双方使用 ECDHE 计算出 Pre-master secret，再结合 Client Random、Server Random 生成主密钥，进而衍生出会话密钥。

**⑦ Change Cipher Spec**
- 告知服务器后续通信采用会话密钥加密

**⑧ Finished**
- 包含全部报文的校验值，加密后发送

**⑨ Change Cipher Spec**（服务器→客户端）
**⑩ Finished**（服务器→客户端）

## HTTPS 的成本
- **证书费用**：需要向 CA 购买证书
- **加解密计算**：消耗服务器资源
- **降低访问速度**：加密解密需要时间

> 有些企业的做法：包含敏感数据的请求使用 HTTPS，其他保持使用 HTTP。

## Wireshark 解密 HTTPS

1. 设置环境变量 `SSLKEYLOGFILE`（浏览器将 key 信息导出到此文件）
2. 重启操作系统
3. 在 Wireshark 中设置：编辑 → 首选项 → Protocols → TLS → 选择 key 文件

## 服务器配置 HTTPS

### Tomcat 配置
1. 使用 JDK 的 keytool 生成证书
   ```
   keytool -genkeypair -alias mj -keyalg RSA -keystore F:/mj.jks
   ```
2. 将 `.jks` 文件放到 `TOMCAT_HOME/conf` 目录
3. 修改 `TOMCAT_HOME/conf/server.xml` 中的 Connector 配置

---
