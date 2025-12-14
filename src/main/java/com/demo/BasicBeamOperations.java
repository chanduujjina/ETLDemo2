package com.demo;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.Filter;
import org.apache.beam.sdk.transforms.GroupByKey;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TypeDescriptor;
import org.apache.beam.sdk.values.TypeDescriptors;

import com.demo.dto.Employee;
import com.demo.service.EmployeeMockService;

public class BasicBeamOperations {

	public static void main(String[] args) {

		// step 1

		Pipeline pipeline = Pipeline.create();

		// step 2 load mock data from emplolyee mock service
		// master list

		PCollection<Employee> employeeMockCollection = pipeline.apply("Load Mock Data",
				Create.of(EmployeeMockService.getEmployeeInfo()));

		// slice

		PCollection<String> namesCollection = employeeMockCollection
				.apply(MapElements.into(TypeDescriptors.strings()).via(Employee::getName));

		namesCollection.apply(ParDo.of(new DoFn<String, Void>() {

			@ProcessElement
			public void processData(@Element String name) {
				//System.out.println(name);
			}
		}));

		// Transform
		// transfrom from List to Map
		PCollection<KV<Integer, Employee>> employeeMapCollection = employeeMockCollection.apply("Map",
				MapElements.into(TypeDescriptors.kvs(TypeDescriptors.integers(), TypeDescriptor.of(Employee.class)))
						.via(emp -> KV.of(emp.getEmpId(), emp)));

		// print the data
		employeeMapCollection.apply(ParDo.of(new DoFn<KV<Integer, Employee>, Void>() {

			@ProcessElement
			public void processData(@Element KV<Integer, Employee> map) {
				//System.out.println(map);
			}
		}));

		

		// Filter
		PCollection<Employee> filteredCollection = employeeMockCollection.apply("FilterData",Filter.by(emp -> emp.getGender().equals("Male")));
		
		// print the data
		filteredCollection.apply(ParDo.of(new DoFn<Employee, Void>() {

					@ProcessElement
					public void processData(@Element Employee emp) {
						//System.out.println(emp);
					}
				}));

		//group by
		
		PCollection<KV<String, Iterable<Employee>>> groupByDept = employeeMockCollection.apply("KV", MapElements.into(TypeDescriptors.kvs(TypeDescriptors.strings(), TypeDescriptor.of(Employee.class))).via(emp -> KV.of(emp.getDeptName(),emp)))
		.apply("Group by key", GroupByKey.create());
		
		// print the data
		groupByDept.apply(ParDo.of(new DoFn<KV<String, Iterable<Employee>>, Void>() {

							@ProcessElement
							public void processData(@Element KV<String, Iterable<Employee>> empMap) {
								System.out.println(empMap);
							}
						}));
		

		pipeline.run().waitUntilFinish();
	}

}
