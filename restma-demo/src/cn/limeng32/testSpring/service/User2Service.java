package cn.limeng32.testSpring.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;

import cn.limeng32.testSpring.mapper.UserMapper;
import cn.limeng32.testSpring.pojo.User;

public class User2Service extends SqlSessionDaoSupport {

	public User select(int id) {
		return getSqlSession().selectOne(
				"cn.limeng32.testSpring.mapper.UserMapper.select", id);
	}

	public void insert(User u) {
		User u1 = getSqlSession().selectOne(
				"cn.limeng32.testSpring.mapper.UserMapper.select", 1);
		User u2 = getSqlSession().selectOne(
				"cn.limeng32.testSpring.mapper.UserMapper.select", 1);
	}

}