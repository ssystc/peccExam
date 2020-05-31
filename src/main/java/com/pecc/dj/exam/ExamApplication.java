package com.pecc.dj.exam;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pecc.dj.exam.repository.CandidateAnswerRepository;

@SpringBootApplication
public class ExamApplication {

	@Autowired
	public CandidateAnswerRepository cnadidateAnswerRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(ExamApplication.class, args);
	}

}
