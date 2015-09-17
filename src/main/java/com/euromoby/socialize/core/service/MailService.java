package com.euromoby.socialize.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.euromoby.socialize.core.dao.MailNewDao;
import com.euromoby.socialize.core.dao.MailSentDao;
import com.euromoby.socialize.core.model.MailNew;

@Component
public class MailService {

	@Autowired
	private MailNewDao mailNewDao;
	
	@Autowired
	private MailSentDao mailSentDao;
	
	@Transactional
	public void sendMail(MailNew mailNew) {
		mailNewDao.save(mailNew);
	}
	
}
