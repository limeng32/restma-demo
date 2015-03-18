package cn.limeng32.testSpring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.limeng32.testSpring.mapper.ArticleMapper;
import cn.limeng32.testSpring.pojo.Article;

@Service
public class TxService2 {

	@Autowired
	private ArticleMapper articleMapper;

	@Transactional(noRollbackFor = { RuntimeException.class }, readOnly = true, propagation = Propagation.REQUIRED)
	public void insert() {
		Article a1 = new Article();
		a1.setTitle("c1");
		articleMapper.insert(a1);
		if (true) {
			throw new RuntimeException("qwe");
		}
	}
}
