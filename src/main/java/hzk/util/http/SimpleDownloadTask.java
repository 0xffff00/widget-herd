package hzk.util.http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.protocol.HttpContext;

import hzk.util.ProgressEvent;
import hzk.util.ProgressTask;
import hzk.util.Task;

public class SimpleDownloadTask extends Task {
	private String readURI;
	private String saveURI;
	private DefaultHttpClient httpclient;
	private long flen;
	private long fpos;

	public SimpleDownloadTask(String readURI,String saveURI) {
		this.readURI = readURI;
		this.saveURI = saveURI;
		httpclient = new DefaultHttpClient();

	}

	@Override
	public void run() {
		HttpResponse response = null;
		HttpGet httpget = new HttpGet(readURI);
		MyRedirectStrategy st = new MyRedirectStrategy();
		httpclient.setRedirectStrategy(st);
		try {
			response = httpclient.execute(httpget);

		} catch (IOException e) {
			log.error("GET:" + readURI, e);
			return;
		}
		HttpEntity entity = response.getEntity();
		flen = entity.getContentLength();

		log.debug("GET:" + readURI);
		log.debug(response.getStatusLine() + "; LENGTH=" + flen);
		log.debug(st.getLastRedirectedURI());

		try {
			InputStream ins = entity.getContent();
			OutputStream outs = new FileOutputStream(saveURI);
			IOUtils.copyLarge(ins, outs);
			ins.close();
			outs.close();
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}

	}

	

}
