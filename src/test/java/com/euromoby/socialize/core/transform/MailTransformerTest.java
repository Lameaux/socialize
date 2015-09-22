package com.euromoby.socialize.core.transform;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.euromoby.socialize.core.model.MailNew;
import com.euromoby.socialize.core.model.MailSent;

public class MailTransformerTest {

	public static final Integer ID = 123;	
	public static final String RECIPIENT = "recipient";
	public static final String SUBJECT = "subject";
	public static final String CONTENT = "content";	
	
	@Test
	public void newSentMail() {
		MailTransformer mailTransformer = new MailTransformer();

		MailNew mail = new MailNew();
		mail.setId(ID);
		mail.setRecipient(RECIPIENT);
		mail.setSubject(SUBJECT);
		mail.setContent(CONTENT);

		MailSent mailSent = mailTransformer.newSentMail(mail);

		assertEquals(ID, mailSent.getId());
		assertEquals(RECIPIENT, mailSent.getRecipient());
		assertEquals(SUBJECT, mailSent.getSubject());
		assertEquals(CONTENT, mailSent.getContent());
		assertTrue(mailSent.getSent() > 0);
	}

}
