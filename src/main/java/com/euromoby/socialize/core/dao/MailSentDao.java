package com.euromoby.socialize.core.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.euromoby.socialize.core.model.MailSent;

@Repository
public class MailSentDao {
	@Autowired
	private SessionFactory sessionFactory;
	 
	public void save(MailSent mailSent) {
		sessionFactory.getCurrentSession().save(mailSent);
	}
	
}
