package com.pecc.dj.exam.service;

import com.pecc.dj.exam.entity.AllUserInfo;

public interface UserService {
	AllUserInfo userAuth(String userId, String password);
	
	boolean updatePass(String userId, String oldPass, String newPass);
	
//	boolean userAuthWithExamId(String userId, String password, String examPaperId);


}
