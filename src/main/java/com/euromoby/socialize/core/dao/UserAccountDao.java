package com.euromoby.socialize.core.dao;


import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.euromoby.socialize.core.model.UserAccount;

@Repository
public class UserAccountDao {
	@Autowired
	private SessionFactory sessionFactory;
	 
	public void save(UserAccount userAccount) {
		userAccount.setCreated(System.currentTimeMillis());
		sessionFactory.getCurrentSession().save(userAccount);
	}

	public void update(UserAccount userAccount) {
		userAccount.setUpdated(System.currentTimeMillis());
		sessionFactory.getCurrentSession().update(userAccount);
	}	
	
	public UserAccount findByEmail(String email) {
		Query query = sessionFactory.getCurrentSession().createQuery("from UserAccount where email = :email");
		query.setString("email", email);
		return (UserAccount) query.uniqueResult();
	}
	
	public UserAccount findByUuid(String uuid) {
		Query query = sessionFactory.getCurrentSession().createQuery("from UserAccount where uuid = :uuid");
		query.setString("uuid", uuid);
		return (UserAccount) query.uniqueResult();		
	}
	
}
