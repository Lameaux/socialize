package com.euromoby.socialize.core.dao;


import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.euromoby.socialize.core.model.Website;

@Repository
public class WebsiteDao {
	@Autowired
	private SessionFactory sessionFactory;
	 
	public void save(Website website) {
		website.setCreated(System.currentTimeMillis());
		sessionFactory.getCurrentSession().save(website);
	}

	public void update(Website website) {
		website.setUpdated(System.currentTimeMillis());
		sessionFactory.getCurrentSession().update(website);
	}	
	
	public Website findByDomain(String domain) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Website where domain = :domain");
		query.setString("domain", domain);
		return (Website) query.uniqueResult();
	}
	
	public Website findByUuid(String uuid) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Website where uuid = :uuid");
		query.setString("uuid", uuid);
		return (Website) query.uniqueResult();		
	}
	
}
