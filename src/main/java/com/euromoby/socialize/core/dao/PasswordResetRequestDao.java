package com.euromoby.socialize.core.dao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.euromoby.socialize.core.model.PasswordResetRequest;

@Repository
public class PasswordResetRequestDao {
	@Autowired
	private SessionFactory sessionFactory;
	 
	public void save(PasswordResetRequest passwordResetRequest) {
		sessionFactory.getCurrentSession().save(passwordResetRequest);
	}
	
	public void delete(PasswordResetRequest passwordResetRequest) {
		sessionFactory.getCurrentSession().delete(passwordResetRequest);
	}
	
	public PasswordResetRequest findByUserAccountId(Integer userAccountId) {
		Query query = sessionFactory.getCurrentSession().createQuery("from PasswordResetRequest where userAccountId = :userAccountId");
		query.setInteger("userAccountId", userAccountId);
		return (PasswordResetRequest) query.uniqueResult();
	}
	
	public PasswordResetRequest findByUuid(String uuid) {
		Query query = sessionFactory.getCurrentSession().createQuery("from PasswordResetRequest where uuid = :uuid");
		query.setString("uuid", uuid);
		return (PasswordResetRequest) query.uniqueResult();
	}	
	
}
