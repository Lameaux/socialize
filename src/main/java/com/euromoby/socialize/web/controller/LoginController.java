package com.euromoby.socialize.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.euromoby.socialize.core.Config;
import com.euromoby.socialize.core.model.UserAccount;
import com.euromoby.socialize.core.service.UserService;
import com.euromoby.socialize.web.Session;
import com.euromoby.socialize.web.dto.LoginDto;
import com.euromoby.socialize.web.dto.LoginStatusDto;

@Controller
public class LoginController {

	@Autowired
	private Session session;

	@Autowired
	private UserService userService;

	@Autowired
	private Config config;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(ModelMap model) {

		model.put("pageTitle", "Login");
		return "login";
	}	

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json; charset=utf-8", produces = "application/json; charset=utf-8")
	public @ResponseBody
	LoginStatusDto loginPost(@Valid @RequestBody(required = true) LoginDto user) {
		LoginStatusDto status = new LoginStatusDto();

		UserAccount userAccount = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
		
		if (userAccount == null) {
			status.setError(true);
			status.setErrorText("Email or password is invalid");
			return status;
		}
		
		if (!userAccount.isActive()) {
			status.setError(true);
			status.setErrorText("Email address is not verified");
			return status;			
		}
		
		userAccount.setLastLogin(System.currentTimeMillis());
		userService.update(userAccount);

		session.setUserAccount(userAccount);
		status.setError(false);
		
		String redirectUrl = config.getAppUrl();
		if (session.getWebsite() != null) {
			redirectUrl = config.getWebsiteUrl().replace("{domain}", session.getWebsite());
		}		
		redirectUrl += "/login-callback";
		status.setRedirectUrl(redirectUrl);

		return status;
	}
	
	@RequestMapping(value = "/login-callback", method = RequestMethod.GET)
	public String callback(ModelMap model) {
		model.put("pageTitle", "Callback " + session.getWebsite());
		return "empty";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {
		session.setUserAccount(null);
		return "redirect:/";
	}	
	
}
