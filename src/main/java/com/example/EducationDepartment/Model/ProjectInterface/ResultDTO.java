package com.example.EducationDepartment.Model.ProjectInterface;


public class ResultDTO {

	private String totalMarks;
	private String obtainedMarks;
	private String classAndSec;
	
	public String getClassAndSec() {
		return classAndSec;
	}

	public void setClassAndSec(String classAndSec) {
		this.classAndSec = classAndSec;
	}

	public String getTotalMarks() {
		return totalMarks;
	}

	public void setTotalMarks(String totalMarks) {
		this.totalMarks = totalMarks;
	}

	public String getObtainedMarks() {
		return obtainedMarks;
	}

	public void setObtainedMarks(String obtainedMarks) {
		this.obtainedMarks = obtainedMarks;
	}

	

}
