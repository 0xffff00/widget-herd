package hzk.util.http;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.junit.Before;
import org.junit.Test;

public class DoubanAPILoaderTest {
	protected Log log = LogFactory.getLog(this.getClass());
	DouBanAPILoadTask task=DoubanAPILoader.createTask();
	
	@Before
	public void setUp(){
		
	}
	@Test
	public void testStringify(){
		assertEquals(task.stringify(new String[]{"4 ","t" ," kkk "}),"4%20t%20kkk");
	}
	@Test
	public void test() throws DocumentException {
		DouBanAPILoadTask task1=DoubanAPILoader.createTask();
		String[] keys={"the dark knight","2008"};
		Document document=
		task1.parseDoubanXML(task1.searchMovie(keys, 1, 1));
		Element rootElem=document.getRootElement();
		log.info(rootElem.getQualifiedName());
		
		Element entry=rootElem.element("entry");
		for (Object obj:entry.elements()){
			Element elem=(Element)obj;
			
			//log.info(elem.getQualifiedName());
		}
		Element rating=rootElem.element("entry").element("rating");
		log.info(rating.attributeValue("average"));
		//Element imdb=rootElem.element("entry").elements("attribute");
		Node n=document.selectSingleNode("/feed");
		/*
		log.info(n.getNodeTypeName()+n.getName());		
		Element e1=(Element)n;
		for (Object o:e1.elements()){
			Node e=(Node)o;
			log.info(e.getNodeTypeName()+e.getName());		
		}*/
		List<?> nodes=document.selectNodes("/feed/title");		
		for (Object node:nodes){
			Node e=(Node)node;
			log.info(e.getNodeTypeName()+e.getName());		
		}
		//log.info(document.asXML());
		Element feed=(Element) document.selectSingleNode("/feed");
		 XPath x = document.createXPath("//w3:author");
		 log.debug(x.selectNodes(document));	
		Element n1=(Element) feed.selectSingleNode("/w3:feed/w3:entry//db:attribute[@name='aka']");
		log.info(n1.getNodeTypeName()+','+n1.getName()+','+n1.getData());		
		Attribute n2=(Attribute) feed.selectSingleNode("//w3:entry//gd:rating/@numRaters");
		log.info(n2.getNodeTypeName()+','+n2.getName()+','+n2.getData());
	}

}
