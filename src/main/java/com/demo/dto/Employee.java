package com.demo.dto;

import java.io.Serializable;
import java.util.List;

public class Employee implements Serializable {
	
	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", name=" + name + ", gender=" + gender + ", deptName=" + deptName
				+ ", skillSet=" + skillSet + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public Employee(Integer empId, String name, String gender, String deptName, List<String> skillSet) {
		super();
		this.empId = empId;
		this.name = name;
		this.gender = gender;
		this.deptName = deptName;
		this.skillSet = skillSet;
	}

	private Integer empId;
	
	private String name;
	
	private String gender;
	
	private String deptName;
	
	private List<String> skillSet;
	
	
	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public List<String> getSkillSet() {
		return skillSet;
	}

	public void setSkillSet(List<String> skillSet) {
		this.skillSet = skillSet;
	}

}
