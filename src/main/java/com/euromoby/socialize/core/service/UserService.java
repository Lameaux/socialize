package com.euromoby.socialize.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.euromoby.socialize.core.dao.UserAccountDao;
import com.euromoby.socialize.core.model.UserAccount;

@Service
public class UserService {

	@Autowired
	private UserAccountDao userAccountDao;
	
	@Transactional(readOnly=true)
	public boolean emailExists(String email) {
		return userAccountDao.findByEmail(email) != null;
	}
	
	@Transactional
	public void save(UserAccount userAccount) {
		userAccountDao.save(userAccount);
	}

	@Transactional
	public void update(UserAccount userAccount) {
		userAccountDao.update(userAccount);
	}	
	
	@Transactional(readOnly=true)	
	public UserAccount findByUuid(String uuid) {
		return userAccountDao.findByUuid(uuid);
	}
	
}
