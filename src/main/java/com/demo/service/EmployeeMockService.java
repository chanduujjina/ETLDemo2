package com.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.demo.dto.Employee;

public class EmployeeMockService {
	
	private EmployeeMockService() {
		
	}
	
	private static List<Employee> employeeList = new ArrayList();
	
	static {
		employeeList.add(new Employee(1, "Raju", "Male", "IT", Arrays.asList("C","Java")));
		employeeList.add(new Employee(2, "Karthik", "Male", "IT", Arrays.asList("TypeScrpt","JSX")));
		employeeList.add(new Employee(3, "Pavan", "Male", "IT", Arrays.asList("Python","GCP")));
		employeeList.add(new Employee(4, "Gowtham", "Male", "IT", Arrays.asList(".Net","Java")));
		
		employeeList.add(new Employee(5, "Rani", "FeMale", "Admin", Arrays.asList("Excel","PowerBI")));
	}
	
	public static List<Employee> getEmployeeInfo(){
		return employeeList;
	}

}
