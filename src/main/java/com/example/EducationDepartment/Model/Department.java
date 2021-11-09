package com.example.EducationDepartment.Model;

import javax.persistence.JoinColumn;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

/**
 * 
 * @author RaisAhmad
 * @date 29/10/2021
 * @Discription Department Class
 */

@Entity
@Table(name = "t_department")
public class Department {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = true)
	private Date date;

	@Column(nullable = true)
	private String name;

	@Column(nullable = true)
	private Date updatedDate;

	@ManyToMany(targetEntity = Institution.class, cascade = { CascadeType.MERGE })

	@JoinTable(name = "t_departmentInstitute", joinColumns = {
			@JoinColumn(name = "departmentId") }, inverseJoinColumns = { @JoinColumn(name = "institutionId") })

	Set<Institution> institution = new HashSet<Institution>();

	@ManyToMany(targetEntity = Curriculum.class, cascade = { CascadeType.MERGE })

	@JoinTable(name = "t_departmentCurriculum", joinColumns = {
			@JoinColumn(name = "departmentId") }, inverseJoinColumns = { @JoinColumn(name = "curriculumId") })

	Set<Curriculum> curriculum = new HashSet<Curriculum>();

	public Set<Curriculum> getCurriculum() {
		return curriculum;
	}

	public void setCurriculum(Set<Curriculum> curriculum) {
		this.curriculum = curriculum;
	}

	public Set<Institution> getInstitution() {
		return institution;
	}

	public void setInstitution(Set<Institution> institution) {
		this.institution = institution;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
