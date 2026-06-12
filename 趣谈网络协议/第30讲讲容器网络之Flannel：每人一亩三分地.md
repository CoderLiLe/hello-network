# 第26讲 Flannel 与 Calico

跨主机容器通信的核心问题：各物理机默认都分配 172.17.0.0/16，IP 会冲突。

## Flannel

### 核心思路

每台物理机从大网段中分一个小网段，互不冲突。例如物理机 A 用 172.17.8.0/24，物理机 B 用 172.17.9.0/24。

### UDP 模式

```
容器A → docker0 → flannel.1(TUN) → flanneld进程(UDP封装) → 物理网络 → flanneld解封装 → docker0 → 容器B
```

flanneld 打开 `/dev/net/tun` 字符设备创建 flannel.1 网卡，所有包经 flanneld 用户态进程封装/解封装。**缺点**：用户态处理，性能差。

### VXLAN 模式

不用 TUN 设备，通过 netlink 通知内核创建 VXLAN VTEP 网卡 flannel.1，封装在内核态完成，性能更好。

```
容器A → docker0 → flannel.1(VTEP,内核态VXLAN封装) → 物理网络 → flannel.1解封装 → docker0 → 容器B
```

---

## Calico

### 核心思路

不走 Overlay 隧道，直接用三层路由转发。将物理机当作路由器，按容器网段配置路由表。

```
物理机A: 172.17.9.0/24 via 192.168.100.101 dev eth0  （去B的容器）
物理机B: 172.17.8.0/24 via 192.168.100.100 dev eth0  （去A的容器）
```

### 容器内路由技巧

容器 IP 配置为 /32（单点局域网），默认网关设为 169.254.1.1：

```
default via 169.254.1.1 dev eth0
```

169.254.1.1 无实际网卡配置，Calico 直接将 veth 的 MAC 硬编码到 ARP 缓存，使包第一跳到达宿主机 veth。

### 架构组件

| 组件 | 功能 |
|------|------|
| **Felix** | 每台物理机上的 agent，自动配置路由和 iptables 规则 |
| **BIRD** | BGP Speaker，广播路由信息到全网 |
| **BGP Route Reflector** | 解决全连接复杂度，按机架分层管理路由（iBGP + eBGP） |

### 安全策略（iptables）

Calico 在 Netfilter 各节点嵌入规则：
- **cali-fip-dnat**：浮动 IP DNAT 到容器 IP
- **cali-from-wl-dispatch**：从容器发出的包匹配
- **cali-to-wl-dispatch**：发往容器的包匹配
- **cali-fip-snat**：容器 IP SNAT 为浮动 IP

### IPIP 模式（跨网段）

当物理机不在同一网段时，三层路由下一跳不是目标物理机而是中间路由器。Calico 用 IPIP 隧道解决：

```
172.17.9.0/24 via 192.168.200.101 dev tun0
```

封装格式：内层（容器源IP → 容器目标IP）+ 外层（物理机A IP → 物理机B IP），通过隧道跨越中间路由器。

## Flannel vs Calico 对比

| | Flannel | Calico |
|----|----|----|
| 数据面 | Overlay（VXLAN/UDP） | 纯路由转发，性能更好 |
| 网络策略 | 不支持 | 支持 Network Policy |
| 跨网段 | 天然支持（Overlay） | 需 IPIP 模式 |
| 复杂度 | 简单 | 较复杂（需 BGP） |
