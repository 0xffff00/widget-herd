package hzk.util.hash;

import hzk.util.ProgressEvent;
import hzk.util.ProgressTask;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JFileHashTask extends ProgressTask{
	private Log log = LogFactory.getLog(this.getClass());
	public static final String ALGORITHM_SHA = "SHA-1";
	public static final String ALGORITHM_MD5 = "MD5";
	public static final int BUFFER_SIZE_OF_BYTE = 65536;
	protected String filePath, result;
	private int pgVal, pgMax;
	private long fpos, flen;	
	private RandomAccessFile f;
	private MessageDigest md;
	private Calendar beginTime;
	
	
	protected JFileHashTask(String filepath) {
		this.filePath = filepath;
	}

	
	@Override
	public void run() {
		beginTime = Calendar.getInstance();
		byte[] buffer = new byte[BUFFER_SIZE_OF_BYTE];
		try {
			f = new RandomAccessFile(filePath, "r");
			md = MessageDigest.getInstance(ALGORITHM_SHA);
			pgVal = 0;
			fpos = 0;
			flen = f.length();
			pgMax = (int) ((flen - 1) / BUFFER_SIZE_OF_BYTE) + 1;
			int nread = 0;
			while ((nread = f.read(buffer)) != -1) {
				if (isCancelled()) {
					break;
				}
				if (isPaused()){
					publish(ProgressEvent.PAUSE);
					letThreadWaiting();
				}
				md.update(buffer, 0, nread);
				pgVal++;
				fpos += nread;
				publish();
			}
			
			if (isCancelled()){
				result= "Hash Canncelled";
				publish(ProgressEvent.CANCEL);
			}else{
				result = HashUtil.toHexString(md.digest());
				publish(ProgressEvent.COMPLETE);
			}
				
		} catch (NoSuchAlgorithmException | IOException e) {
			log.error(null,e);
			result= e.toString();
			publish(ProgressEvent.ERROR);
		}
	}

	@Override
	protected ProgressEvent createEvent() {
		ProgressEvent e = new ProgressEvent();
		e.setBeginTime(beginTime);
		e.setOccurredTime(Calendar.getInstance());
		e.setNewValue(pgVal);
		e.setMaximum(pgMax);
		e.setResult(result);

		String stat = String.valueOf(fpos / 1024) + "KB/"
				+ String.valueOf(flen / 1024) + "KB";
		e.setStatus(stat);
		return e;
	}

}
