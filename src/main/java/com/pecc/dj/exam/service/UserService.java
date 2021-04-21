package com.pecc.dj.exam.service;




public interface UserService {
	boolean userAuth(String userId, String password);
	
	boolean updatePass(String userId, String oldPass, String newPass);
	
//	boolean userAuthWithExamId(String userId, String password, String examPaperId);


}
