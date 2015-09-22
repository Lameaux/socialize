package com.euromoby.socialize.core.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.euromoby.socialize.core.Config;

@RunWith(MockitoJUnitRunner.class)
public class HttpClientProviderTest {

	public static final int TIMEOUT = 123;
	public static final String PROXY_HOST = "proxy";
	public static final int PROXY_PORT = 8080;	
	
	@Mock
	Config config;

	SSLContextProvider sslContextProvider;
	
	@Before
	public void init() throws Exception {
		sslContextProvider = new SSLContextProvider();
		sslContextProvider.afterPropertiesSet();
		Mockito.when(config.getHttpUserAgent()).thenReturn("USER_AGENT");
	}
	
	@Test
	public void createHttpClient() {
		HttpClientProvider httpClientProvider = new HttpClientProvider(config, sslContextProvider);
		CloseableHttpClient closeableHttpClient = httpClientProvider.createHttpClient();
		assertNotNull(closeableHttpClient);
	}

	@Test
	public void createRequestConfigBuilder() {
		
		Mockito.when(config.getClientTimeout()).thenReturn(TIMEOUT);
		Mockito.when(config.getProxyHost()).thenReturn(PROXY_HOST);
		Mockito.when(config.getProxyPort()).thenReturn(PROXY_PORT);		
		
		HttpClientProvider httpClientProvider = new HttpClientProvider(config, sslContextProvider);
		RequestConfig.Builder builder = httpClientProvider.createRequestConfigBuilder();
		RequestConfig requestConfig = builder.build();
		assertEquals(TIMEOUT, requestConfig.getConnectTimeout());
		assertEquals(TIMEOUT, requestConfig.getSocketTimeout());
		
		assertEquals(PROXY_HOST, requestConfig.getProxy().getHostName());		
		assertEquals(PROXY_PORT, requestConfig.getProxy().getPort());
		
	}
	
}
