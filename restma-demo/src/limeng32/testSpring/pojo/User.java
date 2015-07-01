package limeng32.testSpring.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;

import limeng32.mybatis.plugin.mapper.annotation.FieldMapperAnnotation;
import limeng32.mybatis.plugin.mapper.annotation.PersistentFlagAnnotation;
import limeng32.mybatis.plugin.mapper.annotation.TableMapperAnnotation;
import limeng32.testSpring.annotation.Status;

import org.apache.ibatis.type.JdbcType;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@")
@TableMapperAnnotation(tableName = "user")
public class User extends PojoSupport<User> implements Serializable {

	private static final long serialVersionUID = 1L;

	@FieldMapperAnnotation(dbFieldName = "id", jdbcType = JdbcType.INTEGER, isUniqueKey = true)
	private int id;

	@NotBlank
	@FieldMapperAnnotation(dbFieldName = "name", jdbcType = JdbcType.VARCHAR)
	private String name;

	@FieldMapperAnnotation(dbFieldName = "address", jdbcType = JdbcType.VARCHAR)
	private String address;

	@FieldMapperAnnotation(dbFieldName = "nickname", jdbcType = JdbcType.VARCHAR)
	private String nickname;

	@Max(10000)
	@Min(8000)
	private Integer salary;

	@Status
	private String state;

	private String sex;

	@Past
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date birthday;

	private java.util.Collection<Article> article;

	private java.util.Collection<Publisher> publisher;

	public User() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void addArticle(Article newArticle) {
		if (newArticle == null)
			return;
		if (this.article == null)
			this.article = new java.util.LinkedHashSet<Article>();
		if (!this.article.contains(newArticle)) {
			if (newArticle.getById(this.article) != null) {
				removeArticle(newArticle.getById(this.article));
			}
			this.article.add(newArticle);
			newArticle.setUser(this);
		}
	}

	@JsonIgnore
	@JSONField(serialize = false)
	public java.util.Iterator<Article> getIteratorArticle() {
		if (article == null)
			article = new java.util.LinkedHashSet<Article>();
		return article.iterator();
	}

	public java.util.Collection<Article> getArticle() {
		if (article == null)
			article = new java.util.LinkedHashSet<Article>();
		return article;
	}

	public void removeAllArticle() {
		if (article != null) {
			Article oldArticle;
			for (java.util.Iterator<Article> iter = getIteratorArticle(); iter
					.hasNext();) {
				oldArticle = iter.next();
				iter.remove();
				oldArticle.setUser(null);
			}
		}
	}

	public void removeArticle(Article oldArticle) {
		if (oldArticle == null)
			return;
		if (this.article != null)
			if (oldArticle.belongs(this.article)) {
				oldArticle.quit(this.article);
				oldArticle.setUser(null);
			}
	}

	public void setArticle(java.util.Collection<Article> newArticle) {
		removeAllArticle();
		for (java.util.Iterator<Article> iter = newArticle.iterator(); iter
				.hasNext();)
			addArticle(iter.next());
	}

	//
	// public void addPublisher(Publisher newPublisher) {
	// if (newPublisher == null)
	// return;
	// if (this.publisher == null)
	// this.publisher = new java.util.HashSet<Publisher>();
	// if (!newPublisher.belongs(this.publisher)) {
	// this.publisher.add(newPublisher);
	// newPublisher.setUser(this);
	// }
	// }
	//
	// public java.util.Iterator<Publisher> getIteratorPublisher() {
	// if (publisher == null)
	// publisher = new java.util.HashSet<Publisher>();
	// return publisher.iterator();
	// }
	//
	public java.util.Collection<Publisher> getPublisher() {
		if (publisher == null)
			publisher = new java.util.HashSet<Publisher>();
		return publisher;
	}

	//
	// public void removeAllPublisher() {
	// if (publisher != null) {
	// Publisher oldPublisher;
	// for (java.util.Iterator<Publisher> iter = getIteratorPublisher(); iter
	// .hasNext();) {
	// oldPublisher = iter.next();
	// iter.remove();
	// oldPublisher.setUser(null);
	// }
	// }
	// }
	//
	// public void removePublisher(Publisher oldPublisher) {
	// if (oldPublisher == null)
	// return;
	// if (this.publisher != null)
	// if (oldPublisher.belongs(this.publisher)) {
	// oldPublisher.quit(this.publisher);
	// oldPublisher.setUser(this.pojoNull());
	// }
	// }
	//
	public void setPublisher(java.util.Collection<Publisher> newPublisher) {
		this.publisher = newPublisher;
	}

	//
	// public void loadPublisher(SqlSession session, Map<String, Object> map) {
	// }

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@PersistentFlagAnnotation
	private String _persistent;
}