import java.io.*;
import java.net.*;

/**
 * 简单的 HTTP 服务器示例
 * 展示如何通过 TCP Socket 处理 HTTP 请求
 *
 * 运行方式:
 *   javac HttpServer.java && java HttpServer
 *   浏览器访问 http://127.0.0.1:9999
 */
public class HttpServer {

    public static void main(String[] args) throws IOException {
        int port = 9999;
        ServerSocket server = new ServerSocket(port);
        System.out.println("HTTP Server 启动于 http://127.0.0.1:" + port);
        System.out.println("按 Ctrl+C 停止\n");

        while (true) {
            try (Socket client = server.accept();
                 BufferedReader in = new BufferedReader(
                         new InputStreamReader(client.getInputStream()));
                 PrintWriter out = new PrintWriter(
                         client.getOutputStream(), true)) {

                String requestLine = in.readLine();
                if (requestLine == null) continue;
                System.out.println("请求: " + requestLine);

                String body = "<!DOCTYPE html><html><head><meta charset='utf-8'>" +
                        "<title>Hello Network</title></head><body>" +
                        "<h1>Hello Network!</h1>" +
                        "<p>请求: " + requestLine + "</p>" +
                        "<hr><h2>请求头</h2><pre>";

                String line;
                while ((line = in.readLine()) != null && !line.isEmpty()) {
                    body += line + "\n";
                }
                body += "</pre></body></html>";

                out.print("HTTP/1.1 200 OK\r\n" +
                         "Content-Type: text/html; charset=utf-8\r\n" +
                         "Content-Length: " + body.getBytes().length + "\r\n" +
                         "Connection: close\r\n" +
                         "\r\n" +
                         body);
                out.flush();
            } catch (Exception e) {
                System.out.println("处理请求出错: " + e.getMessage());
            }
        }
    }
}
