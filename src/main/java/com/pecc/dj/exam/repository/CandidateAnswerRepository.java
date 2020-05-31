package com.pecc.dj.exam.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pecc.dj.exam.entity.CandidateAnswer;

public interface CandidateAnswerRepository extends JpaRepository<CandidateAnswer, Integer> {
	List<CandidateAnswer> findAllByCandidateId(String candidateId, Sort sort);
	List<CandidateAnswer> findAllByDzb(String dzb);
	List<CandidateAnswer> findAllByExamPaperId(String examPaperId);
}
