package com.euromoby.socialize.core.mail;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.euromoby.socialize.core.Config;
import com.euromoby.socialize.core.model.MailNew;
import com.euromoby.socialize.core.model.PasswordResetRequest;
import com.euromoby.socialize.core.model.UserAccount;
import com.euromoby.socialize.web.Session;

@Component
public class MailCreator {

	@Autowired
	private Config config;
	
	private MailNew createEmptyEmail(UserAccount userAccount) {
		MailNew mailNew = new MailNew();
		mailNew.setRecipient(userAccount.getEmail());
		mailNew.setCreated(System.currentTimeMillis());
		return mailNew;
	}
	
	private String loadTemplate(String templateName) throws IOException {
		ClassPathResource cpr = new ClassPathResource("mail-template/" + templateName + ".html");
		InputStream is = cpr.getInputStream();
		String template = IOUtils.toString(is, "UTF-8");
		IOUtils.closeQuietly(is);
		return template;
	}
	
	private String createTextFromTemplate(String templateName, Map<String, String> map) throws IOException {
		String template = loadTemplate(templateName);
		String[] searchList = new String[map.size()];
		String[] replacementList = new String[map.size()];
		int i=0;
		for (Map.Entry<String, String> entry : map.entrySet()) {
			searchList[i] = "%"+entry.getKey()+"%";
			replacementList[i] = entry.getValue();
			i++;
		}
		return StringUtils.replaceEach(template, searchList, replacementList);
	
	}
	
	public MailNew emailConfirmation(UserAccount userAccount, Session session) throws Exception {
		MailNew mailNew = createEmptyEmail(userAccount);
		mailNew.setSubject("Confirm your email address");

		Map<String, String> map = new HashMap<>();
		map.put("NAME", userAccount.getDisplayName());
		map.put("APP_TITLE", config.getAppTitle());
		
		String confirmUrl = config.getAppUrl();
		if (session.getWebsite() != null) {
			confirmUrl = config.getWebsiteUrl().replace("{domain}", session.getWebsite());
		}
		confirmUrl += "/verify-email/" + userAccount.getUuid();
		map.put("CONFIRM_URL", confirmUrl);
		
		String content = createTextFromTemplate("confirm-email", map);
		mailNew.setContent(content);
		
		return mailNew;
	}

	public MailNew emailPasswordReset(PasswordResetRequest passwordResetRequest, UserAccount userAccount, Session session) throws Exception {
		MailNew mailNew = createEmptyEmail(userAccount);
		mailNew.setSubject("Password Reset");

		Map<String, String> map = new HashMap<>();
		map.put("NAME", userAccount.getDisplayName());
		map.put("APP_TITLE", config.getAppTitle());

		String resetUrl = config.getAppUrl();
		if (session.getWebsite() != null) {
			resetUrl = config.getWebsiteUrl().replace("{domain}", session.getWebsite());
		}
		resetUrl += "/password-reset/" + passwordResetRequest.getUuid();
		map.put("RESET_URL", resetUrl);
		
		String content = createTextFromTemplate("password-reset", map);
		mailNew.setContent(content);
		
		return mailNew;
	}	
	
}
