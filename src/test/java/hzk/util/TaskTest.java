package hzk.util;

import static org.junit.Assert.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

public class TaskTest {
	private Log log = LogFactory.getLog(this.getClass());
	Task task1,task2;
	@Before
	public void setUp() throws Exception {
		task1=new Task(){
			@Override
			public void run() {
				long st=System.currentTimeMillis();
				int x=0;
				log.info("$1 run start.");
				while(x++<8){
					if (isCancelled())
						break;
					if (isPaused()){
						log.info("letThreadWait");
						letThreadWait();
					}
					log.info("$1-#"+x+": @"+(System.currentTimeMillis()-st));
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				log.info("$1 run end.");
			}
			
		};
		task2=new Task(){
			@Override
			public void run() {
				long st=System.currentTimeMillis();
				int x=0;
				log.info("$2 run start.");
				while(x++<12){
					if (isCancelled())
						break;
					if (isPaused()){
						log.info("letThreadWait");
						letThreadWait();
					}
					log.info("$2-#"+x+": @"+(System.currentTimeMillis()-st));
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				log.info("$2 run end.");
			}
			
		};
		
	}

	//@Test
	public void testSimpleTask() throws InterruptedException {
		
		log.info("task start");
		task1.start();
		Thread.sleep(2000);
		
		log.info("task pause");
		task1.pause();
		Thread.sleep(2000);
		
		assertTrue(task1.isAlive());
		log.info("task resume");
		task1.resume();
		assertTrue(task1.isAlive());
		Thread.sleep(1000);
		task1.cancel();
		Thread.sleep(1000);
		assertTrue(!task1.isAlive());
		log.info("test end");
	}
	
	//@Test
	public void testTasksSerialize() throws InterruptedException{
		
		Task t=new TaskSequence(task1,task2);
		t.start();
		Thread.sleep(2000);		
		t.pause();
		Thread.sleep(2000);
		t.resume();
		
		Thread.sleep(3000);
		t.pause();
		Thread.sleep(3000);
		t.resume();
		Thread.sleep(8000);
		log.info("test end");
	}
	
	@Test
	public void testTasksJoin() throws InterruptedException{
		
		task1.start();
		
		task1.join();
		task2.start();
		task2.join();
		/*
		Thread.sleep(3000);
		task1.pause();
		Thread.sleep(3000);
		task1.resume();
		Thread.sleep(3000);*/
		log.info("test end");
	}
}
