package com.euromoby.socialize.core.twitter;

import java.util.Collections;
import java.util.Map;

import org.apache.commons.collections4.map.LRUMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import com.euromoby.socialize.core.Config;
import com.euromoby.socialize.core.utils.StringUtils;


@Component
public class TwitterProvider {

	private Config config;

	private Map<String, String> requestTokens = Collections.synchronizedMap(new LRUMap<String, String>());

	@Autowired
	public TwitterProvider(Config config) {
		this.config = config;
	}

	protected Twitter getTwitter() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		// cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey(config.getTwitterKey());
		cb.setOAuthConsumerSecret(config.getTwitterSecret());

		if (!StringUtils.nullOrEmpty(config.getProxyHost())) {
			cb.setHttpProxyHost(config.getProxyHost());
			cb.setHttpProxyPort(config.getProxyPort());
		}
		cb.setHttpConnectionTimeout(config.getClientTimeout());
		cb.setHttpReadTimeout(config.getClientTimeout());

		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		return twitter;
	}

	public String getAuthorizationUrl(String callbackUrl) throws TwitterException {
		RequestToken requestToken = getTwitter().getOAuthRequestToken(callbackUrl);
		requestTokens.put(requestToken.getToken(), requestToken.getTokenSecret());
		return requestToken.getAuthorizationURL();
	}

	public AccessToken getAccessToken(String oauthToken, String oauthVerifier) throws TwitterException {
		String oauthTokenSecret = requestTokens.get(oauthToken);
		if (StringUtils.nullOrEmpty(oauthTokenSecret)) {
			throw new TwitterException("Token is invalid");
		}
		RequestToken requestToken = new RequestToken(oauthToken, oauthTokenSecret);
		AccessToken accessToken = getTwitter().getOAuthAccessToken(requestToken, oauthVerifier);
		return accessToken;
	}

	public User verifyCredentials(AccessToken accessToken) throws TwitterException {
		Twitter twitter = getTwitter();
		twitter.setOAuthAccessToken(accessToken);
		return twitter.verifyCredentials();

	}




}
