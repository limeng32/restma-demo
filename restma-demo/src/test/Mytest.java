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

		// ��ȡSpring�������Ļ���

		ctx = new FileSystemXmlApplicationContext("sys/myspring.xml");

		// �������Ļ����л�ȡmyBean

		ShowMessage sm = (ShowMessage) ctx.getBean("myBean");

		// ����ShowMessage��show���������Ϣ

		sm.show();

	}

}