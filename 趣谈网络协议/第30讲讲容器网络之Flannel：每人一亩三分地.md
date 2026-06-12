# 第30讲 Flannel 与 Calico

跨主机容器通信核心问题：各物理机默认都分配 172.17.0.0/16，IP 冲突。

## Flannel

**思路**：每台物理机从大网段分小网段，互不冲突（如 A 用 172.17.8.0/24，B 用 172.17.9.0/24）。

**UDP 模式**：容器 → docker0 → flannel.1(TUN) → flanneld 用户态 UDP 封装 → 物理网络 → 对端 flanneld 解封装 → docker0。缺点：用户态处理，性能差。

**VXLAN 模式**：netlink 通知内核创 VXLAN VTEP 网卡 flannel.1，内核态封装，性能更好。

## Calico

**思路**：不走 Overlay，直接用三层路由转发，物理机当路由器：

```
物理机A: 172.17.9.0/24 via 192.168.100.101 dev eth0
物理机B: 172.17.8.0/24 via 192.168.100.100 dev eth0
```

**路由技巧**：容器 IP 配 /32（单点局域网），默认网关 169.254.1.1。Calico 将 veth MAC 硬编码到 ARP 缓存，使包第一跳直达宿主机 veth。

**架构组件**：

| 组件 | 功能 |
|------|------|
| Felix | 每物理机 agent，自动配置路由和 iptables |
| BIRD | BGP Speaker，广播路由信息到全网 |
| Route Reflector | 解决全连接，按机架分层（iBGP+eBGP） |

**安全策略**：在 Netfilter 各节点嵌入 cali-fip-dnat / cali-from-wl-dispatch / cali-to-wl-dispatch / cali-fip-snat。

**IPIP 模式**：物理机跨网段时，三层下一跳不是目标而是中间路由器。Calico 打 IPIP 隧道：`172.17.9.0/24 via 192.168.200.101 dev tun0`，内层容器 IP + 外层物理机 IP。

## 对比

| | Flannel | Calico |
|----|----|----|
| 数据面 | Overlay（VXLAN/UDP） | 纯路由转发 |
| 网络策略 | 不支持 | 支持 Network Policy |
| 跨网段 | 天然支持 | 需 IPIP 模式 |
| 复杂度 | 简单 | 较复杂（需 BGP） |
