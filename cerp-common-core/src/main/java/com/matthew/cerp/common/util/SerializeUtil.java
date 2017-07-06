package com.matthew.cerp.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 序列化工具类
 * 
 * @author liujianzhu
 * @date 2016年3月17日 下午1:37:09
 *
 */
public class SerializeUtil {
	private static Logger logger = LoggerFactory.getLogger(SerializeUtil.class);
	
	/**
	  * 序列化操作
	  * 
	  * @param object
	  * @return
	 */
	public static byte[] serialize(Object object) {
		byte[] result = null;
		if(object == null) 
			return new byte[0];
		try{
			if(!(object instanceof Serializable)) {
				throw new IllegalArgumentException("SerializeUtil.serialize(Object) requires a Serializable payload " +
						"but received an object of type [" + object.getClass().getName() + "]");
			}
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream(128);
			ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
			objectStream.writeObject(object);
			objectStream.flush();
			result = byteStream.toByteArray();
		}catch(Exception e) {
			logger.error("Failed to serialize", e);
		}
		return result;
	}
	
	/**
	  * 反序列化操作
	  * 
	  * @param bytes
	  * @return
	 */
	public static Object deserialize(byte[] bytes) {
		Object result = null;
		if(bytes == null || bytes.length == 0)
			return null;
		try{
			ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
			ObjectInputStream objectStream = new ObjectInputStream(byteStream);
			result = objectStream.readObject();
		}catch(Exception e) {
			logger.error("Failed to deserialize", e);
		}
		return result;
	}
}
