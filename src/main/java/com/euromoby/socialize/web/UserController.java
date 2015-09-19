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

import com.euromoby.socialize.core.Config;
import com.euromoby.socialize.core.mail.MailCreator;
import com.euromoby.socialize.core.model.MailNew;
import com.euromoby.socialize.core.model.UserAccount;
import com.euromoby.socialize.core.service.MailService;
import com.euromoby.socialize.core.service.UserService;
import com.euromoby.socialize.web.dto.CheckEmailStatusDto;
import com.euromoby.socialize.web.dto.LoginDto;
import com.euromoby.socialize.web.dto.LoginStatusDto;
import com.euromoby.socialize.web.dto.SignupDto;
import com.euromoby.socialize.web.dto.SignupStatusDto;
import com.euromoby.socialize.web.transform.UserAccountTransformer;

@Controller
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

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

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(ModelMap model) {
		model.put("pageTitle", "Login");
		return "login";
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
	
	@RequestMapping(value = "/check-email/{email}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody
	CheckEmailStatusDto registerCheckEmail(ModelMap model, @PathVariable("email") String email) {
		CheckEmailStatusDto status = new CheckEmailStatusDto();
		status.setEmail(email);
		status.setExists(userService.emailExists(email));
		return status;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = "application/json; charset=utf-8", produces = "application/json; charset=utf-8")
	public @ResponseBody
	SignupStatusDto signupPost(@Valid @RequestBody(required = true) SignupDto user) {
		SignupStatusDto status = new SignupStatusDto();
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
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json; charset=utf-8", produces = "application/json; charset=utf-8")
	public @ResponseBody
	LoginStatusDto loginPost(@Valid @RequestBody(required = true) LoginDto user) {
		LoginStatusDto status = new LoginStatusDto();

		UserAccount userAccount = userService.findUserByEmailAndPassword(user.getEmail(), user.getPassword());
		
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
		
		status.setError(false);
		String redirectUrl = config.getAppUrl();
		status.setRedirectUrl(redirectUrl);
		return status;
	}
	
}
