package hzk.util.http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Utils {
	public void copy(InputStream src, OutputStream dest) throws IOException {
		InputStream ins = new BufferedInputStream(src);
		OutputStream outs = new BufferedOutputStream(dest);
		int bit;
		while ((bit = ins.read()) != -1) {
			outs.write(bit);

		}

	}
}
