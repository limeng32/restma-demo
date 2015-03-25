package test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * 
 * @author guoliang.liu
 * 
 * @CreateDate Jun 28, 2007
 * 
 * 
 * 
 */

public class Mytest {

	private static ApplicationContext ctx;

	public static void main(String argv[]) {

		// 获取Spring的上下文环境

		ctx = new FileSystemXmlApplicationContext("sys/myspring.xml");

		// 从上下文环境中获取myBean

		ShowMessage sm = (ShowMessage) ctx.getBean("myBean");

		// 调用ShowMessage的show方法输出消息

		sm.show();

	}

}