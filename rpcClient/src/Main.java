import client.RpcProxyClient;
import service.HelloService;

/**
 * @Author : ys
 * @Date : 2019/3/24 0:36 星期日
 **/
public class Main {
	public static void main(String[] args) {
		RpcProxyClient client = new RpcProxyClient();
		HelloService helloService = client.proxyClient(HelloService.class,"localhost",9999);
		String s = helloService.sayHello("welcome to rpc");
		System.out.println(s);
	}
}
