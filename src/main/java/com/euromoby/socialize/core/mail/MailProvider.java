package com.euromoby.socialize.core.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.euromoby.socialize.core.Config;
import com.euromoby.socialize.core.model.MailNew;

@Component
public class MailProvider {
	
	private static final Logger log = LoggerFactory.getLogger(MailProvider.class);

	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private Config config;	
	
	public void send(MailNew mailNew) throws MessagingException {
		log.info("Sending mail to {}", mailNew.getRecipient());
		
		MimeMessage mime = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mime, true, "UTF-8");
		helper.setFrom(config.getMailSenderName() + " <" + config.getMailSenderAddress() + ">");
		helper.setTo("sergeisizov@gmail.com"); // TODO
		helper.setSubject(mailNew.getSubject());
		helper.setText(mailNew.getContent(), true);
		
		mailSender.send(mime);		
	}

}
