package com.matthew.cerp.common.security;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.util.ByteSource;

import java.io.*;
import java.util.Arrays;

/**
 * 在ShiroDbReaml的doGetAuthenticationInfo方法返回的登录信息需要缓存时，
 * 原SimpleByteSource实现并没有继承Serializable接口，不能序列化。故自定义实现个可序列化ByteSource
 * 
 * @author liujianzhu
 * @date 2016年3月18日 上午11:21:02
 *
 */
public class SerializableByteSource implements ByteSource,Externalizable{
	private byte[] bytes;
    private transient String cachedHex;
    private transient String cachedBase64;
    
    public SerializableByteSource(){
    }

    public SerializableByteSource(byte[] bytes) {
        this.bytes = bytes;
    }

    public SerializableByteSource(char[] chars) {
        this.bytes = CodecSupport.toBytes(chars);
    }

    public SerializableByteSource(String string) {
        this.bytes = CodecSupport.toBytes(string);
    }

    public SerializableByteSource(ByteSource source) {
        this.bytes = source.getBytes();
    }

    public SerializableByteSource(File file) {
        this.bytes = new BytesHelper().getBytes(file);
    }

    public SerializableByteSource(InputStream stream) {
        this.bytes = new BytesHelper().getBytes(stream);
    }

    public static boolean isCompatible(Object o) {
        return o instanceof byte[] || o instanceof char[] || o instanceof String ||
                o instanceof ByteSource || o instanceof File || o instanceof InputStream;
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public boolean isEmpty() {
        return this.bytes == null || this.bytes.length == 0;
    }

    public String toHex() {
        if ( this.cachedHex == null ) {
            this.cachedHex = Hex.encodeToString(getBytes());
        }
        return this.cachedHex;
    }

    public String toBase64() {
        if ( this.cachedBase64 == null ) {
            this.cachedBase64 = Base64.encodeToString(getBytes());
        }
        return this.cachedBase64;
    }

    public String toString() {
        return toBase64();
    }

    public int hashCode() {
        if (this.bytes == null || this.bytes.length == 0) {
            return 0;
        }
        return Arrays.hashCode(this.bytes);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof ByteSource) {
            ByteSource bs = (ByteSource) o;
            return Arrays.equals(getBytes(), bs.getBytes());
        }
        return false;
    }

    //will probably be removed in Shiro 2.0.  See SHIRO-203:
    //https://issues.apache.org/jira/browse/SHIRO-203
    private static final class BytesHelper extends CodecSupport {
        public byte[] getBytes(File file) {
            return toBytes(file);
        }

        public byte[] getBytes(InputStream stream) {
            return toBytes(stream);
        }
    }

    //序列化与反序列化
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(getBytes().length);
		out.write(getBytes());
	}

	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		int len = in.readInt();
		bytes = new byte[len];
		in.read(bytes);
	}
    
}
