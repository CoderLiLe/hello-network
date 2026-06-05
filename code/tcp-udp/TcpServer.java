import java.io.*;
import java.net.*;

/**
 * TCP 服务器示例
 * 演示 TCP 三次握手、数据传输过程
 *
 * 运行方式:
 *   javac TcpServer.java && java TcpServer
 */
public class TcpServer {

    public static void main(String[] args) throws IOException {
        int port = 8080;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("TCP Server 启动, 监听端口: " + port);
        System.out.println("等待客户端连接... (3 次握手)");

        try (Socket socket = serverSocket.accept();
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(
                     socket.getOutputStream(), true)) {

            System.out.println("客户端已连接: " + socket.getInetAddress());
            System.out.println("--- 3 次握手完成 ---");

            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("收到: " + line);
                out.println("ECHO: " + line);
            }

            System.out.println("客户端断开连接");
            System.out.println("--- 4 次挥手完成 ---");
        }
    }
}
