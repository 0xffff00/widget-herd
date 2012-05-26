package hzk.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * 
 * JavaEye上某人封装的�?��编码转换工具�?基于流的编码转换 略有改动
 * 
 * @see http://www.javaeye.com/topic/377940
 * @author tuoxie007
 * @author HZK
 */
public class EncodingConverter {

	public static final String UTF8 = "UTF-8";
	public static final String GBK = "GBK";

	public static InputStream convertToInputStream(InputStream is, String srcEnc,
			String tarEnc) throws IOException {

		// builder a reader using source encoding
		Reader reader = new InputStreamReader(is, srcEnc);
		StringBuilder sb = new StringBuilder();

		// read content
		int c;
		while ((c = reader.read()) != -1) {
			sb.append((char) c);
		}
		reader.close();

		// get byte array
		InputStream tarIs = new ByteArrayInputStream(sb.toString().getBytes(
				tarEnc));
		return tarIs;
	}

	public static InputStream convertToInputStream(byte[] bytes, String srcEnc, String tarEnc)
			throws IOException {
	
		// builder a reader using source encoding
		InputStream is = new ByteArrayInputStream(bytes);
		Reader reader = new InputStreamReader(is, srcEnc);
		StringBuilder sb = new StringBuilder();
	
		// read content
		int c;
		while ((c = reader.read()) != -1) {
			sb.append((char) c);
		}
		reader.close();
	
		// get byte array
		InputStream tarIs = new ByteArrayInputStream(sb.toString().getBytes(
				tarEnc));
		return tarIs;
	}

	public static String convertToString(InputStream is, String srcEnc)
			throws IOException {

		// builder a reader using source encoding
		Reader reader = new InputStreamReader(is, srcEnc);
		StringBuilder sb = new StringBuilder();

		// read content
		int c;
		while ((c = reader.read()) != -1) {
			sb.append((char) c);
		}
		reader.close();

		return sb.toString();
	}

	public static String convertToString(InputStream is) throws IOException {

		Reader reader = new InputStreamReader(is);
		StringBuffer buffer = new StringBuffer();
		int c;
		while ((c = reader.read()) != -1) {
			buffer.append((char) c);
		}
		return buffer.toString();
	}

	public static String convertToString(byte[] bytes, String srcEnc)
			throws IOException {
	
		// builder a reader using source encoding
		InputStream is = new ByteArrayInputStream(bytes);
		Reader reader = new InputStreamReader(is, srcEnc);
		StringBuilder sb = new StringBuilder();
	
		// read content
		int c;
		while ((c = reader.read()) != -1) {
			sb.append((char) c);
		}
		reader.close();
	
		return sb.toString();
	}

	public static byte[] convertToByteArray(InputStream is, String srcEnc,
			String tarEnc) throws IOException {
	
		// builder a reader using source encoding
		Reader reader = new InputStreamReader(is, srcEnc);
		StringBuilder sb = new StringBuilder();
	
		// read content
		int c;
		while ((c = reader.read()) != -1) {
			sb.append((char) c);
		}
		reader.close();
	
		// get byte array
		return sb.toString().getBytes(tarEnc);
	}

	public static byte[] convertToByteArray(byte[] bytes, String srcEnc,
			String tarEnc) throws IOException {

		// builder a reader using source encoding
		InputStream is = new ByteArrayInputStream(bytes);
		Reader reader = new InputStreamReader(is, srcEnc);
		StringBuilder sb = new StringBuilder();

		// read content
		int c;
		while ((c = reader.read()) != -1) {
			sb.append((char) c);
		}
		reader.close();

		// get byte array
		return sb.toString().getBytes(tarEnc);
	}

	/**
	 * 以下是派生的方法
	 */
	public static InputStream convertToUTF8InputStream(InputStream is, String srcEnc)
			throws IOException {
		return convertToInputStream(is, srcEnc, UTF8);
	}

	public static InputStream convertToUTF8InputStream(byte[] bytes, String srcEnc)
			throws IOException {
		return convertToInputStream(bytes,srcEnc,UTF8);
	}

	public static byte[] convertToUTF8ByteArray(InputStream is, String srcEnc)
			throws IOException {
		return convertToByteArray(is, srcEnc, UTF8);
	}

	public static byte[] convertToUTF8ByteArray(byte[] bytes, String srcEnc)
			throws IOException {
			return convertToByteArray(bytes, srcEnc,UTF8);
	}

}
