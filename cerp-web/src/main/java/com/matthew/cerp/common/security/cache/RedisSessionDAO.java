package com.matthew.cerp.common.security.cache;

import com.matthew.cerp.common.util.SerializeUtil;
import com.yaoyaohao.framework.redis.ShardedJedisClient;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 通过Redis来实现session会话的存取管理
 * 
 * @author liujianzhu
 * @date 2016年3月17日 下午3:50:44
 *
 */
public class RedisSessionDAO extends AbstractSessionDAO {
	
	private static Logger logger = LoggerFactory.getLogger(RedisSessionDAO.class);

	private ShardedJedisClient jedisClient;
	
	private String keyPrefix = "Shiro_Session:";
	
	private final static int DEFAULT_EXPIRE = 1800;
	
	//单位为秒，默认30分钟
	private int expire = DEFAULT_EXPIRE; 
	
	/**
	 * 获得byte[]型的key
	 * @param sessionId
	 * @return
	 */
	private byte[] getByteKey(Serializable sessionId){
		String preKey = this.keyPrefix + sessionId;
		return preKey.getBytes();
	}
	
	/**
	  * 保存session
	  * 
	  * @param session
	  * @throws UnknownSessionException
	 */
	private void storeSession(Session session) throws UnknownSessionException {
		if(session == null || session.getId() == null){
			logger.error("session or session id is null");
			return;
		}
		byte[] key = getByteKey(session.getId());
		byte[] value = SerializeUtil.serialize(session);
		session.setTimeout(expire * 1000);
		//
		jedisClient.set(key, value, expire);
	}

	public void update(Session session) throws UnknownSessionException {
		this.storeSession(session);
	}

	public void delete(Session session) {
		if(session == null || session.getId() == null){
			logger.error("session or session id is null");
			return;
		}
		jedisClient.del(getByteKey(session.getId()));
	}

	public Collection<Session> getActiveSessions() {
		Set<Session> sessions = new HashSet<Session>();
		Set<byte[]> keys = jedisClient.bKeys(this.keyPrefix + "*");
		if(keys != null && keys.size() > 0) {
			for(byte[] key : keys) {
				Session s = (Session)jedisClient.get(key);
				sessions.add(s);
			}
		}
		return sessions;
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = this.generateSessionId(session);
		this.assignSessionId(session, sessionId);
		this.storeSession(session);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		if(sessionId == null){
			logger.error("session id is null");
			return null;
		}
		Session s = (Session)jedisClient.get(getByteKey(sessionId));
		return s;
	}

	public ShardedJedisClient getJedisClient() {
		return jedisClient;
	}

	public void setJedisClient(ShardedJedisClient jedisClient) {
		this.jedisClient = jedisClient;
	}

	public String getKeyPrefix() {
		return keyPrefix;
	}

	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}

	public int getExpire() {
		return expire;
	}

	public void setExpire(int expire) {
		if(expire > 0)
			this.expire = expire;
	}
}
