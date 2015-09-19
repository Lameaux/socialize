package com.euromoby.socialize.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.euromoby.socialize.core.dao.UserAccountDao;
import com.euromoby.socialize.core.model.UserAccount;
import com.euromoby.socialize.core.utils.PasswordUtils;

@Service
public class UserService {

	@Autowired
	private UserAccountDao userAccountDao;
	
	@Transactional(readOnly=true)
	public UserAccount findById(Integer id) {
		return userAccountDao.findById(id);
	}	
	
	@Transactional(readOnly=true)
	public UserAccount findByEmail(String email) {
		return userAccountDao.findByEmail(email);
	}
	
	@Transactional(readOnly=true)
	public UserAccount findByEmailAndPassword(String email, String password) {
		UserAccount userAccount = userAccountDao.findByEmail(email);
		if (userAccount == null) {
			return null;
		}
		
		if (PasswordUtils.passwordMatches(password, userAccount.getPasswordHash())) {
			return userAccount;
		}
		
		return null;
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
