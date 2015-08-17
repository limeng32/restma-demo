package limeng32.mybatis.plugin.cache.impl;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import limeng32.mybatis.plugin.ReflectHelper;
import limeng32.mybatis.plugin.cache.CacheKeysPool;
import limeng32.mybatis.plugin.cache.EnhancedCachingManager;
import limeng32.mybatis.plugin.cache.annotation.CacheAnnotation;
import limeng32.mybatis.plugin.cache.annotation.CacheRoleType;
import limeng32.testSpring.mapper.WriterMapper;
import limeng32.testSpring.service.WriterService;

import org.apache.ibatis.cache.Cache;

public class EnhancedCachingManagerImpl implements EnhancedCachingManager {

	// 每一个statementId 更新依赖的statementId集合
	private static Map<String, Set<String>> observers = new ConcurrentHashMap<String, Set<String>>();

	// 全局性的 statemntId与CacheKey集合
	private CacheKeysPool sharedCacheKeysPool = new CacheKeysPool();
	// 记录每一个statementId 对应的Cache 对象
	private Map<String, Cache> holds = new ConcurrentHashMap<String, Cache>();
	private boolean initialized = false;
	private boolean cacheEnabled = false;

	private static EnhancedCachingManagerImpl enhancedCacheManager;

	private EnhancedCachingManagerImpl() {
	}

	public static EnhancedCachingManagerImpl getInstance() {
		return enhancedCacheManager == null ? (enhancedCacheManager = new EnhancedCachingManagerImpl())
				: enhancedCacheManager;
	}

	public void refreshCacheKey(CacheKeysPool keysPool) {
		sharedCacheKeysPool.putAll(keysPool);
		// sharedCacheKeysPool.display();
	}

	public void clearRelatedCaches(final Set<String> set) {
		// sharedCacheKeysPool.display();
		for (String observable : set) {
			Set<String> relatedStatements = observers.get(observable);
			if (relatedStatements != null) {
				for (String statementId : relatedStatements) {
					Cache cache = holds.get(statementId);
					Set<Object> cacheKeys = sharedCacheKeysPool
							.get(statementId);
					for (Object cacheKey : cacheKeys) {
						cache.removeObject(cacheKey);
					}
				}
			}
			// clear shared cacheKey Pool width specific key
			sharedCacheKeysPool.remove(observable);
		}
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void initialize(Properties properties) {
		// String dependency = properties.getProperty("dependency");
		// if (!("".equals(dependency) || dependency == null)) {
		// InputStream inputStream;
		// try {
		// inputStream = Resources.getResourceAsStream(dependency);
		// XPathParser parser = new XPathParser(inputStream);
		// List<XNode> statements = parser
		// .evalNodes("/dependencies/statements/statement");
		// for (XNode node : statements) {
		// Set<String> temp = new HashSet<String>();
		// List<XNode> obs = node.evalNodes("observer");
		// for (XNode observer : obs) {
		// temp.add(observer.getStringAttribute("id"));
		// }
		// observers.put(node.getStringAttribute("id"), temp);
		// }
		// initialized = true;
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
		initialized = true;
		// cacheEnabled
		String cacheEnabled = properties.getProperty("cacheEnabled", "true");
		if ("true".equals(cacheEnabled)) {
			this.cacheEnabled = true;
		}

		String annotationPackageName = properties
				.getProperty("annotationPackage");
		Package annotationPackage = Package.getPackage(annotationPackageName);
		if (annotationPackage != null) {
			Set<Class<?>> classes = ReflectHelper
					.getClasses(annotationPackageName);
			for (Class<?> clazz : classes) {
				for (Method method : clazz.getDeclaredMethods()) {
					CacheAnnotation cacheAnnotation = method
							.getAnnotation(CacheAnnotation.class);
					if (cacheAnnotation != null) {
						System.out.println("---------------" + cacheAnnotation);
					}
				}
			}

			System.out.println("---------------" + classes);
		}
	}

	public void appendStatementCacheMap(String statementId, Cache cache) {
		if (holds.containsKey(statementId) && holds.get(statementId) != null) {
			return;
		}
		holds.put(statementId, cache);
	}

	public boolean isCacheEnabled() {
		return cacheEnabled;
	}

	public static void buildObservers(String id) {
		Set<String> temp = new HashSet<String>();
		/* 在这里使用注解的方式获取对应的方法 */
		// temp.add("limeng32.testSpring.mapper.WriterMapper.select");
		// temp.add("limeng32.testSpring.mapper.WriterMapper.selectAll");
		for (Method m : WriterService.class.getDeclaredMethods()) {
			CacheAnnotation an = m.getAnnotation(CacheAnnotation.class);
			if (an != null && CacheRoleType.Observer.equals(an.role())) {
				temp.add(WriterMapper.class.getName() + "." + m.getName());
			}
		}
		observers.put(id, temp);
	}
}
