package com.demo;

import java.lang.reflect.Method;

import org.apache.beam.sdk.transforms.DoFn.ProcessElement;

public class ReflectionTest {
	
	public static void main(String[] args) {
		
		TestDataSetProcessor dataSetProcessor = new TestDataSetProcessor();
		
		Class<? extends TestDataSetProcessor> cls = dataSetProcessor.getClass();
		
		Class<?>[] declaredClasses = cls.getDeclaredClasses();
		
		for (Class clsmeta : declaredClasses) {
			
			Method[] declaredMethods = clsmeta.getDeclaredMethods();
			
			for (Method method : declaredMethods) {
				System.out.println(method);
				
				if (method.isAnnotationPresent(ProcessElement.class)) {
					System.out.println(method);
				}
				
			}
		}
	}

}
