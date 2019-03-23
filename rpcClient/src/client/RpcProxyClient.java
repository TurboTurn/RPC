package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * @Author : ys
 * @Date : 2019/3/24 0:33 星期日
 **/
public class RpcProxyClient {
	/**
	 * 创建代理对象
	 *
	 * @param clazz 要进行代理的接口
	 * @param host  服务器所在的地址
	 * @param port  服务器所提供的端口
	 * @param <T>   接口类
	 * @return 接口的代理对象
	 */
	public <T> T proxyClient(Class<T> clazz, String host, int port) {
		return (T) clazz.cast(Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {

			/**
			 * 代理方法调用，用于从服务端获取调用结果
			 */
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

				try (Socket socket = new Socket(host, port)) {
					try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
						oos.writeUTF(method.getName());
						oos.writeObject(args);
						oos.flush();

						try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
							return ois.readObject();
						}
					}
				}
			}
		}));
	}
}
