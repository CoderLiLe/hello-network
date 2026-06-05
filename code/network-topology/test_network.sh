#!/bin/bash
# 容器网络连通性测试脚本
# 使用方式: bash test_network.sh

echo "========================================"
echo "  容器网络拓扑 - 连通性测试"
echo "========================================"
echo ""

echo "1. 测试 Client → Web (同 frontend 网络)"
echo "----------------------------------------"
docker compose exec client ping -c 2 web && echo "  ✅ 连通" || echo "  ❌ 不通"

echo ""
echo "2. 测试 Client → API (跨网络, 默认不通)"
echo "----------------------------------------"
docker compose exec client ping -c 2 api 2>&1 || echo "  ✅ 预期不通 (跨网络隔离)"

echo ""
echo "3. 测试 Web → API (同 backend 网络)"
echo "----------------------------------------"
docker compose exec web ping -c 2 api && echo "  ✅ 连通" || echo "  ❌ 不通"

echo ""
echo "4. 测试 Web → Cache (同 backend 网络)"
echo "----------------------------------------"
docker compose exec web ping -c 2 cache && echo "  ✅ 连通" || echo "  ❌ 不通"

echo ""
echo "5. 验证 HTTP 服务"
echo "----------------------------------------"
STATUS=$(curl -s -o /dev/null -w "%{http_code}" http://127.0.0.1:8080)
echo "   Web (localhost:8080): HTTP $STATUS"

echo ""
echo "========================================"
echo "  网络拓扑说明"
echo "========================================"
echo ""
echo "  [Client] ←frontend→ [Web] ←backend→ [API, Cache]"
echo ""
echo "  frontend: client 和 web 互通"
echo "  backend:  web、api、cache 互通"
echo "  frontend ↔ backend: 不通 (需通过 Web 代理)"
