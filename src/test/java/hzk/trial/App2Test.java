package hzk.trial;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class App2Test {
	App2 a;
	@Before
	public void setUp() throws Exception {
		a=new App2();
	}

	@Test
	public void testSay() {
		assertNotSame(a.say(),"helldo22122d");
	}

}
