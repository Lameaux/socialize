package com.euromoby.socialize.core.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.euromoby.socialize.core.model.MailNew;

@Repository
public class MailNewDao {
	@Autowired
	private SessionFactory sessionFactory;
	 
	public void save(MailNew mailNew) {
		sessionFactory.getCurrentSession().save(mailNew);
	}
	
}
