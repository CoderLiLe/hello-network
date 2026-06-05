# TLS/HTTPS 示例

演示 TLS 握手过程和 HTTPS 请求。

```bash
python tls_client.py
```

## TLS 握手流程

```
客户端                         服务器
  |                              |
  | -- ClientHello -----------> |
  |    支持的 TLS 版本           |
  |    加密套件列表              |
  |                              |
  | <-- ServerHello ----------- |
  |    选定的 TLS 版本           |
  |    选定的加密套件            |
  |    服务器证书                |
  |                              |
  | -- 验证服务器证书 ---------> |
  |                              |
  | -- 生成 Pre-Master Secret   |
  | -- 用公钥加密发送 ---------> |
  |                              |
  | <-- 会话密钥协商完成 ------- |
  |                              |
  | -- [加密] HTTP 请求 ------> |
  | <-- [加密] HTTP 响应 ----- |
  |                              |
```

## 关键概念

1. **证书验证**: 客户端验证服务器证书是否由受信任 CA 签发
2. **密钥交换**: 使用非对称加密安全传递对称密钥
3. **对称加密**: 后续数据传输使用对称加密（效率高）
4. **完整性校验**: MAC 确保数据未被篡改

## 输出示例

```
  TLS 版本: TLSv1.3
  加密套件: TLS_AES_256_GCM_SHA384
  证书主体: CN=www.baidu.com, O=Beijing Baidu Netcom...
  签发者: CN=DigiCert Secure Site CN CA...
  生效: Mar 23 00:00:00 2026 GMT
  到期: Apr 23 23:59:59 2027 GMT
```
