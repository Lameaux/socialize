package com.euromoby.socialize.core.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.euromoby.socialize.core.Config;
import com.euromoby.socialize.core.model.MailNew;
import com.euromoby.socialize.core.model.UserAccount;

@Component
public class MailBuilder {

	@Autowired
	private Config config;
	
	private MailNew createEmptyEmail(UserAccount userAccount) {
		MailNew mailNew = new MailNew();
		String sender = config.getMailSenderName() + " <" + config.getMailSenderAddress() + ">";
		mailNew.setSender(sender);
		mailNew.setRecipient(userAccount.getEmail());
		mailNew.setCreated(System.currentTimeMillis());
		return mailNew;
	}
	
	public MailNew emailConfirmation(UserAccount userAccount) {
		MailNew mailNew = createEmptyEmail(userAccount);

		mailNew.setSubject("Confirm your email address");

		String content = "Hello " + userAccount.getLogin();
		mailNew.setContent(content);
		
		return mailNew;
	}
	
}
