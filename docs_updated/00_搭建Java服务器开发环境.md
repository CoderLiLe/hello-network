# 搭建Java服务器开发环境

> 讲师: 李明杰 (小码哥教育)

## 前提条件

- 下载安装 JDK（建议至少使用 JDK8）
- 下载安装 IntelliJ IDEA Ultimate（必须安装旗舰版，社区版没有 Web 项目功能）
- 下载解压 Tomcat

## JDK 安装与配置

### 安装步骤
1. 下载 JDK（建议使用 JDK8 或更高版本）
2. 安装路径建议不要包含中文等特殊字符
3. 配置环境变量
   - 配置 `JAVA_HOME`
   - 配置 `Path`

### Java 跨平台原理
Java 的跨平台特性：**一次编译，到处运行**
- 编译生成与平台无关的字节码文件（`.class` 文件）
- 由对应平台的 JVM 解析字节码为机器指令

```
*.java → 编译 → *.class → JVM(Windows/Mac/Linux) → 机器指令
```

**JVM（Java Virtual Machine）**：Java 虚拟机，是 Java 跨平台的核心。

## Tomcat 服务器

### 安装
1. 下载 Tomcat（建议 Tomcat 9+）
2. 解压路径建议不要包含中文等特殊字符

### 部署项目
将 Web 项目打包成 `.war` 文件，放入 Tomcat 的 `webapps` 目录，启动 Tomcat 即可自动部署。

## IntelliJ IDEA 配置

### 创建 Web 项目
1. **新建一个空的项目**
2. **添加 Java 模块**
3. **添加 Web 模块**
   - 在模块上右键 → Add Framework Support → Web Application
4. **部署项目到 Tomcat**
   - Run → Edit Configurations → 添加 Tomcat Server
   - 选择本地 Tomcat 路径
   - Deployment 中添加要部署的 artifact
5. **添加 Servlet 支持**
   - 添加 Servlet API 依赖
   - 创建 Servlet 类
   - 配置 web.xml 或使用注解

## 客户端-服务器架构

```
浏览器(客户端) ←→ Tomcat(服务器) ←→ Java 代码
```

- **客户端**：浏览器（HTML + CSS + JS）
- **服务器**：Java + Tomcat
- **请求流程**：客户端发送 HTTP 请求 → Tomcat 接收 → Java 代码处理 → 返回响应

## 开发工具

| 工具 | 用途 |
|------|------|
| Chrome/Firefox | 浏览器调试 |
| Fiddler/Wireshark | 网络抓包 |
| Xshell | 终端模拟（Windows） |
| Packet Tracer | 网络模拟 |
| GNS3 | 网络模拟器 |

---
