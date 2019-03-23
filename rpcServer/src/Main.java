import server.RpcProxyServer;

/**
 * @Author : ys
 * @Date : 2019/3/24 0:35 星期日
 **/
public class Main {
	public static void main(String[] args) {
		RpcProxyServer server = new RpcProxyServer();
		server.publisherServer(9999);
	}
}
