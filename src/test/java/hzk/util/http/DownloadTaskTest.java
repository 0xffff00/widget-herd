package hzk.util.http;

import static org.junit.Assert.*;
import hzk.util.ProgressEvent;
import hzk.util.ProgressObserver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class DownloadTaskTest {
	protected Log log = LogFactory.getLog(this.getClass());
	@Test
	public void test() throws InterruptedException {
		log.info("s");
		String url="http://down.tech.sina.com.cn/download/d_load.php?d_id=28382&down_id=1&ip=218.242.204.99";
		String url1="http://api.douban.com/movie/subjects?q=the%20dark%20knight";
		DownloadTask task =new DownloadTask(url);
		task.addObserver(new ProgressObserver(){

			@Override
			public void progressUpdated(ProgressEvent e) {
				if (e.isCompleted()){
					log.info("COMPLETED");
				}
				
			}
			
		});
		task.start();
		int x=0;
		long m=System.currentTimeMillis();
		while(true){
			if (!task.isAlive())
				break;
				Thread.sleep(400);
				log.info(System.currentTimeMillis()-m);
		}
		//task.join();
		//Thread.sleep(5000);
		log.info(null);
		assertTrue(true);
	}

}
