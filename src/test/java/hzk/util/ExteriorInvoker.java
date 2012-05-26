package hzk.util;


import java.io.IOException;
import java.io.InputStream;

/**
 * �ⲿ���������
 * 
 * @author HZK
 * 
 */
public class ExteriorInvoker {

	private static InputStream execute(String command) throws IOException {
		Process ps = Runtime.getRuntime().exec(command);
		InputStream in = ps.getInputStream();
		return in;

	}

	/**
	 * ���ó��� ���������ɽӲ���
	 * 
	 * @param command
	 *            ������������
	 * @param args
	 *            �����ѡ��
	 * @return ������
	 * @throws IOException 
	 */
	public static InputStream invoke(String command, String... args) throws IOException {
		StringBuffer cmd = new StringBuffer(command);
		for (String arg : args)
			cmd.append(" ").append(arg);

		return execute(cmd.toString());

	}

}
