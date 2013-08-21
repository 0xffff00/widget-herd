package hzk.util.http;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import hzk.util.ProgressEvent;
import hzk.util.ProgressObserver;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class SimpleDownloadTaskTest {
	protected Log log = LogFactory.getLog(this.getClass());
	String url = "http://down.tech.sina.com.cn/download/d_load.php?d_id=28382&down_id=1&ip=218.242.204.99";
	String url3="http://releases.ubuntu.com/precise/ubuntu-12.04-desktop-i386.iso";
	String url1 = "http://api.douban.com/movie/subjects?q=the%20dark%20knight";
	String file1="D:/var/hzk/herd/tmp/tdk.xml";
	String save1="D:/var/hzk/herd/tmp/3";
	String save2="D:/var/hzk/herd/tmp/2";
	//@Test 
	public void testIOUtils() throws IOException{
		InputStream input = new FileInputStream(file1);
		OutputStream output = new FileOutputStream(save2);
		IOUtils.copy(input, output);
		
	}
	@Test
	public void test() throws InterruptedException {		
		SimpleDownloadTask task = new SimpleDownloadTask(url3,save1);
		task.start();
		
		task.join();
		
		log.info(null);
		assertTrue(true);
	}

}
