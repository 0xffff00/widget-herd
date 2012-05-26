package hzk.util.hash;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JEncryptor implements SHA1Encryptor{
	private Log log=LogFactory.getLog(this.getClass());
	public static final String ALGORITHM="SHA-1";
	public static final int BUFFER_SIZE_OF_BYTE=65536;
	
	@Override
	public byte[] SHA1(byte[] data) {
		try {
			MessageDigest md = MessageDigest.getInstance(ALGORITHM);
			md.update(data);
			return md.digest();
		} catch (NoSuchAlgorithmException e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public String SHA1(String data) {
		return toHexString(SHA1(data.getBytes()));
	}

	@Override
	public String SHA1(String data, String charset) {
		try {
			return toHexString(SHA1(data.getBytes(charset)));
		} catch (UnsupportedEncodingException e) {
			log.warn(e);
			return toHexString(SHA1(data.getBytes()));
		}
	
	}

	/**
	 * 此方法技术一个文件的SHA1值。
	 * 在分析完整个文件之前，此方法一直阻塞当前线程
	 */
	public String SHA1ofFile(String path) {		
		RandomAccessFile f;
		MessageDigest md;
		byte[] buffer=new byte[BUFFER_SIZE_OF_BYTE];			
		try {
			f = new RandomAccessFile(path, "r");	
			md = MessageDigest.getInstance(ALGORITHM);
			int nread=0;
			while ((nread = f.read(buffer)) != -1){				
				md.update(buffer,0,nread);
			}
			return toHexString(md.digest());
		} catch (NoSuchAlgorithmException | IOException e) {
			log.error(e);
			return null;			
	
		}
	}
	
		
	/**
	 * 转成16进制字符串(小写字母形式)
	 * 
	 * @param b  字节数组
	 * @return 16进制字符串
	 */
	protected static String toHexString(byte[] b) {
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
