package hzk.util.hash;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JFileHasher implements ProgressAppreciable {
	private Log log = LogFactory.getLog(this.getClass());
	protected Collection<ProgressListener> listeners;

	private FileHashTask task;

	public JFileHasher() {
		listeners = new LinkedList<ProgressListener>();
	}

	@Override
	public void addListener(ProgressListener listener) {
		listeners.add(listener);

	}

	public void startHashTask(String path) {
		if (task == null)
			task = new FileHashTask(this);
		task.setFilePath(path);
		log.debug("startHashTask:" + path);
		task.start();
	}

	public void pauseCurrentTask() {
		task.toggleStatus();
	}

	public boolean isBuzy() {
		return task != null && task.isAlive();
	}

	public void resumeCurrentTask() {
		task.toggleStatus();
		synchronized (task) {
			task.notify();

		}

	}

}

class FileHashTask extends Thread {
	private Log log = LogFactory.getLog(this.getClass());
	public static final String ALGORITHM_SHA = "SHA-1";
	public static final String ALGORITHM_MD5 = "MD5";
	public static final int BUFFER_SIZE_OF_BYTE = 65536;
	protected String filePath, result;
	private int pgVal, pgMax;
	private long fpos, flen;
	private JFileHasher context;
	private RandomAccessFile f;
	private MessageDigest md;
	private Calendar beginTime;

	protected FileHashTask(JFileHasher context) {
		this.context = context;
	}

	protected void setFilePath(String filepath) {
		filePath = filepath;
	}

	int interval = 300;
	boolean threadSuspended = false;

	public void toggleStatus() {
		threadSuspended = !threadSuspended;
	}

	private void checkStatus() {
		if (threadSuspended) {
			try {
				synchronized (this) {
					while (threadSuspended)
						wait();
				}
			} catch (InterruptedException e) {
				log.error(e);
			}
		}
	}

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
				checkStatus();
				md.update(buffer, 0, nread);
				pgVal++;
				fpos += nread;
				publish();
			}
			result = HashUtil.toHexString(md.digest());
			publish();
		} catch (NoSuchAlgorithmException | IOException e) {
			log.error(e);
		}
	}

	private void publish() {

		for (ProgressListener li : context.listeners) {
			ProcessEvent e = new ProcessEvent();
			e.setBeginTime(beginTime);
			e.setOccurredTime(Calendar.getInstance());
			e.setNewValue(pgVal);
			e.setMaximum(pgMax);
			e.setResult(result);

			String stat = String.valueOf(fpos / 1024) + "KB/"
					+ String.valueOf(flen / 1024) + "KB";

			e.setStatus(stat);
			li.progressUpdated(e);
		}
	}
}
