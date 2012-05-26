package hzk.util.hash;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.security.MessageDigest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileHashPerformTest {
	private Log log = LogFactory.getLog(this.getClass());
	JEncryptor je = new JEncryptor();

	@BeforeClass
	public static void initTestData() {

	}

	@Before
	public void setUp() throws Exception {

	}

	//@Test
	public void testSHA1Task() {
		int x = 1;
		String myAns="" ;
		//je.newSHA1Task(TEST_DATA.params[x]);
		log.info(myAns);
		assertTrue(myAns.equalsIgnoreCase(TEST_DATA.answers[x]));
	}

	@Test
	public void testSHA1ofFile() {
		int x = 2;
		String myAns = je.SHA1ofFile(TEST_DATA.params[x]);
		//log.info(TEST_DATA.params[x]);
		//log.info(myAns);
		//log.info(TEST_DATA.answers[x]);
		assertTrue(myAns.equalsIgnoreCase(TEST_DATA.answers[x]));
	}
	

	@Test
	public void testSHA1() {
		assertEquals(je.SHA1(TEST_DATA.params[8]), TEST_DATA.answers[8].toLowerCase());
		assertEquals(je.SHA1(TEST_DATA.params[9]), TEST_DATA.answers[9].toLowerCase());
	}
	
	

	@Test
	public void testFIS() throws Exception {

		String datafile = TEST_DATA.params[1];

		MessageDigest md = MessageDigest.getInstance("SHA1");
		FileInputStream fis = new FileInputStream(datafile);
		byte[] dataBytes = new byte[1024];

		int nread = 0;

		while ((nread = fis.read(dataBytes)) != -1) {
			md.update(dataBytes, 0, nread);
		}
		;

		byte[] mdbytes = md.digest();

		// convert the byte to hex format
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < mdbytes.length; i++) {
			sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		
		assertEquals( sb.toString(), TEST_DATA.answers[1].toLowerCase());
		
	}
}
