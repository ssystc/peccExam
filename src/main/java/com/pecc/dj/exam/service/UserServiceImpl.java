//package com.pecc.dj.exam.service;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.pecc.dj.exam.entity.AllUserInfo;
//import com.pecc.dj.exam.repository.AllUserInfoRespository;
//import com.pecc.dj.exam.repository.ExamPaperInfoRepository;
//
//
//@Service
//public class UserServiceImpl implements UserService {
//
//	public static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
//	
//	@Autowired
//	private AllUserInfoRespository allUserInfoRespository;
//	
//	@Autowired
//	private ExamPaperInfoRepository paperInfoRepository;
//	
//	@Override
//	public boolean userAuth(String userId, String password) {
//		AllUserInfo info = allUserInfoRespository.findByUserIdAndPassword(userId, password);
//		if(info != null) {
//			return true;
//		}
//		return false;
//	}
//	
//	@Override
//	public boolean userAuthWithExamId(String userId, String password, String examPaperId) {
//		AllUserInfo info = allUserInfoRespository.findByUserIdAndPassword(userId, password);
//		if(info != null) {
//			String candidateListString = getCandidateListByExamId(examPaperId);
//			String[] candidates = candidateListString.split(",");
//			for(String candidateId : candidates) {
//				if(userId.equals(candidateId)) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}
//
//	@Override
//	public boolean updatePass(String userId, String oldPass, String newPass) {
//
//		
//		if(userId == null || oldPass == null || newPass == null){
//            logger.error("username or password is empty.");
//            return false;
//        }
//		
//		AllUserInfo userInfo = allUserInfoRespository.findByUserIdAndPassword(userId, oldPass);
//        if(userInfo == null){
//            logger.error("userId=" + userId + "oldPass=" + oldPass + "密码错误");
//            return false;
//        }
//
//        userInfo.setPassword(newPass);
//        allUserInfoRespository.save(userInfo);
//        return true;
//		
//	}
//
//
//	private String getCandidateListByExamId(String examPaperId) {
//		return paperInfoRepository.getOne(Integer.parseInt(examPaperId)).getCandidateList();
//	}
//
//	
//
//}
