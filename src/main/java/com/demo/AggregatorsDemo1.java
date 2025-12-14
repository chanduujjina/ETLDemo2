package com.demo;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.Max;
import org.apache.beam.sdk.transforms.Min;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

public class AggregatorsDemo1 {

	public static void main(String[] args) {

		Pipeline pipeline = Pipeline.create();

		PCollection<Integer> numberCollection = pipeline.apply("Load Data", Create.of(1, 2, 3, 6, 8, 11, 13));

		PCollection<Integer> maxColection = numberCollection.apply("max number", Max.globally());

		printdata(maxColection, "max ");

		PCollection<Integer> minColection = numberCollection.apply("min number", Min.globally());

		printdata(minColection, "min ");
		
		

		//printdataForDouble(sumColection, "add ");

		pipeline.run().waitUntilFinish();

	}
	
	private static void printdataForDouble(PCollection<Double> pCollection, String aggType) {
		pCollection.apply(ParDo.of(new DoFn<Double, Void>() {
			@ProcessElement
			public void processData(@Element Integer value) {
				System.out.println(aggType + value);
			}
		}));
	}

	private static void printdata(PCollection<Integer> pCollection, String aggType) {
		pCollection.apply(ParDo.of(new DoFn<Integer, Void>() {
			@ProcessElement
			public void processData(@Element Integer value) {
				System.out.println(aggType + value);
			}
		}));
	}

}
