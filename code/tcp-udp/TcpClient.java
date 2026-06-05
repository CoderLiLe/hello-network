import java.io.*;
import java.net.*;

/**
 * TCP 客户端示例
 *
 * 运行方式:
 *   javac TcpClient.java && java TcpClient
 */
public class TcpClient {

    public static void main(String[] args) throws IOException {
        String host = "127.0.0.1";
        int port = 8080;

        System.out.println("正在连接服务器 " + host + ":" + port + "...");
        System.out.println("--- 3 次握手进行中 ---");

        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(
                     socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             BufferedReader console = new BufferedReader(
                     new InputStreamReader(System.in))) {

            System.out.println("--- 3 次握手完成 ---");
            System.out.println("已连接到服务器");

            String msg;
            while ((msg = console.readLine()) != null) {
                out.println(msg);
                System.out.println("服务器响应: " + in.readLine());
            }
        }

        System.out.println("--- 4 次挥手完成 ---");
    }
}
