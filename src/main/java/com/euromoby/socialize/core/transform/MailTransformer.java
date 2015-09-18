package com.euromoby.socialize.core.transform;

import org.springframework.stereotype.Component;

import com.euromoby.socialize.core.model.MailNew;
import com.euromoby.socialize.core.model.MailSent;

@Component
public class MailTransformer {

	public MailSent newSentMail(MailNew mail) {
		MailSent sentMail = new MailSent();
		sentMail.setId(mail.getId());
		sentMail.setRecipient(mail.getRecipient());
		sentMail.setSubject(mail.getSubject());
		sentMail.setContent(mail.getContent());
		sentMail.setSent(System.currentTimeMillis());
		return sentMail;
	}
	
}
