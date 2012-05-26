package hzk.util;

import java.io.IOException;
import java.io.InputStream;


public class CSHA1Executor {
	protected static String cmd="D:\\var\\eclipse\\hzk\\SWTDemo\\src\\main\\resouces\\sha1.exe";
	
	public static String calculate(String fileName){
		InputStream in;
		String str = null;
		try {
			in = ExteriorInvoker.invoke(cmd,fileName);
			str = EncodingConverter.convertToString(in);
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}
		
		if (str==null ||str.length()<40) return null;
		return str.substring(0, 40);
	}
}
