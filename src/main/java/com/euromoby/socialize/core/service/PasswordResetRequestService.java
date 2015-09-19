package com.euromoby.socialize.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.euromoby.socialize.core.dao.PasswordResetRequestDao;
import com.euromoby.socialize.core.model.PasswordResetRequest;

@Service
public class PasswordResetRequestService {

	@Autowired
	private PasswordResetRequestDao passwordResetRequestDao;

	@Transactional(readOnly = true)
	public PasswordResetRequest findByUserAccountId(Integer userAccountId) {
		return passwordResetRequestDao.findByUserAccountId(userAccountId);
	}

	@Transactional(readOnly = true)
	public PasswordResetRequest findByUuid(String uuid) {
		return passwordResetRequestDao.findByUuid(uuid);
	}

	@Transactional
	public void delete(PasswordResetRequest passwordResetRequest) {
		passwordResetRequestDao.delete(passwordResetRequest);
	}	

	@Transactional
	public void save(PasswordResetRequest passwordResetRequest) {
		passwordResetRequestDao.save(passwordResetRequest);
	}	
	
}
