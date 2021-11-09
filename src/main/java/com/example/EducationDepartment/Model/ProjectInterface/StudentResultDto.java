package com.example.EducationDepartment.Model.ProjectInterface;

import java.util.List;


public class StudentResultDto {

	private String firstName;
	private String lastNamString;
	private String cnic;
	private List<ResultDTO> result;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public List<ResultDTO> getResult() {
		return result;
	}

	public void setResult(List<ResultDTO> result) {
		this.result = result;
	}

	public String getLastNamString() {
		return lastNamString;
	}

	public void setLastNamString(String lastNamString) {
		this.lastNamString = lastNamString;
	}

	public String getCnic() {
		return cnic;
	}

	public void setCnic(String cnic) {
		this.cnic = cnic;
	}

}
