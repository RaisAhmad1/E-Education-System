package com.example.EducationDepartment.Model.ProjectInterface;

import java.util.List;

public class ExamDto {
	private String name;
	private List<InstitutionDTO> institution;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<InstitutionDTO> getInstitution() {
		return institution;
	}

	public void setInstitution(List<InstitutionDTO> institution) {
		this.institution = institution;
	}
	
}
