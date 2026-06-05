import java.net.*;

/**
 * DNS 查询示例
 * 演示 Java 中的域名解析
 *
 * 运行方式:
 *   javac DnsLookup.java && java DnsLookup
 */
public class DnsLookup {

    public static void main(String[] args) {
        String[] domains = {"www.google.com", "www.baidu.com", "github.com"};

        for (String domain : domains) {
            lookup(domain);
            System.out.println();
        }
    }

    static void lookup(String host) {
        System.out.println("查询: " + host);
        try {
            InetAddress[] addresses = InetAddress.getAllByName(host);
            for (InetAddress addr : addresses) {
                String type = (addr instanceof Inet6Address) ? "IPv6" : "IPv4";
                String canonical = addr.getCanonicalHostName();
                System.out.printf("  %-4s %-20s %s%n", type, addr.getHostAddress(), canonical);
                if (!canonical.equals(host)) {
                    System.out.printf("       规范名: %s%n", canonical);
                }
            }
        } catch (UnknownHostException e) {
            System.out.println("  解析失败: " + e.getMessage());
        }
    }
}
