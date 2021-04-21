package com.pecc.dj.exam.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//试题类
@Entity
public class DzbEntity {
	
	//一套试题的主键ID
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	//一套试题的唯一英文名称
	@Column
	private String dzb;
	
	//一套试题的唯一中文名
	@Column
	private String name;

	//上传时间
	@Column
	private Date timeFlagDate;
	
	public DzbEntity() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDzb() {
		return dzb;
	}

	public void setDzb(String dzb) {
		this.dzb = dzb;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getTimeFlagDate() {
		return timeFlagDate;
	}

	public void setTimeFlagDate(Date timeFlagDate) {
		this.timeFlagDate = timeFlagDate;
	}
}
