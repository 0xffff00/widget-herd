package hzk.util.hash;

import static org.junit.Assert.*;

import hzk.util.CSHA1Executor;

import java.io.FileInputStream;
import java.security.MessageDigest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class JHashCHashPerformCompareTest {
	private Log log = LogFactory.getLog(this.getClass());
	JFileHasher jfh = new JFileHasher();
	
	@Before
	public void setUp() throws Exception {

	}
	int x=5;
	@Test
	public void testJHash() {
		/*
		jfh.addListener(new ProgressListener(){
			
			@Override
			public void progressUpdated(ProcessEvent e) {
				if (e.isCompleted()){

					log.info(e.getResult());
					assertTrue(e.getResult().equalsIgnoreCase(TEST_DATA.answers[x]));
				}
				
			}
			
		});
		jfh.startHashTask(TEST_DATA.params[x]);
		*/
		JEncryptor je=new JEncryptor();
		String myAns=je.SHA1ofFile(TEST_DATA.params[x]);
		assertTrue(myAns.equalsIgnoreCase(TEST_DATA.answers[x]));
	}

	@Test
	public void testCHash() {
		String myAns = CSHA1Executor.calculate(TEST_DATA.params[x]);
		//log.info(TEST_DATA.params[x]);
		//log.info(myAns);
		//log.info(TEST_DATA.answers[x]);
		assertTrue(myAns.equalsIgnoreCase(TEST_DATA.answers[x]));
	}
	

}
