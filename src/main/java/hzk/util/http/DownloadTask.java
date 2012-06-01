package hzk.util.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import hzk.util.ProgressEvent;
import hzk.util.ProgressTask;

public class DownloadTask extends ProgressTask {
	private String uri;
	private HttpClient httpclient;
	private long flen;
	private long fpos;
	public DownloadTask(String uri){
		this.uri=uri;
		httpclient=new DefaultHttpClient();
	}
	@Override
	public void run() {		
		HttpResponse response = null;		
		try {			
			response= httpclient.execute(new HttpGet(uri));
		} catch (IOException e) {			
			log.error("GET:"+uri,e);
			return;
		}
		HttpEntity entity = response.getEntity();		
		flen=entity.getContentLength();
		log.info("GET:"+uri);
		log.info(response.getStatusLine()+"; LENGTH="+flen);
		
		
		publish(COMPLETE);
	}

	@Override
	protected ProgressEvent createEvent() {
		// TODO Auto-generated method stub
		return null;
	}


}
