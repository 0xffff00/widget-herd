package hzk.util.hash;

public class HashUtil {
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
