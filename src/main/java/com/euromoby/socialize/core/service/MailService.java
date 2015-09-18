package com.euromoby.socialize.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.euromoby.socialize.core.dao.MailNewDao;
import com.euromoby.socialize.core.dao.MailSentDao;
import com.euromoby.socialize.core.model.MailNew;
import com.euromoby.socialize.core.model.MailSent;
import com.euromoby.socialize.core.transform.MailTransformer;

@Component
public class MailService {

	@Autowired
	private MailNewDao mailNewDao;
	
	@Autowired
	private MailSentDao mailSentDao;
	
	@Autowired
	private MailTransformer mailTransformer;
	
	@Transactional
	public void sendMail(MailNew mailNew) {
		mailNewDao.save(mailNew);
	}

	@Transactional(readOnly=true)	
	public List<MailNew> getNewMails() {
		return mailNewDao.findAll();
	}
	
	@Transactional
	public void moveToSent(List<MailNew> mails) {
		for (MailNew mail : mails) {
			MailSent mailSent = mailTransformer.newSentMail(mail);
			mailSentDao.save(mailSent);
			mailNewDao.delete(mail);
		}
		
	}
	
	
	
}
