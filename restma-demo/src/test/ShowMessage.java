package test;

/**
 * 
 * @author guoliang.liu
 * 
 * @CreateDate Jun 28, 2007
 * 
 * 
 * 
 */

public class ShowMessage {

	// 消息对象

	private String message;

	public void setMessage(String message) {

		this.message = message;

	}

	public String getMessage() {

		return this.message;

	}

	// 发送消息方法

	public void show() {

		System.out.print("---Message---" + getMessage());

	}

}