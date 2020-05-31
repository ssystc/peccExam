package com.pecc.dj.exam.response;

import java.util.List;
import java.util.Map;

public class GetScoreResponse {

	String userId;
	String userName;
	String examTime;
	String finalScore;
	String fullScore;
	String examId;
	String examName;
	List<Map<String, String>> errQuestionMsg;
	public GetScoreResponse() {
		super();
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getExamTime() {
		return examTime;
	}
	public void setExamTime(String examTime) {
		this.examTime = examTime;
	}
	public String getFinalScore() {
		return finalScore;
	}
	public void setFinalScore(String finalScore) {
		this.finalScore = finalScore;
	}
	public String getFullScore() {
		return fullScore;
	}
	public void setFullScore(String fullScore) {
		this.fullScore = fullScore;
	}
	public List<Map<String, String>> getErrQuestionMsg() {
		return errQuestionMsg;
	}
	public void setErrQuestionMsg(List<Map<String, String>> errQuestionMsg) {
		this.errQuestionMsg = errQuestionMsg;
	}
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
}
