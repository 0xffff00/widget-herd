package hzk.util.http;

import java.net.URI;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultRedirectHandler;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.protocol.HttpContext;

public class MyRedirectStrategy extends DefaultRedirectStrategy {

	private URI lastRedirectedURI;
    @Override
	public URI getLocationURI(HttpRequest arg0, HttpResponse arg1,
			HttpContext arg2) throws ProtocolException {

    	lastRedirectedURI= super.getLocationURI(arg0, arg1, arg2);
    	return lastRedirectedURI;
	}

    
	public URI getLastRedirectedURI() {
		return lastRedirectedURI;
	}



}