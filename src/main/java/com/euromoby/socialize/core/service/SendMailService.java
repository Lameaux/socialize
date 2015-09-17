package com.euromoby.socialize.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class SendMailService {

	@Autowired
	private MailSender mailSender;
	
	public void sendTestMail() {
	
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("sergeisizov@gmail.com");
		message.setTo("sergeisizov@gmail.com");
		message.setSubject("Socialize test");
		message.setText("Testing");

		mailSender.send(message);
		
	}
	
}
