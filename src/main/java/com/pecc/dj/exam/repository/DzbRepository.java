package com.pecc.dj.exam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pecc.dj.exam.entity.DzbEntity;

public interface DzbRepository extends JpaRepository<DzbEntity, Integer> {
	
	DzbEntity getOneByDzb(String dzb);
	
	DzbEntity getOneByName(String name);

}
