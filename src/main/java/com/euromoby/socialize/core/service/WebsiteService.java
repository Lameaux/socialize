package com.euromoby.socialize.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.euromoby.socialize.core.dao.WebsiteDao;
import com.euromoby.socialize.core.model.Website;

@Service
public class WebsiteService {

	@Autowired
	private WebsiteDao websiteDao;

	@Transactional(readOnly = true)
	public Website findByDomain(String domain) {
		return websiteDao.findByDomain(domain);
	}

	@Transactional(readOnly = true)
	public Website findByUuid(String uuid) {
		return websiteDao.findByUuid(uuid);
	}

	@Transactional
	public void save(Website website) {
		websiteDao.save(website);
	}

	@Transactional
	public void update(Website website) {
		websiteDao.update(website);
	}

}
