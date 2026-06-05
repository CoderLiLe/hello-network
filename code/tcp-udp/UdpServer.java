import java.net.*;

/**
 * UDP 服务器示例
 * 演示 UDP 无连接通信
 *
 * 运行方式:
 *   javac UdpServer.java && java UdpServer
 */
public class UdpServer {

    public static void main(String[] args) throws Exception {
        int port = 8888;
        DatagramSocket socket = new DatagramSocket(port);
        System.out.println("UDP Server 启动, 监听端口: " + port);
        System.out.println("UDP 是无连接的, 不需要 3 次握手");

        byte[] buf = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);

        socket.receive(packet);
        String msg = new String(packet.getData(), 0, packet.getLength());
        System.out.println("收到: " + msg + " 来自: " + packet.getAddress());

        // 回复
        byte[] resp = ("ECHO: " + msg).getBytes();
        DatagramPacket respPacket = new DatagramPacket(
                resp, resp.length, packet.getAddress(), packet.getPort());
        socket.send(respPacket);

        socket.close();
    }
}
