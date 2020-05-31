package com.pecc.dj.exam.request;

import java.util.Map;

public class HandQuestionsRequest {
	private String userId;
	private String dzb;
	private String questionSequence;
	private String examPaperId;
	private Map<String, String> candidateAnswer;
	public HandQuestionsRequest() {
		super();
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getQuestionSequence() {
		return questionSequence;
	}
	public void setQuestionSequence(String questionSequence) {
		this.questionSequence = questionSequence;
	}
	public Map<String, String> getCandidateAnswer() {
		return candidateAnswer;
	}
	public void setCandidateAnswer(Map<String, String> candidateAnswer) {
		this.candidateAnswer = candidateAnswer;
	}
	public String getDzb() {
		return dzb;
	}
	public void setDzb(String dzb) {
		this.dzb = dzb;
	}
	public String getExamPaperId() {
		return examPaperId;
	}
	public void setExamPaperId(String examPaperId) {
		this.examPaperId = examPaperId;
	}
	
}
