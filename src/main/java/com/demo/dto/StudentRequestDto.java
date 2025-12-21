package com.demo.dto;

import java.io.Serializable;
import java.util.List;

public class StudentRequestDto implements Serializable{
	
	
	
	
	public StudentRequestDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public List<Student> getPayLoad() {
		return payLoad;
	}
	public void setPayLoad(List<Student> payLoad) {
		this.payLoad = payLoad;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Student> payLoad;
	

}
