package hzk.util;

import static org.junit.Assert.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

public class TaskTest {
	private Log log = LogFactory.getLog(this.getClass());
	Task task;
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void test() throws InterruptedException {
		task=new Task(){

			@Override
			public void run() {
				long st=System.currentTimeMillis();
				boolean t=1>0;
				while(t){
					if (isPaused()){
						log.info("pause is true");
						letThreadWait();
					}
					else log.info("pause is false");
					log.info(System.currentTimeMillis()-st);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				log.info("run end.");
			}
			
		};
		log.info("start");
		task.start();
		Thread.sleep(8000);
		
		log.info("pause");
		task.pause();
		//Thread.sleep(500);
		
		assertTrue(task.isAlive());
		log.info("resume");
		task.resume();
		assertTrue(task.isAlive());
		Thread.sleep(8000);
		//task.resume();
		//log.debug(task.isPaused());
		log.info("test end");
	}
	
	

}
