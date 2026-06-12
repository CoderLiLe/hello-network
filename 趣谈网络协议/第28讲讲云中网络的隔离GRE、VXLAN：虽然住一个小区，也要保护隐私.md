# 第28讲 网络隔离：GRE/VXLAN

VLAN 只有 12 位（4096 个 ID），云平台规模远超此限制。Overlay 网络在原有包头基础上扩展出新头，包含足够区分租户的 ID。

- **Underlay**：底层物理网络设备
- **Overlay**：基于物理网络的虚拟化网络

## GRE

GRE（Generic Routing Encapsulation）是 IP-over-IP 隧道技术。GRE 头中 Key 字段（32 位）存放 Tunnel ID 区分用户。NVGRE 给网络 ID 24 位。

```
内层IP → GRE头 → 外层IP → 物理网络 → 对端解封装
```

**不足**：点对点隧道导致数量指数增长；不支持组播；部分防火墙无法解析 GRE。

## VXLAN

VXLAN 在二层外套 VXLAN 头（VXLAN ID 24 位），外层封装 UDP + IP + MAC。

**VTEP**（VXLAN Tunnel Endpoint）：封装/解封装端点，每台物理机上一个。

### 通信流程

1. VTEP 启动时通过 IGMP 加入组播组
2. VM1 发 ARP → VTEP1 组播询问 → VTEP2 本地广播 → VM2 响应
3. VTEP1 学到 VM2→VTEP2，VTEP2 学到 VM1→VTEP1
4. 后续通信直接单播，无需组播

## OVS 隧道实现

OVS 支持 GRE、VXLAN、IPsec_GRE。通过 br1 将虚拟机互联和物理机互联分层。

流表设计（设置在 br1 上，3 个端口：port1 对内，port2/3 对外）：

| 表 | 功能 |
|----|------|
| Table 0 | 入口分流：port1 出方向→Table1，port2/3 入方向→Table3 |
| Table 1 | 区分单播（→Table20）和多播（→Table21） |
| Table 3 | Tunnel ID → VLAN ID 映射 |
| Table 10 | MAC 地址学习，结果写入 Table 20 |
| Table 20 | MAC Learning Table，命中走单播，未命中→Table21 |
| Table 21 | VLAN ID → Tunnel ID，多端口组播输出 |
