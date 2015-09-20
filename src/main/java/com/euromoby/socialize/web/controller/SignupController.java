package com.euromoby.socialize.web.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.euromoby.socialize.web.dto.CheckEmailStatusDto;
import com.euromoby.socialize.web.dto.CheckUserStatusDto;
import com.euromoby.socialize.web.dto.SignupDto;
import com.euromoby.socialize.web.dto.SignupStatusDto;
import com.euromoby.socialize.web.transform.UserAccountTransformer;

@Controller
public class SignupController {

	private static final Logger log = LoggerFactory.getLogger(SignupController.class);

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

	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(ModelMap model) {
		model.put("pageTitle", "Sign Up");
		return "signup";
	}

	@RequestMapping(value = "/registered", method = RequestMethod.GET)
	public String registered(ModelMap model, @RequestParam(value="user", required=false) Integer userId) {
		
		if (userId != null) {
			UserAccount userAccount = userService.findById(userId);
			if (userAccount != null && userAccount.isActive()) {
				return "redirect:/login";
			}
		}
		
		model.put("pageTitle", "Registered");
		return "registered";
	}	
	
	@RequestMapping(value = "/email-verified", method = RequestMethod.GET)
	public String emailVerified(ModelMap model) {
		
		model.put("pageTitle", "Email Verified");
		return "email-verified";
	}	
	
	@RequestMapping(value = "/verify-email/{uuid}", method = RequestMethod.GET)
	public String verifyEmail(ModelMap model, @PathVariable("uuid") String uuid) {
		
		UserAccount userAccount = userService.findByUuid(uuid);
		if (userAccount == null) {
			return "redirect:/";
		}
		if (!userAccount.isActive()) {
			userAccount.setActive(true);
			userService.update(userAccount);
		}
		
		return "redirect:/email-verified";
	}
	
	@RequestMapping(value = "/check-email/{email}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody
	CheckEmailStatusDto checkEmail(ModelMap model, @PathVariable("email") String email) {
		CheckEmailStatusDto status = new CheckEmailStatusDto();
		UserAccount userAccount = userService.findByEmail(email);
		if (userAccount != null) {
			status.setExists(true);
		}
		return status;
	}

	@RequestMapping(value = "/check-user/{id}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody
	CheckUserStatusDto checkUser(ModelMap model, @PathVariable("id") Integer id) {
		CheckUserStatusDto status = new CheckUserStatusDto();
		UserAccount userAccount = userService.findById(id);
		if (userAccount != null) {
			status.setActive(userAccount.isActive());
		}
		return status;
	}	
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = "application/json; charset=utf-8", produces = "application/json; charset=utf-8")
	public @ResponseBody
	SignupStatusDto signupPost(@Valid @RequestBody(required = true) SignupDto user) {
		SignupStatusDto status = new SignupStatusDto();

		if (!user.getPassword().equals(user.getPassword2())) {
			status.setError(true);
			return status;
		}

		if (userService.findByEmail(user.getEmail()) != null) {
			status.setError(true);
			return status;
		}

		UserAccount userAccount = userAccountTransformer.newUserAccount(user);
		userService.save(userAccount);

		try {
			MailNew emailConfirmation = mailBuilder.emailConfirmation(userAccount, session);
			mailService.sendMail(emailConfirmation);
		} catch (Exception e) {
			log.error("Unable to send confirmation email", e);
		}

		String redirectUrl = config.getAppUrl();
		if (session.getWebsite() != null) {
			redirectUrl = config.getWebsiteUrl().replace("{domain}", session.getWebsite());
		}
		redirectUrl += "/registered?user=" + userAccount.getId();
		status.setRedirectUrl(redirectUrl);
		
		status.setError(false);
		return status;
	}
	
}
