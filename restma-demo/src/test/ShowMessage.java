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

	// ��Ϣ����

	private String message;

	public void setMessage(String message) {

		this.message = message;

	}

	public String getMessage() {

		return this.message;

	}

	// ������Ϣ����

	public void show() {

		System.out.print("---Message---" + getMessage());

	}

}