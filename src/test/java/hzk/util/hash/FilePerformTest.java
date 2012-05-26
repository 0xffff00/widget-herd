package hzk.util.hash;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FilePerformTest {

	private Log log = LogFactory.getLog(FilePerformTest.class);
	static String[] paths=new String[10],res=new String[10];
	byte[] resBytes;String path,result,resultExpected;
	JEncryptor je=new JEncryptor();
		
	@Before
	public void setUp() throws Exception {
		int x =6;		
		path=TEST_DATA.params[x];
		resultExpected=TEST_DATA.answers[x];
		log.info("file size:"+new File(path).length());
	}

	@Test
	public void testNioFiles() throws IOException {
		log.info("Files.readAllBytes in package 'java.nio.file' of JDK7	");
		Path ppath = Paths.get(path);
		resBytes = Files.readAllBytes(ppath);
		logAndAssertResult();
				
	}

	@Test
	public void testIOUtils() throws IOException {
		log.info("IOUtils.toByteArray in  Apache Commons 2.3");
		InputStream ios = new FileInputStream(path);
		resBytes = IOUtils.toByteArray(ios);
		logAndAssertResult();
	}

	@Test
	public void testFileUtils() throws IOException {
		log.info("FileUtils.readFileToByteArray in  Apache Commons 2.3");
		resBytes = FileUtils.readFileToByteArray(new File(path));
		logAndAssertResult();
	}

	@Test
	public void testRandomAccessFile() throws IOException {
		log.info("RandomAccessFile: an old native class in  JDK 1-7");
		RandomAccessFile f = new RandomAccessFile(path, "r");
		byte[] b = new byte[(int) f.length()];
		f.read(b);
		resBytes = b;
		logAndAssertResult();
	}

	public void logAndAssertResult(){
		//result =JEncryptor.toHexString(je.SHA1(resBytes));
		//log.info(resBytes.length+"===="+jd.calcSHA1(Arrays.copyOfRange(resBytes, 300, 1900)));
		assert(result.equalsIgnoreCase(resultExpected));
	}
	@After
	public void list() {
		//log.info(result);
	}
}
