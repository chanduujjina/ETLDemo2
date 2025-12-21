package com.demo.dto;

import java.io.Serializable;

public class Student implements Serializable{
	
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", gender=" + gender + ", barnch=" + barnch + ", eventTime="
				+ eventTime + "]";
	}

	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

	public String getBarnch() {
		return barnch;
	}

	public void setBarnch(String barnch) {
		this.barnch = barnch;
	}

	private int id;
	
	private String name;
	
	private String gender;
	
	private String barnch;
	
	private String eventTime;
	
	public String getEventTime() {
		return eventTime;
	}
	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}

}
