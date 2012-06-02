package hzk.util.hash;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class JFileHasherTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSimpleSHA1() {
		assertEquals(JFileHasher.simpleSHA1(TEST_DATA.params[1]),TEST_DATA.answers[1].toLowerCase());
		assertEquals(JFileHasher.simpleSHA1(TEST_DATA.params[2]),TEST_DATA.answers[2].toLowerCase());
		
		assertNull(JFileHasher.simpleSHA1("dffdgdfg"));
	}

}
