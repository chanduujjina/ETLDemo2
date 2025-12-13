package com.demo;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

public class TestDofnLifeCycleMethods {
	
	public static void main(String[] args) {
		
		
		Pipeline pipeline = Pipeline.create();
		
		//load the data from mock source
		
		
		PCollection<String> mockCollection = pipeline.apply("load mock data", Create.of("nandu"));
		
		mockCollection.apply("transform data", ParDo.of(new LifeCycleFn()));
		
		pipeline.run().waitUntilFinish();
	}
	
	static class LifeCycleFn extends DoFn<String, String>{
		
		@Setup
		public void init() {//db connection,IOStreams
			System.out.println("inside init method");
		}
		
		@ProcessElement
		public void processData(ProcessContext ctx) {
			System.out.println("inside procces Element");
		
			String input = ctx.element();
			
			ctx.output(input.toUpperCase());
		}
		
		@StartBundle
		public void bundleStart() {
			System.out.println("inside bundle start");
		}
		
		@FinishBundle
		public void bundleEnd() {
			System.out.println("inside bundle end");
		}
		
		
		@Teardown
		public void close() {//resource closing
			System.out.println("inside close");
		}
	}

}
