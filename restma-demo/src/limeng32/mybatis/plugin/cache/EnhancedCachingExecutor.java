package limeng32.mybatis.plugin.cache;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import limeng32.mybatis.plugin.cache.impl.EnhancedCachingManagerImpl;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

@Intercepts(value = {
		@Signature(args = { MappedStatement.class, Object.class,
				RowBounds.class, ResultHandler.class }, method = "query", type = Executor.class),
		@Signature(args = { MappedStatement.class, Object.class }, method = "update", type = Executor.class),
		@Signature(args = { boolean.class }, method = "commit", type = Executor.class),
		@Signature(args = { boolean.class }, method = "rollback", type = Executor.class),
		@Signature(args = { boolean.class }, method = "close", type = Executor.class) })
public class EnhancedCachingExecutor implements Interceptor {
	private static CacheKeysPool queryCacheOnCommit = new CacheKeysPool();
	private static Set<String> updateStatementOnCommit = new HashSet<String>();
	EnhancedCachingManager cachingManager = EnhancedCachingManagerImpl
			.getInstance();

	// private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new
	// DefaultObjectFactory();
	// private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY
	// = new DefaultObjectWrapperFactory();

	public Object intercept(Invocation invocation) throws Throwable {
		// Executor executorProxy = (Executor) invocation.getTarget();
		// MetaObject metaExecutor = MetaObject.forObject(executorProxy,
		// DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
		// // 分离代理对象链
		// while (metaExecutor.hasGetter("h")) {
		// Object object = metaExecutor.getValue("h");
		// metaExecutor = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY,
		// DEFAULT_OBJECT_WRAPPER_FACTORY);
		// }
		// // 分离最后一个代理对象的目标类
		// while (metaExecutor.hasGetter("target")) {
		// Object object = metaExecutor.getValue("target");
		// metaExecutor = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY,
		// DEFAULT_OBJECT_WRAPPER_FACTORY);
		// }
		String name = invocation.getMethod().getName();
		Object result = null;
		switch (name) {
		case "commit":
			result = this.processCommit(invocation);
			break;
		case "close":
			result = this.processClose(invocation);
			break;
		case "query":
			result = this.processQuery(invocation);
			break;
		case "rollback":
			result = this.processRollback(invocation);
			break;
		case "update":

			result = this.processUpdate(invocation);
			break;
		default:
			break;
		}
		return result;
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	/**
	 * when executing a query operation 1. record this statement's id and it's
	 * corresponding Cache Object into Global Caching Manager; 2. record this
	 * statement's id and
	 * 
	 * @param invocation
	 * @return
	 * @throws Throwable
	 */
	protected Object processQuery(Invocation invocation) throws Throwable {
		Object result = invocation.proceed();
		if (cachingManager.isCacheEnabled()) {
			Object[] args = invocation.getArgs();
			MappedStatement mappedStatement = (MappedStatement) args[0];

			// 如果本条statementId表示的查询语句配置了 flushCache=true，则清空querCacheOnCommit缓存
			if (mappedStatement.isFlushCacheRequired()) {
				queryCacheOnCommit.clear();
			}
			// 如果本条statementId表示的查询语句配置了使用缓存，并且二级缓存不为空，则将StatementId
			// 和对应的二级缓存对象映射关系添加到全局缓存映射管理器中
			if (mappedStatement.isUseCache()
					&& mappedStatement.getCache() != null) {
				cachingManager.appendStatementCacheMap(mappedStatement.getId(),
						mappedStatement.getCache());
			}

			Object parameter = args[1];
			RowBounds rowBounds = (RowBounds) args[2];
			Executor executor = (Executor) invocation.getTarget();
			BoundSql boundSql = mappedStatement.getBoundSql(parameter);

			// 记录本次查询所产生的CacheKey
			CacheKey cacheKey = executor.createCacheKey(mappedStatement,
					parameter, rowBounds, boundSql);
			queryCacheOnCommit.putElement(mappedStatement.getId(), cacheKey);
		}

		return result;
	}

	protected Object processUpdate(Invocation invocation) throws Throwable {

		Object result = invocation.proceed();
		MappedStatement mappedStatement = (MappedStatement) invocation
				.getArgs()[0];
		// 在这里将没加入到observers的mappedStatement加入到observers中
		EnhancedCachingManagerImpl.buildObservers(mappedStatement.getId());
		updateStatementOnCommit.add(mappedStatement.getId());
		return result;
	}

	protected Object processCommit(Invocation invocation) throws Throwable {
		Object result = invocation.proceed();
		refreshCache();
		return result;
	}

	protected Object processRollback(Invocation invocation) throws Throwable {
		Object result = invocation.proceed();
		clearSessionData();
		return result;
	}

	protected Object processClose(Invocation invocation) throws Throwable {
		Object result = invocation.proceed();
		boolean forceRollback = (Boolean) invocation.getArgs()[0];
		if (forceRollback) {
			clearSessionData();
		} else {
			refreshCache();
		}
		return result;
	}

	/**
	 * when the sqlSession has been committed,rollbacked,or closed, session
	 * buffer query CacheKeys and update Statement collections should be
	 * cleared.
	 * 
	 * 当SqlSession 执行了commit()、rollback()、close()方法，
	 * Session级别的查询语句产生的CacheKey集合以及 执行的更新语句集合应该被清空
	 */
	private synchronized void clearSessionData() {
		queryCacheOnCommit.clear();
		updateStatementOnCommit.clear();
	}

	/**
	 * refresh the session cache,there are two things have to do: 1. add this
	 * session scope query logs to global cache Manager 2. clear the related
	 * caches according to the update statements as configured in "dependency"
	 * file 3. clear the session data
	 */
	private synchronized void refreshCache() {
		cachingManager.refreshCacheKey(queryCacheOnCommit);
		// clear the related caches
		cachingManager.clearRelatedCaches(updateStatementOnCommit);
		clearSessionData();
	}

	/**
	 * 
	 * 
	 * Executor插件配置信息加载点 properties中有 "dependency" 属性来指示
	 * 配置的缓存依赖配置信息，读取文件，初始化EnhancedCacheManager
	 */
	public void setProperties(Properties properties) {

		if (!cachingManager.isInitialized()) {
			cachingManager.initialize(properties);
		}
	}
}
