package hzk.util.hash;

public interface SHA1Encryptor {
	
	byte[] SHA1(byte[] data);
	String SHA1(String data);
	String SHA1(String data,String charset);
	String SHA1ofFile(String path);

}
