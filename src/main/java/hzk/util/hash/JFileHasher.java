package hzk.util.hash;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JFileHasher {
	private static Log log = LogFactory.getLog(JFileHasher.class);
	private static final int BUFFER_SIZE_OF_BYTE = 65536;
	public static final String ALGORITHM_SHA = "SHA-1";
	public static final String ALGORITHM_MD5 = "MD5";

	public static JFileHashTask createTask(String path, String algorithm) {
		JFileHashTask task = new JFileHashTask(path, algorithm);
		log.debug("createTask:("+algorithm+")" + path);
		return task;
	}

	public static JFileHashTask createTask(String path) {
		return createTask(path, ALGORITHM_SHA);
	}

	public static String simpleSHA1(String path) {
		byte[] buffer = new byte[BUFFER_SIZE_OF_BYTE];
		try {
			InputStream ins = new FileInputStream(path);
			MessageDigest md = MessageDigest
					.getInstance(JFileHasher.ALGORITHM_SHA);
			int nread = 0;
			while ((nread = ins.read(buffer)) != -1) {
				md.update(buffer, 0, nread);
			}
			return HashUtils.toHexString(md.digest());
		} catch (NoSuchAlgorithmException | IOException e) {
			log.error(null, e);
			return null;
		}

	}

}
