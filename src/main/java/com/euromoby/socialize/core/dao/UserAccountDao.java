package com.euromoby.socialize.core.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.euromoby.socialize.core.model.UserAccount;

@Repository
public class UserAccountDao {
	@Autowired
	private SessionFactory sessionFactory;
	 
	public void save(UserAccount userAccount) {
		sessionFactory.getCurrentSession().save(userAccount);
	}
	
}
