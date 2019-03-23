package service.impl;

import service.HelloService;

/**
 * @Author : ys
 * @Date : 2019/3/24 0:26 星期日
 **/
public class HelloServiceImpl implements HelloService {
	@Override
	public String sayHello(String info) {
		String result = "hello: " + info;
		System.out.println(result);
		return result;
	}
}
