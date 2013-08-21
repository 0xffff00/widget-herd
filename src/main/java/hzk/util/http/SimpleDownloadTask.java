package hzk.util.http;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import hzk.util.Task;

public class SimpleDownloadTask extends Task {
	private String readURI;
	private String saveURI;
	private DefaultHttpClient httpclient;
	private MyRedirectStrategy redirectStrategy;
	private long flen;
	long MAX_FILE_SIZE=1024*1024;
	int CONN_TIMEOUT=10000;
	public SimpleDownloadTask(String readURI,String saveURI) {
		this.readURI = readURI;
		this.saveURI = saveURI;
		httpclient = new DefaultHttpClient();
		HttpParams params=httpclient.getParams();
		HttpConnectionParams.setSoTimeout(params, CONN_TIMEOUT);
		HttpConnectionParams.setConnectionTimeout(params, CONN_TIMEOUT);
		redirectStrategy = new MyRedirectStrategy();
		httpclient.setRedirectStrategy(redirectStrategy);
	}

	@Override
	public void run() {
		HttpResponse response = null;
		HttpGet httpget = new HttpGet(readURI);
		try {
			response = httpclient.execute(httpget);

		} catch (IOException e) {
			log.error("GET:" + readURI, e);
			return;
		}
		HttpEntity entity = response.getEntity();
		flen = entity.getContentLength();
		if (flen>MAX_FILE_SIZE || flen <=0){
			log.error("this file ("+flen+" bytes) is too large or empty.");
			return;
		}
		
		log.debug("GET:" + readURI);
		log.debug(response.getStatusLine() + "; LENGTH=" + flen);
		log.debug(redirectStrategy.getLastRedirectedURI());
		
		InputStream ins = null;
		OutputStream outs = null;
		try {
			ins = entity.getContent();
			outs = new FileOutputStream(saveURI);
			IOUtils.copyLarge(ins, outs);			
		} catch (IllegalStateException | IOException e) {
			log.error(null,e);
		}finally{
			IOUtils.closeQuietly(ins);
			IOUtils.closeQuietly(outs);
		}

	}
	
	

}
