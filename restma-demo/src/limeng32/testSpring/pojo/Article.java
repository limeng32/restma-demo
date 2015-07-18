package limeng32.testSpring.pojo;

import java.io.Serializable;
import java.util.Date;

import limeng32.mybatis.plugin.mapper.annotation.FieldMapperAnnotation;
import limeng32.mybatis.plugin.mapper.annotation.PersistentFlagAnnotation;
import limeng32.mybatis.plugin.mapper.annotation.TableMapperAnnotation;
import limeng32.testSpring.pojo.condition.ArticleCondition;

import org.apache.ibatis.type.JdbcType;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;

@TableMapperAnnotation(tableName = ArticleCondition.field_tableName)
public class Article extends PojoSupport<Article> implements Serializable {

	private static final long serialVersionUID = 1L;

	@FieldMapperAnnotation(dbFieldName = ArticleCondition.field_id, jdbcType = JdbcType.INTEGER, isUniqueKey = true)
	private Integer id;

	@FieldMapperAnnotation(dbFieldName = ArticleCondition.field_userid, jdbcType = JdbcType.INTEGER, dbAssociationUniqueKey = "id")
	private User user;

	@FieldMapperAnnotation(dbFieldName = ArticleCondition.field_title, jdbcType = JdbcType.VARCHAR)
	private String title;

	@FieldMapperAnnotation(dbFieldName = ArticleCondition.field_content, jdbcType = JdbcType.LONGVARCHAR)
	private String content;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(serialize = false)
	private Date updateTime = new Date();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
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

	public void setUser(User newUser) {
		if (this.user == null || !this.user.equals(newUser)) {
			if (this.user != null) {
				User oldUser = this.user;
				this.user = null;
				oldUser.removeArticle(this);
			}
			if (newUser != null) {
				this.user = newUser;
				this.user.addArticle(this);
			}
		}
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

	@PersistentFlagAnnotation
	private String _persistent;
}
