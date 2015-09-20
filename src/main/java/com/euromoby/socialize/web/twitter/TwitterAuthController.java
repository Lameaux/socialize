package com.euromoby.socialize.web.twitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.auth.AccessToken;

import com.euromoby.socialize.core.model.AuthUser;
import com.euromoby.socialize.core.twitter.TwitterProvider;
import com.euromoby.socialize.core.utils.StringUtils;
import com.euromoby.socialize.web.Session;

@Controller
@RequestMapping("/twitter")
public class TwitterAuthController {

	private static final Logger log = LoggerFactory.getLogger(TwitterAuthController.class);
	
	@Autowired
	private Session session;

	@Autowired
	private TwitterProvider twitterProvider;
	
	@RequestMapping("/auth")
	public String twitterAuth(ModelMap model) {
		String callbackUrl = "http://euromoby.com:8080/twitter/callback";
		try {
			String authorizationUrl = twitterProvider.getAuthorizationUrl(callbackUrl);
			return "redirect:" + authorizationUrl;
		} catch (Exception e) {
			log.error("Unable to get auth url", e);
		}
		return "redirect:/";
	}

	@RequestMapping("/callback")	
	public String twitterCallback(ModelMap model, @RequestParam(value="oauth_token", required=false) String oAuthToken, @RequestParam(value="oauth_verifier", required=false) String oAuthVerifier, @RequestParam(value="denied", required=false) String denied) {
		
		if (!StringUtils.nullOrEmpty(denied)) {
			log.debug("auth denied", denied);
			return "redirect:/";			
		}
		
		try {
			AccessToken accessToken = twitterProvider.getAccessToken(oAuthToken, oAuthVerifier);
			User user = twitterProvider.verifyCredentials(accessToken);
			//session.setAuthUser(createAuthUser(user));
			return "redirect:/profile";
		} catch (TwitterException e) {
			log.debug("auth failed", e);
		}
		
		return "redirect:/";
	}
	
	private AuthUser createAuthUser(User user) {
		AuthUser authUser = new AuthUser();
		authUser.setProvider("twitter");
		authUser.setProviderId(String.valueOf(user.getId()));
		authUser.setLogin(user.getScreenName());
		authUser.setName(user.getName());
		authUser.setImage(user.getProfileImageURL());
		return authUser;
	}
}
