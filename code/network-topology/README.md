# 容器网络拓扑演示

## 网络架构

```
[Client] ←frontend→ [Web] ←backend→ [API, Cache]
```

- **frontend 网络**: client ↔ web 互通
- **backend 网络**: web ↔ api ↔ cache 互通
- **跨网络隔离**: client 无法直接访问 api/cache

## 使用方式

```bash
# 启动所有容器
docker compose up -d

# 查看网络
docker network ls
docker network inspect code_frontend
docker network inspect code_backend

# 查看容器 IP
docker compose ps
docker compose exec web ip addr

# 测试连通性
bash test_network.sh

# 验证 Web 服务
curl http://127.0.0.1:8080

# 停止并清理
docker compose down
```

## 学习要点

1. Docker bridge 网络的隔离与通信
2. 多网络接口容器（Web 同时接入 frontend 和 backend）
3. 跨网络通信必须经过网关
4. 与真实数据中心网络架构的类比（前端层/后端层隔离）
