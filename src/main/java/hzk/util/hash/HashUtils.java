package hzk.util.hash;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HashUtils {	
	private static Log log=LogFactory.getLog(HashUtils.class);
	private static final String ALGORITHM_SHA1="SHA-1";
	
	public static byte[] SHA1(byte[] data) {
		try {
			MessageDigest md = MessageDigest.getInstance(ALGORITHM_SHA1);
			md.update(data);
			return md.digest();
		} catch (NoSuchAlgorithmException e) {
			log.error(e);
			return null;
		}
	}

	
	public static String SHA1(String data) {
		return toHexString(SHA1(data.getBytes()));
	}

	
	public static String SHA1(String data, String charset) {
		try {
			return toHexString(SHA1(data.getBytes(charset)));
		} catch (UnsupportedEncodingException e) {
			log.warn(e);
			return toHexString(SHA1(data.getBytes()));
		}
	
	}
	/**
	 * 转成16进制字符串(小写字母形式)
	 * 
	 * @param b
	 *            字节数组
	 * @return 16进制字符串
	 */
	public static String toHexString(byte[] b) {
		if (b == null) {
			return null;
		}
		StringBuilder hex = new StringBuilder();
		int x, y, i;
		char p, q;
		for (i = 0; i < b.length; i++) {
			x = b[i] >> 4 & 15;
			y = b[i] & 15;
			if (x > 9)
				p = (char) (x + 87);
			else
				p = (char) (x + 48);
			if (y > 9)
				q = (char) (y + 87);
			else
				q = (char) (y + 48);
			hex.append(p).append(q);
		}

		return hex.toString();

	}
}
