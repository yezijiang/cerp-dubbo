package com.matthew.cerp.common.security.cache;

import com.yaoyaohao.framework.redis.ShardedJedisClient;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * shiro与redis结合的缓存管理类
 * 
 * @author liujianzhu
 * @date 2016年3月17日 下午3:33:03
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class RedisCacheManager implements CacheManager {
	private static final Logger logger = LoggerFactory.getLogger(RedisCacheManager.class);
	
	//为了快速查找
	private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();
	
	private ShardedJedisClient jedisClient;
	
	private String keyPrefix = "Shiro_Cache:";

	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		logger.debug("获取名称为: " + name + " 的RedisCache实例");
		Cache c = caches.get(name);
		if(c == null) {
			c = new RedisCache<K, V>(jedisClient, keyPrefix);
			//
			caches.put(name, c);
		}
		return c;
	}

	public ShardedJedisClient getJedisClient() {
		return jedisClient;
	}

	@Autowired
	public void setJedisClient(ShardedJedisClient jedisClient) {
		this.jedisClient = jedisClient;
	}

	public String getKeyPrefix() {
		return keyPrefix;
	}

	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}
}
