package com.pecc.dj.exam.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.pecc.dj.exam.entity.DzbEntity;
import com.pecc.dj.exam.request.GenerateExamPaperRequest;
import com.pecc.dj.exam.request.HandQuestionsRequest;
import com.pecc.dj.exam.response.GetScoreResponse;


public interface ExamService {
	
	List<Map<String, String>> getQuestions(String dzb, boolean hasAnswerFlag);			//根据党支部标识获取题目内容
	
	boolean selectDzbForNextExam(String dzb);					//选择下次考试的党支部标识
	
	DzbEntity getNextExamDzb();									//显示下次考试的党支部信息
	
//	List<ExamPaperInfo> getAllExamPaper();						//获取数据库中所有考卷概览
	
//	int generateExam(GenerateExamPaperRequest request);			//根据选题人选的题目生成一套试题
	
//	boolean updateCandidateToExam(String candidateIdList, String examPaperId);	//为一套试题添加考试成员
	
//	List<Map<String, String>> generateExamByExamId(String id);	//根据考卷ID获取题目内容
	
	boolean handQuestions(HandQuestionsRequest handRequest);	//上传答案
	
	GetScoreResponse getLastScore(String userId);				//获取某用户上一次考试成绩
	
	List<GetScoreResponse> getAllScore(String userId);			//获取某用户所有考试成绩
	
	List<Map<String, String>> getScoreByDzb(String dzb);		//根据党支部标识获取所有考生成绩
	
//	List<GetScoreResponse> getScoreByExamId(String id);			//根据考卷ID该考卷所有考生成绩
	
	List<DzbEntity> getAllDzb();								//获取党支部信息

	boolean uploadQuestionExcel(InputStream is, String dzb, String name);	//上传题目Excel
}
