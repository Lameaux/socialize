package com.euromoby.socialize.web.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.euromoby.socialize.core.Config;
import com.euromoby.socialize.core.mail.MailCreator;
import com.euromoby.socialize.core.model.MailNew;
import com.euromoby.socialize.core.model.UserAccount;
import com.euromoby.socialize.core.service.MailService;
import com.euromoby.socialize.core.service.UserService;
import com.euromoby.socialize.web.Session;
import com.euromoby.socialize.web.dto.SignupDto;
import com.euromoby.socialize.web.dto.SignupStatusDto;
import com.euromoby.socialize.web.transform.UserAccountTransformer;

@Controller
public class WebsiteController {

	private static final Logger log = LoggerFactory.getLogger(WebsiteController.class);

	@Autowired
	private Session session;

	@Autowired
	private UserService userService;

	@Autowired
	private UserAccountTransformer userAccountTransformer;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private MailCreator mailBuilder;
	
	@Autowired
	private Config config;

	
	@RequestMapping(value = "/add-website", method = RequestMethod.GET)
	public String add(ModelMap model) {
		if (session.getUserAccount() == null) {
			return "redirect:/login";
		}
		
		model.put("pageTitle", "Add Website");
		model.put("appDomain", config.getAppDomain());
		return "website-add";
	}

	@RequestMapping(value = "/website", method = RequestMethod.GET)
	public String registered(ModelMap model, @RequestParam(value="user", required=false) Integer userId, @RequestParam(value="website", required=false) Integer websiteId) {
		session.setWebsiteId(websiteId);

		String loginUrl = config.getAppUrl() + "/login";
		if (websiteId != null) {
			loginUrl += "?website=" + websiteId;
		}		
		
		if (userId != null) {
			UserAccount userAccount = userService.findById(userId);
			if (userAccount != null && userAccount.isActive()) {
				return "redirect:" + loginUrl;
			}
		}
		
		model.put("pageTitle", "Registered");
		model.put("loginUrl", loginUrl);		
		return "registered";
	}	
	

	@RequestMapping(value = "/add-website", method = RequestMethod.POST, consumes = "application/json; charset=utf-8", produces = "application/json; charset=utf-8")
	public @ResponseBody
	SignupStatusDto addPost(@Valid @RequestBody(required = true) SignupDto user) {
		SignupStatusDto status = new SignupStatusDto();

		boolean error = false;
		if (!user.getPassword().equals(user.getPassword2())) {
			error = true;
		}

		if (userService.findByEmail(user.getEmail()) != null) {
			error = true;
		}

		if (!error) {
			UserAccount userAccount = userAccountTransformer.newUserAccount(user);
			userService.save(userAccount);
			
			try {
				MailNew emailConfirmation = mailBuilder.emailConfirmation(userAccount, session);
				mailService.sendMail(emailConfirmation);
			} catch (Exception e) {
				log.error("Unable to send confirmation email", e);
			}
			
			String redirectUrl = config.getAppUrl() + "/registered?user=" + userAccount.getId();
			if (session.getWebsiteId() != null) {
				redirectUrl += "&website=" + session.getWebsiteId();
			}
			status.setRedirectUrl(redirectUrl);
		}
		status.setError(error);
		return status;
	}
	
}
