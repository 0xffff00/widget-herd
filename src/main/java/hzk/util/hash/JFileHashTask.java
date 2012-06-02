package hzk.util.hash;

import hzk.util.ProgressEvent;
import hzk.util.ProgressTask;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JFileHashTask extends ProgressTask {
	private Log log = LogFactory.getLog(this.getClass());
	private static final int BUFFER_SIZE_OF_BYTE = 65536;
	private String filePath, algorithm, result;
	private int pgVal, pgMax;
	private long fpos, flen;
	private RandomAccessFile f;
	private MessageDigest md;

	protected JFileHashTask(String filepath, String algorithm) {
		this.filePath = filepath;
		this.algorithm = algorithm;
	}

	@Override
	public void run() {
		byte[] buffer = new byte[BUFFER_SIZE_OF_BYTE];
		try {
			f = new RandomAccessFile(filePath, "r");
			md = MessageDigest.getInstance(algorithm);
			pgVal = 0;
			fpos = 0;
			flen = f.length();
			pgMax = (int) ((flen - 1) / BUFFER_SIZE_OF_BYTE) + 1;
			int nread = 0;
			while ((nread = f.read(buffer)) != -1) {
				if (isCancelled()) {
					break;
				}
				if (isPaused()) {
					publish(PAUSE);
					letThreadWait();
				}
				md.update(buffer, 0, nread);
				pgVal++;
				fpos += nread;
				publish();
			}

			if (isCancelled()) {
				result = "Hash Canncelled";
				publish(STOP);
			} else {
				result = HashUtils.toHexString(md.digest());
				publish(COMPLETE);
			}

		} catch (NoSuchAlgorithmException | IOException e) {
			log.error(null, e);
			result = e.toString();
			publish(ERROR);
		}
	}

	@Override
	protected ProgressEvent createEvent() {
		ProgressEvent e = new ProgressEvent();
		e.setTaskRunMillisec(getRunMillisec());
		e.setNewValue(pgVal);
		e.setMaximum(pgMax);
		e.setResult(result);

		String stat = String.valueOf(fpos / 1024) + "KB/"
				+ String.valueOf(flen / 1024) + "KB";
		e.setStatus(stat);
		return e;
	}

}
