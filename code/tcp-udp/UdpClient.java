import java.net.*;

/**
 * UDP 客户端示例
 *
 * 运行方式:
 *   javac UdpClient.java && java UdpClient
 */
public class UdpClient {

    public static void main(String[] args) throws Exception {
        String host = "127.0.0.1";
        int port = 8888;

        System.out.println("UDP 客户端启动, 无需连接, 直接发送数据");
        System.out.println("UDP 没有 3 次握手, 也没有 4 次挥手");

        DatagramSocket socket = new DatagramSocket();

        String msg = "Hello UDP!";
        byte[] data = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(
                data, data.length, InetAddress.getByName(host), port);

        socket.send(packet);
        System.out.println("发送: " + msg);

        // 接收回复
        byte[] buf = new byte[1024];
        DatagramPacket resp = new DatagramPacket(buf, buf.length);
        socket.receive(resp);
        System.out.println("收到: " + new String(resp.getData(), 0, resp.getLength()));

        socket.close();
    }
}
