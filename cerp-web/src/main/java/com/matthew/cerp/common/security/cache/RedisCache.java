package com.matthew.cerp.common.security.cache;

import com.matthew.cerp.common.util.SerializeUtil;
import com.yaoyaohao.framework.redis.ShardedJedisClient;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 自定义shiro-redis缓存实现，通过redis客户端来实现缓存的存取
 * 
 * @author liujianzhu
 * @date 2016年3月17日 下午2:06:49
 *
 * @param <K>
 * @param <V>
 */
@SuppressWarnings("unchecked")
public class RedisCache<K, V> implements Cache<K, V> {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private ShardedJedisClient jedisClient;

	private String keyPrefix = "Shiro_Session:";

	public RedisCache(ShardedJedisClient jedisClient) {
		if (jedisClient == null) {
			throw new IllegalArgumentException("Cache argument cannot be null.");
		}
		this.jedisClient = jedisClient;
	}

	public RedisCache(ShardedJedisClient jedisClient, String prefix) {
		this(jedisClient);
		// set the prefix
		this.keyPrefix = prefix;
	}

	/**
	 * 得到转化为byte[]类型的key
	 * 
	 * @param key
	 * @return
	 */
	private byte[] getByteKey(K key) {
//		if (key instanceof String) {
//			String preKey = this.keyPrefix + key;
//			return preKey.getBytes();
//		} else {
//			return SerializeUtil.serialize(key);
//		}
		String preKey = this.keyPrefix + key;
		return preKey.getBytes();
	}

	public V get(K key) throws CacheException {
		logger.debug("根据key从Redis中获取对象 key [" + key + "]");
		if (key == null)
			return null;
		try {
			return (V) jedisClient.get(getByteKey(key));
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	public V put(K key, V value) throws CacheException {
		logger.debug("根据key从存储 key [" + key + "]");
		try {
			jedisClient.set(getByteKey(key), value);
			return value;
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	public V remove(K key) throws CacheException {
		logger.debug("从redis中删除 key [" + key + "]");
		try {
			V previous = get(key);
			jedisClient.del(getByteKey(key));
			return previous;
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	public void clear() throws CacheException {
		logger.debug("从redis中删除所有元素");
		try {
			Set<byte[]> keys = jedisClient.bKeys(this.keyPrefix + "*");
			if (!CollectionUtils.isEmpty(keys)) {
				for (byte[] key : keys) {
					jedisClient.del(key);
				}
			}
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	public int size() {
		return keys().size();
	}

	public Set<K> keys() {
		try {
			Set<byte[]> keys = jedisClient.bKeys(this.keyPrefix + "*");
			if (CollectionUtils.isEmpty(keys)) {
				return Collections.emptySet();
			} else {
				Set<K> newKeys = new HashSet<K>();
				for (byte[] key : keys) {
					Object obj = SerializeUtil.deserialize(key);
					newKeys.add((K) obj);
				}
				return newKeys;
			}
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	public Collection<V> values() {
		try {
			Set<byte[]> keys = jedisClient.bKeys(this.keyPrefix + "*");
			if (!CollectionUtils.isEmpty(keys)) {
				List<V> values = new ArrayList<V>(keys.size());
				for (byte[] key : keys) {
					V value = (V)jedisClient.get(key);
					if (value != null) {
						values.add(value);
					}
				}
				return Collections.unmodifiableList(values);
			} else {
				return Collections.emptyList();
			}
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	public String getKeyPrefix() {
		return keyPrefix;
	}

	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}
}
