package com.pecc.dj.exam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pecc.dj.exam.entity.AllQuestionInfo;

public interface AllQuestionInfoRepository extends JpaRepository<AllQuestionInfo, Integer> {

	List<AllQuestionInfo> findAllByDzb(String dzb);
	
}
