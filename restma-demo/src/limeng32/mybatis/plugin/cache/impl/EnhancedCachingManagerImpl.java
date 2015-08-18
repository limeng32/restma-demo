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

import org.apache.ibatis.cache.Cache;

public class EnhancedCachingManagerImpl implements EnhancedCachingManager {

	// 每一个statementId 更新依赖的statementId集合
	private static Map<String, Set<String>> observers = new ConcurrentHashMap<>();
	private Map<Class<?>, Set<Method>> triggerMethods = new ConcurrentHashMap<>();
	private Map<Class<?>, Set<Method>> observerMethods = new ConcurrentHashMap<>();

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
						switch (cacheAnnotation.role()) {
						case Observer:
							for (Class<?> clazz1 : cacheAnnotation
									.MappedClass()) {
								if (!observerMethods.containsKey(clazz1)) {
									observerMethods.put(clazz1,
											new HashSet<Method>());
								}
								observerMethods.get(clazz1).add(method);
							}
							break;

						case Trigger:
							for (Class<?> clazz1 : cacheAnnotation
									.MappedClass()) {
								if (!triggerMethods.containsKey(clazz1)) {
									triggerMethods.put(clazz1,
											new HashSet<Method>());
								}
								triggerMethods.get(clazz1).add(method);
							}
							break;
						}
					}
				}
			}
			buildObservers(triggerMethods, observerMethods);
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
	}

	public static void buildObservers(
			Map<Class<?>, Set<Method>> triggerMethodMap,
			Map<Class<?>, Set<Method>> observerMethodMap) {
		for (Class<?> clazz : triggerMethodMap.keySet()) {
			Set<Method> observerMethods = observerMethodMap.get(clazz);
			for (Method triggerMethod : triggerMethodMap.get(clazz)) {
				String triggerFullName = triggerMethod.getDeclaringClass()
						.getName() + "." + triggerMethod.getName();
				if (!observers.containsKey(triggerFullName)) {
					observers.put(triggerFullName, new HashSet<String>());
				}
				for (Method observerMethod : observerMethods) {
					String observerFullName = observerMethod
							.getDeclaringClass().getName()
							+ "."
							+ observerMethod.getName();
					observers.get(triggerFullName).add(observerFullName);
				}
			}
		}
	}
}
