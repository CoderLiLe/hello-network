import java.io.*;
import java.net.*;

/**
 * HTTP 客户端示例
 * 展示通过 TCP Socket 发送 HTTP 请求和接收响应
 *
 * 运行方式:
 *   javac HttpClient.java && java HttpClient
 */
public class HttpClient {

    public static void main(String[] args) throws IOException {
        String host = "httpbin.org";
        int port = 80;

        System.out.println("正在连接 " + host + ":" + port + "...");
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(
                     socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()))) {

            System.out.println("--- TCP 连接已建立 ---\n");

            out.print("GET /get HTTP/1.1\r\n" +
                     "Host: " + host + "\r\n" +
                     "User-Agent: hello-network/1.0\r\n" +
                     "Accept: */*\r\n" +
                     "Connection: close\r\n" +
                     "\r\n");
            out.flush();

            System.out.println("响应:");
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        }
        System.out.println("\n--- 连接已关闭 ---");
    }
}
