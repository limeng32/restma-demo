package cn.limeng32.testSpring.pojo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@")
public class Article2 implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	private String title;

	private String content;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updateTime = new Date();

	public Article2() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public boolean belongs(Collection<Article> c) {
		boolean ret = false;
		for (Article i : c) {
			if (this.getId() == i.getId()) {
				ret = true;
				break;
			}
		}
		return ret;
	}

	public void quit(Collection<Article> c) {
		for (Article i : c) {
			if (this.getId() == i.getId()) {
				c.remove(i);
				break;
			}
		}
	}

}
