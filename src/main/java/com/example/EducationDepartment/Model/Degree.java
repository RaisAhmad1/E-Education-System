package com.example.EducationDepartment.Model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 
 * @author RaisAhmad
 * @date 29/10/2021
 * @Discription Degree Class
 * 
 */

@Entity
@Table(name = "t_degree")
public class Degree {

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

	public String getStudentCnic() {
		return studentCnic;
	}

	public void setStudentCnic(String studentCnic) {
		this.studentCnic = studentCnic;
	}

	@Column(nullable = true)
	private boolean status;
	
	@Column(nullable = true)
	private String studentCnic;

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
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
