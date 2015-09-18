package com.euromoby.socialize.core.job;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.euromoby.socialize.core.mail.MailProvider;
import com.euromoby.socialize.core.model.MailNew;
import com.euromoby.socialize.core.service.MailService;

@Component
public class SendMailJob {

	private static final Logger log = LoggerFactory.getLogger(SendMailJob.class);
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private MailProvider mailProvider;
	
	@Scheduled(fixedDelay=5000)
	public void sendMail() {
		List<MailNew> newMails = mailService.getNewMails();
		for (MailNew mailNew : newMails) {
			try {
				mailProvider.send(mailNew);
			} catch (Exception e) {
				log.error("Unable to send mail", e);
			}
			mailService.moveToSent(newMails);
		}
		
		
	}
	
	
}
