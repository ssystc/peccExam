package com.pecc.dj.exam.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class AllQuestionInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int questionId;
	
	@Column
	private String type;
	
	@Column
	private float score;
	
	@Lob
	@Column(columnDefinition="text")
	private String question;
	
	@Column
	private String options;
	
	@Column
	private String rightAnswer;
	
	@Column
	private String dzb;
	
	@Lob
	@Column(columnDefinition="text")
	private String questionExplain;
	
	@Column
	private String blank1;
	
	@Column
	private String blank2;
	
	@Column
	private String blank3;
	
	public AllQuestionInfo() {
		super();
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getRightAnswer() {
		return rightAnswer;
	}

	public void setRightAnswer(String rightAnswer) {
		this.rightAnswer = rightAnswer;
	}

	public String getDzb() {
		return dzb;
	}

	public void setDzb(String dzb) {
		this.dzb = dzb;
	}

	public String getQuestionExplain() {
		return questionExplain;
	}

	public void setQuestionExplain(String questionExplain) {
		this.questionExplain = questionExplain;
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
