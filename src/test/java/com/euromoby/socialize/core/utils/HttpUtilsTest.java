package com.euromoby.socialize.core.utils;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicStatusLine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.euromoby.socialize.core.http.HttpClientProvider;

@RunWith(MockitoJUnitRunner.class)
public class HttpUtilsTest {

	public static final String URL = "http://example.com";	
	public static final byte[] RESPONSE = new byte[] {0,1,2,3,4,5};
	
	@Mock
	HttpClientProvider httpClientProvider;	
	@Mock
	CloseableHttpResponse response;
	
	
	@Before
	public void init() {
		
		Mockito.when(httpClientProvider.createRequestConfigBuilder()).thenReturn(RequestConfig.custom());
	}	
	
	@Test
	public void loadUrl() throws IOException {
		StatusLine statusLine = new BasicStatusLine(new ProtocolVersion("HTTP", 1, 1), 200, "OK"); 
		Mockito.when(response.getStatusLine()).thenReturn(statusLine);

		Mockito.when(response.getEntity()).thenReturn(new ByteArrayEntity(RESPONSE));
		
		Mockito.when(httpClientProvider.executeRequest(Mockito.any(HttpUriRequest.class))).thenReturn(response);
		Assert.assertArrayEquals(RESPONSE, HttpUtils.loadUrl(httpClientProvider, URL));
	
	}

	@Test
	public void notFound() throws IOException {
		StatusLine statusLine = new BasicStatusLine(new ProtocolVersion("HTTP", 1, 1), 404, "Not Found"); 
		Mockito.when(response.getStatusLine()).thenReturn(statusLine);

		Mockito.when(response.getEntity()).thenReturn(new ByteArrayEntity(RESPONSE));
		
		Mockito.when(httpClientProvider.executeRequest(Mockito.any(HttpUriRequest.class))).thenReturn(response);
		
		try {
			HttpUtils.loadUrl(httpClientProvider, URL);
			Assert.fail();
		} catch (IOException e) {
			assertEquals("404 Not Found",  e.getMessage());
		}
	
	}	
	
}
