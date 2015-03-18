package cn.limeng32.testSpring.test;

import java.sql.*;
import java.util.*;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.mybatis.spring.*;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.context.*;
import org.springframework.context.support.*;
import org.springframework.dao.*;
import org.springframework.jdbc.core.*;

import cn.limeng32.testSpring.mapper.*;
import cn.limeng32.testSpring.pojo.User;

public class TestMybatis {

	public User select123() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"spring-dispather.xml");

		DefaultSqlSessionFactory sfb = (DefaultSqlSessionFactory) context
				.getBean("sqlSessionFactory");
		// SqlSessionFactory sf = null;
		// try {
		// sf = sfb.getObject();
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		SqlSession session = sfb.openSession();
		UserMapper mapper = session.getMapper(UserMapper.class);
		User u = mapper.select(1);
		// User u = new User();
		return u;
	}

}
