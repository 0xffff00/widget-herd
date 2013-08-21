package hzk.util.nomin;


import static org.junit.Assert.*;

import hzk.util.nomin.WikisNomination;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

public class WikisNominationsTest {
	private Log log = LogFactory.getLog(this.getClass());
	String[] params = { "nhk.蝙蝠侠.黑暗骑士4.The.Flowers.Of.War.2011-2022.IMAX.BD.MiniSD-TLF.mkv",
			"金陵十三钗.The.Flowers.Of.War.DvD.BD.MiniSD-TLF.mkv",
			"The.Flowers.Of.War.2011.BD.MiniSD-TLF.mkv",
			"[秋菊打官司].The.Story.of.Qiu.Ju.1993.DVD.x264.AAC.HALFCD-TLF.mkv",
			"[对我说(限量版)].Talk.to.Me.LIMITTED.2007.x264.halfcd-TLF.MKV",
			"[大事件].Breaking.News.2004.BD-RMVB-人人影视-levn.RMVB"

	};

	@Before
	public void setUp() throws Exception {
	}

	//@Test
	public void test1() {
		//String param = params[2];
		String regex = "[\u2E80-\u9FFF]+";		
		Pattern p = Pattern.compile(regex);
		log.info("<REGEX> " +p.pattern());
		for (String param : params) {
			log.info("<INPUT> " + param);			
			String val = null;

			Matcher m = p.matcher(param);
			// log.info(m.group());
			
			//log.info(m.replaceAll("ssss"));
			while (m.find()) {
				val = m.group();
				log.info("<MATCH> " + val);
			}

		}
	}

		
	@Test
	public void test2(){
		WikisNomination wn;
		wn=WikisNominations.parse(params[0]);
		//log(wn);
		assertEquals(wn.warezSource,"BD");
		assertEquals(wn.warezProvider,"TLF");
		
		wn=WikisNominations.parse(params[5]);
		log(wn);
		assertEquals(wn.nameEng,"Breaking News ");
		assertEquals(wn.year1,"2004");
		assertEquals(wn.warezSource,"BD");
		assertEquals(wn.warezProvider,"人人影视");
	}
	
	@Test
	public void testRegex(){
		assertTrue(Pattern.compile("\\?").matcher("e34?21.a").find());		
		Matcher m=Pattern.compile("(?<=\\.)m1(?=[\\.\\-~])|(?<=\\.)m2(?=\\.)|(?<=\\.)m3(?=\\.)").matcher("m3m2s.m1-m2..m3..7BD-11,,aBVDVD-3345");
		int x=0;
		while(m.find()) x++;
		log.info("findCount="+x);
		m.reset();
		while(m.find()){
			log.info("find:groupCount="+m.groupCount());
			log.info(m.group()+"\t\t"+m.start());
			for (int i=1;i<=m.groupCount();i++){
				log.info("group "+i+": "+m.group(i)+"\t\t"+m.start(1));
			}
			
			
		}
		
		
		
		
	}

	private void log(WikisNomination arg){
		Field[] fields=arg.getClass().getDeclaredFields();
		for (Field field:fields){
			try {
				log.info("<log>"+field+"="+field.get(arg));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				log.error(e);
			}
		}
	}

}
