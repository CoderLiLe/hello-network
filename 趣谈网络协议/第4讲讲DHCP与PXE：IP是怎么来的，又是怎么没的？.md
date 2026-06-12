# 第4讲 DHCP与PXE

## 动态主机配置协议（DHCP）

手动配 IP 麻烦且易错（配错网段包根本发不出去）。DHCP 实现自动配置。

### 工作流程（四次交互）

1. **DHCP Discover**：新机器用 `0.0.0.0` 广播到 `255.255.255.255`（"我来啦，有人吗？"）
2. **DHCP Offer**：DHCP Server 广播回应，提供可用 IP、子网掩码、网关、租期
3. **DHCP Request**：客户端选择 Offer（通常选最先到达的），广播告知所有 Server 自己的选择
4. **DHCP ACK**：Server 确认，租约达成

### 续租与回收

- 租期过半时，客户端直接向 Server 发送 DHCP Request 续租
- 租期到期未续租，IP 被回收

## 预启动执行环境（PXE）

数据中心批量部署裸机时，逐台手动装系统不现实。PXE 通过**网络启动并安装操作系统**。

### 工作过程

1. BIOS 启动 PXE 客户端，通过 DHCP 获取 IP 和 PXE 服务器地址（DHCP 配置中的 `next-server`）
2. PXE 客户端通过 TFTP 从 PXE 服务器下载启动文件 `pxelinux.0`
3. 执行启动文件，再请求配置文件 `pxelinux.cfg`，获取内核和 initramfs 位置
4. 下载内核和 initramfs，启动 Linux 内核

关键：**DHCP 不仅分配 IP，还通过 `next-server` 和 `filename` 告知 PXE 服务器地址和启动文件名。**
