package com.euromoby.socialize.web;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.euromoby.socialize.core.mail.MailCreator;
import com.euromoby.socialize.core.model.MailNew;
import com.euromoby.socialize.core.model.UserAccount;
import com.euromoby.socialize.core.service.MailService;
import com.euromoby.socialize.core.service.UserService;
import com.euromoby.socialize.web.dto.CheckEmailStatusDto;
import com.euromoby.socialize.web.dto.RegisterUserDto;
import com.euromoby.socialize.web.dto.RegisterUserStatusDto;
import com.euromoby.socialize.web.transform.UserAccountTransformer;

@Controller
public class RegisterController {

	private static final Logger log = LoggerFactory.getLogger(RegisterController.class);

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

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(ModelMap model) {
		model.put("pageTitle", "Register");
		return "register";
	}

	@RequestMapping(value = "/registered", method = RequestMethod.GET)
	public String registered(ModelMap model) {
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
	
	@RequestMapping(value = "/register/check-email/{email}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody
	CheckEmailStatusDto registerCheckEmail(ModelMap model, @PathVariable("email") String email) {
		CheckEmailStatusDto status = new CheckEmailStatusDto();
		status.setEmail(email);
		status.setExists(userService.emailExists(email));
		return status;
	}

	@RequestMapping(value = "/register/user", method = RequestMethod.POST, consumes = "application/json; charset=utf-8", produces = "application/json; charset=utf-8")
	public @ResponseBody
	RegisterUserStatusDto registerUser(@Valid @RequestBody(required = true) RegisterUserDto user) {
		RegisterUserStatusDto status = new RegisterUserStatusDto();
		status.setUser(user);

		boolean error = false;
		if (!user.getPassword().equals(user.getPassword2())) {
			error = true;
		}

		if (userService.emailExists(user.getEmail())) {
			error = true;
		}

		if (!error) {
			UserAccount userAccount = userAccountTransformer.newUserAccount(user);
			userService.save(userAccount);
			
			try {
				MailNew emailConfirmation = mailBuilder.emailConfirmation(userAccount);
				mailService.sendMail(emailConfirmation);
			} catch (Exception e) {
				log.error("Unable to send confirmation email", e);
			}
			
			status.setId(userAccount.getId());
		}
		status.setError(error);
		return status;
	}
	
	
}
