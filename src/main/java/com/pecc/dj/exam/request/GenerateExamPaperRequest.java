package com.pecc.dj.exam.request;

import java.util.List;

public class GenerateExamPaperRequest {
	
	private String examName;
	private String dzb;
	private String author;
	private List<String> questionIdList;
	private List<String> candidateIdList;
	public GenerateExamPaperRequest() {
		super();
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public String getDzb() {
		return dzb;
	}
	public void setDzb(String dzb) {
		this.dzb = dzb;
	}
	public List<String> getQuestionIdList() {
		return questionIdList;
	}
	public void setQuestionIdList(List<String> questionIdList) {
		this.questionIdList = questionIdList;
	}
	public List<String> getCandidateIdList() {
		return candidateIdList;
	}
	public void setCandidateIdList(List<String> candidateIdList) {
		this.candidateIdList = candidateIdList;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}

}
