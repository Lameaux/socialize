package com.euromoby.socialize.web;

import java.util.UUID;

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
import com.euromoby.socialize.core.model.PasswordResetRequest;
import com.euromoby.socialize.core.model.UserAccount;
import com.euromoby.socialize.core.service.MailService;
import com.euromoby.socialize.core.service.PasswordResetRequestService;
import com.euromoby.socialize.core.service.UserService;
import com.euromoby.socialize.core.utils.PasswordUtils;
import com.euromoby.socialize.web.dto.PasswordResetRequestDto;
import com.euromoby.socialize.web.dto.PasswordResetRequestFormDto;
import com.euromoby.socialize.web.dto.PasswordResetStatusDto;

@Controller
public class ResetPasswordController {

	private static final Logger log = LoggerFactory.getLogger(ResetPasswordController.class);

	@Autowired
	private Session session;

	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordResetRequestService passwordResetRequestService;

	@Autowired
	private MailService mailService;
	
	@Autowired
	private MailCreator mailBuilder;
	
	@Autowired
	private Config config;

	@RequestMapping(value = "/password-reset", method = RequestMethod.GET)
	public String passwordReset(ModelMap model, @RequestParam(value="website", required=false) Integer websiteId) {
		session.setWebsiteId(websiteId);

		String signupUrl = config.getAppUrl() + "/signup";
		if (websiteId != null) {
			signupUrl += "?website=" + websiteId;
		}
		
		String loginUrl = config.getAppUrl() + "/login";
		if (websiteId != null) {
			loginUrl += "?website=" + websiteId;
		}
		
		model.put("signupUrl", signupUrl);	
		model.put("loginUrl", loginUrl);		

		model.put("pageTitle", "Password Reset");		
		return "password-reset";
	}	

	@RequestMapping(value = "/password-reset/{uuid}", method = RequestMethod.GET)
	public String passwordResetForm(ModelMap model, @PathVariable("uuid") String uuid, @RequestParam(value="website", required=false) Integer websiteId) {
		session.setWebsiteId(websiteId);

		String passwordResetUrl = config.getAppUrl() + "/password-reset";
		if (websiteId != null) {
			passwordResetUrl += "?website=" + websiteId;
		}
		
		PasswordResetRequest request = passwordResetRequestService.findByUuid(uuid);
		if (request == null || request.getCreated() < System.currentTimeMillis() - PasswordResetRequest.TIME_TO_LIVE) {
			return "redirect:" + passwordResetUrl;
		}
		
		String loginUrl = config.getAppUrl() + "/login";
		if (websiteId != null) {
			loginUrl += "?website=" + websiteId;
		}

		model.put("pageTitle", "Password Reset");		
		model.put("uuid", uuid);
		model.put("loginUrl", loginUrl);		
		
		return "password-reset-form";
	}	
	
	@RequestMapping(value = "/password-reset", method = RequestMethod.POST, consumes = "application/json; charset=utf-8", produces = "application/json; charset=utf-8")
	public @ResponseBody
	PasswordResetStatusDto passwordResetPost(@Valid @RequestBody(required = true) PasswordResetRequestDto user) {
		PasswordResetStatusDto status = new PasswordResetStatusDto();

		UserAccount userAccount = userService.findByEmail(user.getEmail());
		if (userAccount == null) {
			status.setError(true);
			return status;
		}
		
		PasswordResetRequest request = passwordResetRequestService.findByUserAccountId(userAccount.getId());
		if (request != null && request.getCreated() >= System.currentTimeMillis() - PasswordResetRequest.TIME_TO_LIVE) {
			return status;
		}

		if (request != null && request.getCreated() < System.currentTimeMillis() - PasswordResetRequest.TIME_TO_LIVE) {
			passwordResetRequestService.delete(request);
		}
		
		request = new PasswordResetRequest();
		request.setUserAccountId(userAccount.getId());
		request.setUuid(UUID.randomUUID().toString());
		request.setCreated(System.currentTimeMillis());
		passwordResetRequestService.save(request);
		
		try {
			MailNew emailPasswordReset = mailBuilder.emailPasswordReset(request, userAccount, session);
			mailService.sendMail(emailPasswordReset);
		} catch (Exception e) {
			log.error("Unable to send confirmation email", e);
		}		
		
		return status;
	}
	
	@RequestMapping(value = "/password-reset/{uuid}", method = RequestMethod.POST, consumes = "application/json; charset=utf-8", produces = "application/json; charset=utf-8")
	public @ResponseBody
	PasswordResetStatusDto passwordResetFormPost(@PathVariable("uuid") String uuid, @Valid @RequestBody(required = true) PasswordResetRequestFormDto form, ModelMap model) {
		PasswordResetStatusDto status = new PasswordResetStatusDto();

		PasswordResetRequest passwordResetRequest = passwordResetRequestService.findByUuid(uuid);
		if (passwordResetRequest == null || passwordResetRequest.getCreated() < System.currentTimeMillis() - PasswordResetRequest.TIME_TO_LIVE) {
			status.setError(true);
			return status;
		}
		
		UserAccount userAccount = userService.findById(passwordResetRequest.getUserAccountId());
		if (userAccount == null) {
			status.setError(true);
			return status;
		}
		
		if (!form.getPassword().equals(form.getPassword2())) {
			status.setError(true);
			return status;
		}		
		
		userAccount.setPasswordHash(PasswordUtils.generatePasswordHash(form.getPassword()));
		userService.update(userAccount);
		passwordResetRequestService.delete(passwordResetRequest);
		
		status.setError(false);
		return status;
	}
	
}
