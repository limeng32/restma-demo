package limeng32.testSpring.service;

import limeng32.testSpring.mapper.ArticleMapper;
import limeng32.testSpring.pojo.Article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TxService {

	@Autowired
	private ArticleMapper articleMapper;

	@Autowired
	private TxService2 txService2;

	// @Autowired
	// private JdbcTemplate jdbcTemplate;
	//
	// public void getConn() {
	// try {
	// Connection conn = DataSourceUtils.getConnection(jdbcTemplate
	// .getDataSource());
	// // conn = jdbcTemplate.getNativeJdbcExtractor().getNativeConnection(
	// // conn);
	// // DatabaseMetaData md = conn.getMetaData();
	// // System.out.println("-1-" + md.supportsSavepoints());
	// // System.out.println("-" + md.supportsTransactions());
	// // System.out.println("--" + md.getDefaultTransactionIsolation());
	// // System.out
	// // .println("--1"
	// // + md
	// // .supportsTransactionIsolationLevel(Isolation.READ_UNCOMMITTED
	// // .value()));
	// // System.out
	// // .println("--2"
	// // + md
	// // .supportsTransactionIsolationLevel(Isolation.READ_COMMITTED
	// // .value()));
	// // System.out
	// // .println("--3"
	// // + md
	// // .supportsTransactionIsolationLevel(Isolation.REPEATABLE_READ
	// // .value()));
	// // System.out
	// // .println("--4"
	// // + md
	// // .supportsTransactionIsolationLevel(Isolation.SERIALIZABLE
	// // .value()));
	// conn.setAutoCommit(false);
	// Statement stmt = conn.createStatement();
	// stmt.executeUpdate("INSERT INTO publisher (name) VALUES('a')");
	// Savepoint svpt = conn.setSavepoint("save1");
	// stmt.executeUpdate("INSERT INTO publisher (name) VALUES('b')");
	// conn.rollback(svpt);
	// conn.commit();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	@Transactional(noRollbackFor = { RuntimeException.class }, readOnly = false)
	public void insert() {
		// Article a1 = new Article();
		// a1.setTitle("a1");
		// articleMapper.insert(a1);

		txService2.insert();
		// insert2();
		// if (true) {
		// throw new RuntimeException("asd");
		// }
		// a1.setTitle("b1");
		// articleMapper.insert(a1);
	}

	@Transactional(rollbackFor = { RuntimeException.class }, readOnly = true, propagation = Propagation.NEVER)
	private void insert2() {
		Article a1 = new Article();
		a1.setTitle("d1");
		articleMapper.insert(a1);
		if (true) {
			throw new RuntimeException("qwe");
		}
	}
}
