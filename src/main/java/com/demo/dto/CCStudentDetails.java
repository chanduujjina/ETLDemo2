package com.demo.dto;

import java.io.Serializable;

public class CCStudentDetails  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public CCStudentDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CCStudentDetails(int id, String name, String gender, String phoneNumber, int totalExperince,
			String skillSet) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.phoneNumber = phoneNumber;
		this.totalExperince = totalExperince;
		this.skillSet = skillSet;
	}

	private int id;
	
	private String name;
	
	private String gender;
	
	private String phoneNumber;
	
	private int totalExperince;
	
	private String skillSet;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getTotalExperince() {
		return totalExperince;
	}

	public void setTotalExperince(int totalExperince) {
		this.totalExperince = totalExperince;
	}

	public String getSkillSet() {
		return skillSet;
	}

	public void setSkillSet(String skillSet) {
		this.skillSet = skillSet;
	}

}
