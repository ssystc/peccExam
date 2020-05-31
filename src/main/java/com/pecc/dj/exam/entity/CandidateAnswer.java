package com.pecc.dj.exam.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class CandidateAnswer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String candidateId;
	
	@Column
	private String candidateName;
	
	@Column
	private String candidateDept;
	
	@Column
	private String dzb;

	@Lob
	@Column(columnDefinition="text")
	private String candidateAnswerMap;
	
	@Column
	private Date examTime;
	
	@Column
	private float finalScore;
	
	@Column
	private float fullScore;
	
	@Column
	private String questionSequence;
	
	@Column
	private String examPaperId;
	
	@Column
	private String blank1;
	
	@Column
	private String blank2;
	
	@Column
	private String blank3;
	
	public CandidateAnswer() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getCandidateAnswerMap() {
		return candidateAnswerMap;
	}

	public void setCandidateAnswerMap(String candidateAnswerMap) {
		this.candidateAnswerMap = candidateAnswerMap;
	}

	public Date getExamTime() {
		return examTime;
	}

	public void setExamTime(Date examTime) {
		this.examTime = examTime;
	}


	public String getCandidateDept() {
		return candidateDept;
	}

	public void setCandidateDept(String candidateDept) {
		this.candidateDept = candidateDept;
	}

	public String getDzb() {
		return dzb;
	}

	public void setDzb(String dzb) {
		this.dzb = dzb;
	}

	public float getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(float finalScore) {
		this.finalScore = finalScore;
	}

	public String getQuestionSequence() {
		return questionSequence;
	}

	public void setQuestionSequence(String questionSequence) {
		this.questionSequence = questionSequence;
	}
	

	public float getFullScore() {
		return fullScore;
	}

	public void setFullScore(float fullScore) {
		this.fullScore = fullScore;
	}
	
	public String getExamPaperId() {
		return examPaperId;
	}

	public void setExamPaperId(String examPaperId) {
		this.examPaperId = examPaperId;
	}
	
	public String getBlank1() {
		return blank1;
	}

	public void setBlank1(String blank1) {
		this.blank1 = blank1;
	}

	public String getBlank2() {
		return blank2;
	}

	public void setBlank2(String blank2) {
		this.blank2 = blank2;
	}

	public String getBlank3() {
		return blank3;
	}

	public void setBlank3(String blank3) {
		this.blank3 = blank3;
	}
	
}
