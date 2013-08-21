package hzk.util.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import hzk.util.Task;

public class DoubanAPILoader {
	protected Log log = LogFactory.getLog(this.getClass());

	public static DouBanAPILoadTask createTask() {
		DouBanAPILoadTask task = new DouBanAPILoadTask();
		return task;
	}

}

class DouBanAPILoadTask extends Task {
	HttpClient httpclient = new DefaultHttpClient();
	List<String> querys;
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	public InputStream searchMovie(String[] keys, int pagin_page, int pagin_size) {
		String url = new StringBuilder()
				.append("http://api.douban.com/movie/subjects?q=")
				.append(stringify(keys)).append("&start-index=")
				.append(pagin_page).append("&max-results=").append(pagin_size)
				.toString();
		log.info("url= " + url);
		
		try {
			HttpResponse response = httpclient.execute(new HttpGet(url));
			
			log.debug(response.getStatusLine());
			HttpEntity entity = response.getEntity();
			log.info("length="+entity.getContentLength());
			return entity.getContent();
		} catch (IOException e) {
			log.error(null, e);
			return null;
		}

	}

	public Document parseDoubanXML(InputStream ins) throws DocumentException {
		SAXReader reader = new SAXReader();
		Map<String, String> namespaceURIs = new HashMap<String, String>();
		namespaceURIs.put("w3", "http://www.w3.org/2005/Atom");
		namespaceURIs.put("db", "http://www.douban.com/xmlns/");
		namespaceURIs.put("gd", "http://schemas.google.com/g/2005");
		reader.getDocumentFactory().setXPathNamespaceURIs(namespaceURIs);
		return reader.read(ins);
	}

	public String stringify(String[] args) {
		StringBuilder sb = new StringBuilder();
		for (String arg : args) {
			sb.append(arg.trim()).append(' ');
		}
		return sb.toString().trim().replaceAll(" ", "%20");
	}
}