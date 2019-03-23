package server;

import service.HelloService;
import service.impl.HelloServiceImpl;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author : ys
 * @Date : 2019/3/24 0:31 星期日
 * RPC(Remote procedure Call)
 *
 * 构建一个ServerSocket服务监听来自客户端的请求。
 *
 * 接收请求的数据。（方法名和参数）
 *
 * 根据请求的数据（方法名和参数），使用反射调用相应的服务。
 *
 * 输出服务的响应数据。
 **/
public class RpcProxyServer {
	private HelloService hello = new HelloServiceImpl();

	/**
	 * 启动代理服务器
	 * @param port
	 */
	public void publisherServer(int port) {
		try (ServerSocket ss = new ServerSocket(port)) {
			while (true) {
				// 执行代理客户端发送过来的请求，并且将请求返回
				try (Socket socket = ss.accept()) {
					try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
						String method = ois.readUTF();
						Object[] objs = (Object[]) ois.readObject();
						Class<?>[] types = new Class[objs.length];
						for (int i = 0; i < types.length; i++) {
							types[i] = objs[i].getClass();
						}
						Method m = HelloServiceImpl.class.getMethod(method, types);
						Object obj = m.invoke(hello, objs);

						try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
							oos.writeObject(obj);
							oos.flush();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
