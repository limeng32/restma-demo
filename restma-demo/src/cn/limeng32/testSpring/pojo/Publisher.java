package cn.limeng32.testSpring.pojo;

import java.io.Serializable;

public class Publisher extends PojoSupport<Publisher> implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
