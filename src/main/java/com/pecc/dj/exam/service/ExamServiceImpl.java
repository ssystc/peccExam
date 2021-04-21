package com.pecc.dj.exam.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pecc.dj.exam.constant.ErrorDefEnum;
import com.pecc.dj.exam.entity.AllQuestionInfo;
import com.pecc.dj.exam.entity.AllUserInfo;
//import com.pecc.dj.exam.entity.AllUserInfo;
import com.pecc.dj.exam.entity.CandidateAnswer;
import com.pecc.dj.exam.entity.DzbEntity;
//import com.pecc.dj.exam.entity.ExamPaperInfo;
import com.pecc.dj.exam.exception.ExamException;
import com.pecc.dj.exam.repository.AllQuestionInfoRepository;
import com.pecc.dj.exam.repository.AllUserInfoRespository;
//import com.pecc.dj.exam.repository.AllUserInfoRespository;
import com.pecc.dj.exam.repository.CandidateAnswerRepository;
import com.pecc.dj.exam.repository.DzbRepository;
//import com.pecc.dj.exam.repository.ExamPaperInfoRepository;
import com.pecc.dj.exam.request.GenerateExamPaperRequest;
import com.pecc.dj.exam.request.HandQuestionsRequest;
import com.pecc.dj.exam.response.GetScoreResponse;
import com.pecc.dj.exam.util.ExcelReadUtil;


@Service
public class ExamServiceImpl implements ExamService {

	public static Logger logger = LoggerFactory.getLogger(ExamServiceImpl.class);
	
	@Autowired
	private AllQuestionInfoRepository allQuestionInfoRepository;
	
	@Autowired
	private CandidateAnswerRepository candidateAnswerRepository;
	
	@Autowired
	private AllUserInfoRespository userInfoRepository;
//	
//	@Autowired
//	private ExamPaperInfoRepository examInfoRepository;
	
	@Autowired
	private DzbRepository dzbRepository;
	
	@Override
	public List<Map<String, String>> getQuestions(String dzb, boolean hasAnswerFlag) {
		List<AllQuestionInfo> questions = allQuestionInfoRepository.findAllByDzb(dzb);
		if (questions.size()==0) {
			throw new ExamException(ErrorDefEnum.DZB_ERROR);
		}
		List<Map<String, String>> result = new ArrayList<Map<String,String>>();
		
		for(AllQuestionInfo question : questions) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("ID", question.getQuestionId()+"");
			map.put("TYPE", question.getType());
			map.put("QUESTION", question.getQuestion());
			map.put("OPTIONS", question.getOptions());
			map.put("SCORE", question.getScore()+"");
			if(hasAnswerFlag) {
				map.put("RIGHTANSWER", question.getRightAnswer());
			}
			result.add(map);
		}
		return result;
	}

	@Override
	public boolean handQuestions(HandQuestionsRequest handRequest) {
		String userId = handRequest.getUserId();
		String questionSequence = handRequest.getQuestionSequence();
		String dzb = handRequest.getDzb();
//		if(dzbRepository.getOneByDzb(dzb) == null) {
//			throw new ExamException(ErrorDefEnum.DZB_ERROR);
//		}
		if(candidateAnswerRepository.findByCandidateIdAndDzb(userId, dzb) != null) {
			throw new ExamException(ErrorDefEnum.USER_HAS_EXAMED);
		}
		
		
		try {		
			String examPaperId = handRequest.getExamPaperId();
			Map<String, String> candidateAnswer = handRequest.getCandidateAnswer();
			
			Gson gson = new GsonBuilder().create();
			String candidateAnswerString = gson.toJson(candidateAnswer);
			
			AllUserInfo userInfo = userInfoRepository.getOne(userId);
			
			
			CandidateAnswer answer = new CandidateAnswer();
			answer.setCandidateName(userInfo.getUserName());
			answer.setCandidateDept(userInfo.getDepartment());
			answer.setId(0);
			answer.setCandidateAnswerMap(candidateAnswerString);
			answer.setCandidateId(userId);
			answer.setExamTime(new Date());
			answer.setQuestionSequence(questionSequence);
			answer.setExamPaperId(examPaperId);
			
			answer.setDzb(dzb);
			float fullScore = 0;
			float finalScore = 0;
			for (String key : candidateAnswer.keySet()) {
			      String questionId = key;
			      String userAnswer = candidateAnswer.get(questionId).replace(" ", "").toUpperCase();
			      AllQuestionInfo questionInfo = allQuestionInfoRepository.getOne(Integer.parseInt(questionId));
			      String rightAnswer = questionInfo.getRightAnswer().replace(" ", "").toUpperCase();
			      if(userAnswer.equals(rightAnswer)) {
			    	  finalScore+=(questionInfo.getScore());
			      }
			      fullScore += (questionInfo.getScore());
			}
			answer.setFinalScore(finalScore);
			answer.setFullScore(fullScore);
			
			candidateAnswerRepository.saveAndFlush(answer);
		} catch (Exception e) {
			logger.error("答案上传失败：" + e.getMessage());
			throw new ExamException(ErrorDefEnum.HAND_ANSWER_ERROR);
		}
		
		return true;
	}

	@Override
	public GetScoreResponse getLastScore(String userId) {
		Sort sort = Sort.by("examTime");
		List<CandidateAnswer> answers = candidateAnswerRepository.findAllByCandidateId(userId, sort);
		if (answers.size()==0) {
			logger.error("该考生信息不存在：" + userId);
			throw new ExamException(ErrorDefEnum.CANDIDATE_ID_ERROR);
		}
		CandidateAnswer candidateAnswer = answers.get(answers.size()-1);
		
		return constructScoreResponse(candidateAnswer);
	}

	@Override
	public List<GetScoreResponse> getAllScore(String userId) {
		Sort sort = Sort.by("examTime");
		List<CandidateAnswer> answers = candidateAnswerRepository.findAllByCandidateId(userId, sort);
		List<GetScoreResponse> result = new ArrayList<GetScoreResponse>();
		for (CandidateAnswer answer : answers) {
			result.add(constructScoreResponse(answer));
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private GetScoreResponse constructScoreResponse(CandidateAnswer candidateAnswer) {
		try {
			GetScoreResponse result = new GetScoreResponse();
			
			Date date = candidateAnswer.getExamTime();
			DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			String examTime = dFormat.format(date);
			result.setExamTime(examTime);
			result.setFinalScore(candidateAnswer.getFinalScore()+"");
			
			String candidateAnswerMapString = candidateAnswer.getCandidateAnswerMap();
			
			Gson gson = new GsonBuilder().create();
			Map<String, String> candidateAnswerMap = new HashMap<String, String>();
			candidateAnswerMap = gson.fromJson(candidateAnswerMapString, candidateAnswerMap.getClass());
			float fullScore = 0;
			List<Map<String, String>> errQuestionMsg = new ArrayList<Map<String,String>>();
			String questionQuence = candidateAnswer.getQuestionSequence();
			String[] questionQuenceArray = questionQuence.split(",");
			for (String key : candidateAnswerMap.keySet()) {
			      String questionId = key;
			      String userAnswer = candidateAnswerMap.get(questionId).replace(" ", "").toUpperCase();
			      AllQuestionInfo questionInfo = allQuestionInfoRepository.getOne(Integer.parseInt(questionId));
			      String rightAnswer = questionInfo.getRightAnswer().replace(" ", "").toUpperCase();
			      String questionValue = questionInfo.getQuestion();
			      String options = questionInfo.getOptions();
			      fullScore +=(questionInfo.getScore());
			      if(userAnswer.equals(rightAnswer)) {
			    	  continue;
			      }else {
			    	  Map<String, String> oneErrQuestionMsg = new HashMap<String, String>();
			    	  oneErrQuestionMsg.put("questionId", questionId);
			    	  oneErrQuestionMsg.put("question", questionValue);
			    	  oneErrQuestionMsg.put("options", options);
			    	  oneErrQuestionMsg.put("questionOrder", getIndexInArray(questionQuenceArray, questionId)+"");
			    	  oneErrQuestionMsg.put("userAnswer", userAnswer);
//			    	  oneErrQuestionMsg.put("rightAnswer", rightAnswer);
			    	  oneErrQuestionMsg.put("explain", questionInfo.getQuestionExplain());
			    	  errQuestionMsg.add(oneErrQuestionMsg);
			      }
			}
			result.setFullScore(fullScore+"");
			result.setErrQuestionMsg(errQuestionMsg);
			result.setUserId(candidateAnswer.getCandidateId());
			String examPaperId = candidateAnswer.getExamPaperId();
			result.setExamId(examPaperId);
			return result;
		} catch (Exception e) {
			logger.error("查询成绩异常：" + e.getMessage());
			throw new ExamException(ErrorDefEnum.QUERY_SCORE_ERROR);
		}
		
	}
	
	private int getIndexInArray(String[] array, String value) {
		for (int i = 0; i < array.length; i++) {
            if (array[i].equals(value)) {
                return (i+1);
            }
        }
        return -1;
	}

	@Override
	public List<Map<String, String>> getScoreByDzb(String dzb) {
		List<CandidateAnswer> answers = candidateAnswerRepository.findAllByDzb(dzb);
		List<Map<String, String>> result = new ArrayList<Map<String,String>>();
		for(CandidateAnswer answer : answers) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("candidateId", answer.getCandidateId());
			map.put("candidateName", answer.getCandidateName());
			map.put("candidateDept", answer.getCandidateDept());
			map.put("finalScore", answer.getFinalScore()+"");
			map.put("fullScore", answer.getFullScore()+"");
			DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			map.put("handQuestionTime", dFormat.format(answer.getExamTime()));
			result.add(map);
		}
		return result;
	}

//	@Override
//	public int generateExam(GenerateExamPaperRequest request) {
//		String examNameString = request.getExamName();
//		if (examInfoRepository.findAllByExamName(examNameString).size()!=0) {
//			throw new ExamException(ErrorDefEnum.EXAM_NAME_EXISTED);	
//		}
//		try {
//			ExamPaperInfo paperInfo = new ExamPaperInfo();
//			paperInfo.setId(0);
//			paperInfo.setExamName(request.getExamName());
//			paperInfo.setDzb(request.getDzb());
//			paperInfo.setQuestionList(request.getQuestionIdList().toString());
//			paperInfo.setCandidateList(request.getCandidateIdList().toString());
//			paperInfo.setCreateTime(new Date());
//			paperInfo.setAuthor(request.getAuthor());
//			examInfoRepository.save(paperInfo);
//			return paperInfo.getId();
//		} catch (Exception e) {
//			logger.error("生成考卷失败：" + e.getMessage());
//			throw new ExamException(ErrorDefEnum.GENERATE_EXAM_PAPER_ERROR);
//		}
//	}
//
//	@Override
//	public boolean updateCandidateToExam(String candidateIdList, String examPaperId) {
//		try {
//			ExamPaperInfo paperInfo = examInfoRepository.getOne(Integer.parseInt(examPaperId));
//			paperInfo.setCandidateList(candidateIdList);
//		} catch (Exception e) {
//			logger.error("为试卷添加考试成员失败:" + e.getMessage());
//			throw new ExamException(ErrorDefEnum.ADD_CANDIDATE_ERROR);
//		}
//		return true;
//	}
//
//	@Override
//	public List<Map<String, String>> generateExamByExamId(String id) {
//		try {
//			String questionIdListString = examInfoRepository.getOne(Integer.parseInt(id)).getQuestionList();
//			String[] questionIds = questionIdListString.split(",");
//			List<Map<String, String>> result = new ArrayList<Map<String,String>>();
//			for (String questionId : questionIds) {
//				Map<String, String>	map = new HashMap<String, String>();
//				AllQuestionInfo questionInfo = allQuestionInfoRepository.getOne(Integer.parseInt(questionId));
//				map.put("ID", questionInfo.getQuestionId()+"");
//				map.put("TYPE", questionInfo.getType());
//				map.put("QUESTION", questionInfo.getQuestion());
//				map.put("OPTIONS", questionInfo.getOptions());
//				map.put("SCORE", questionInfo.getScore()+"");
//				result.add(map);
//			}
//			return result;
//		} catch (Exception e) {
//			logger.error("根据考卷ID获取题目失败:" + e.getMessage());
//			throw new ExamException(ErrorDefEnum.GET_QUESTIONS_BY_EXAM_PAPER_ID_ERROR);
//		}
//		
//	}
//
//	@Override
//	public List<GetScoreResponse> getScoreByExamId(String examPaperId) {
//		try {
//			List<CandidateAnswer> answers = candidateAnswerRepository.findAllByExamPaperId(examPaperId);
//			List<GetScoreResponse> result = new ArrayList<GetScoreResponse>();
//			for (CandidateAnswer answer : answers) {
//				result.add(constructScoreResponse(answer));
//			}
//			return result;
//		} catch (Exception e) {
//			logger.error("根据考卷ID:" + examPaperId + ",获取成绩失败:" + e.getMessage());
//			throw new ExamException(ErrorDefEnum.QUERY_SCORE_ERROR);
//		}
//		
//	}
//
//	@Override
//	public List<ExamPaperInfo> getAllExamPaper() {
//		return examInfoRepository.findAll();
//	}

	@Override
	public List<DzbEntity> getAllDzb() {
		return dzbRepository.findAll();
	}


	@Override
	public boolean selectDzbForNextExam(String dzb) {
		try {
			DzbEntity dzbEntity = dzbRepository.getOneByDzb(dzb);
			dzbEntity.setTimeFlagDate(new Date());
			dzbRepository.save(dzbEntity);
			return true;
		} catch (Exception e) {
			throw new ExamException(ErrorDefEnum.DZB_ERROR);
		}
		
	}

	@Override
	public DzbEntity getNextExamDzb() {
		Sort sort = Sort.by("timeFlagDate");
		List<DzbEntity> results = dzbRepository.findAll(sort);
		if (results.size()==0) {
			logger.error("党支部信息表为空！");
			throw new ExamException(ErrorDefEnum.DZB_ERROR);
		}
		DzbEntity result = results.get(results.size()-1);
		return result;
	}

	@Override
	@Transactional
	public boolean uploadQuestionExcel(InputStream is, String dzb, String name) {
		if(dzbRepository.getOneByDzb(dzb) != null) {
			throw new ExamException(ErrorDefEnum.DZB_EXISTED);
		}
		DzbEntity entity = new DzbEntity();
		entity.setDzb(dzb);
		entity.setId(0);
		entity.setName(name);
		entity.setTimeFlagDate(new Date());
		dzbRepository.save(entity);
		
		try {
			List<List<String>> excelList = ExcelReadUtil.readExcel(is);
			List<String> firstRow = excelList.get(0);
			checkFormatByFirstRow(firstRow);
			excelList.remove(0);
			for (List<String> rowMsg : excelList) {
				String type = rowMsg.get(1).replace(" ", "").replace(" ", "");
				
				if(type.length()<1) {
					break;
				}
				
				AllQuestionInfo allQuestionInfo = new AllQuestionInfo();
				allQuestionInfo.setQuestionId(0);
				allQuestionInfo.setDzb(dzb);
				allQuestionInfo.setType(rowMsg.get(1).replace(" ", "").replace(" ", ""));
				allQuestionInfo.setQuestion(rowMsg.get(2).replace(" ", "").replace(" ", ""));
				allQuestionInfo.setOptions(rowMsg.get(3).replace(" ", "").replace(" ", ""));
				allQuestionInfo.setRightAnswer(rowMsg.get(4).replace(" ", "").replace(" ", ""));
				allQuestionInfo.setScore(Float.parseFloat(rowMsg.get(5).replace(" ", "").replace(" ", "")));
				allQuestionInfoRepository.save(allQuestionInfo);
			}
			return true;
			
		} catch (ExamException e) {
			throw e;
		} catch (IOException e) {
			throw new ExamException(ErrorDefEnum.UPLOAD_FILE_PARSE_ERROR);
		}
	}
	
	private boolean checkFormatByFirstRow(List<String> firstRow) {
		if(firstRow.get(0).replace(" ", "").replace(" ", "").toUpperCase().equals("ID") && 
				firstRow.get(1).replace(" ", "").replace(" ", "").toUpperCase().equals("TYPE")  &&
				firstRow.get(2).replace(" ", "").replace(" ", "").toUpperCase().equals("QUESTION") &&
				firstRow.get(3).replace(" ", "").replace(" ", "").toUpperCase().equals("OPTIONS") &&
				firstRow.get(4).replace(" ", "").replace(" ", "").toUpperCase().equals("ANSWER")&&
				firstRow.get(5).replace(" ", "").replace(" ", "").toUpperCase().equals("SCORE") ) {
			return true;
		}else {
			throw new ExamException(ErrorDefEnum.COLUMN_HEAD_ERROR);
		}
		
	}

	

}
