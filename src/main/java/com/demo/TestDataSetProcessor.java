package com.demo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionTuple;
import org.apache.beam.sdk.values.TupleTag;

public class TestDataSetProcessor {
	
	private static final TupleTag<Integer> EVEN_TAG = new TupleTag<>() {};
	
	private static final TupleTag<Integer> ODD_TAG = new TupleTag<>() {};
	
	public static void main(String[] args) {
		
		
		//create a PipeLine
		Pipeline pipleLine = Pipeline.create();
		
		//create a mock dataset
		
		List<Integer> mockDatSet = Stream.iterate(1, i->i+1).limit(300).collect(Collectors.toList());
		
		
		PCollection<Integer> mockDataSetCollection = pipleLine.apply("Load mock data",Create.of(mockDatSet));
		
		
		PCollectionTuple result = mockDataSetCollection.apply("Split Data",ParDo.of(new EvenOrOddTransform()).withOutputTags(EVEN_TAG, org.apache.beam.sdk.values.TupleTagList.of(ODD_TAG)));
		
		
		
		PCollection<Integer> evenPCollection = result.get(EVEN_TAG);
		
		PCollection<Integer> oddPCollection = result.get(ODD_TAG);
		
		
		evenPCollection.apply(ParDo.of(new DoFn<Integer, Void>() {
			@ProcessElement
			public void processData(ProcessContext ctx) {
				System.out.println(ctx.element());
			}
			
		}));
		
		pipleLine.run().waitUntilFinish();
	}
	
	static class EvenOrOddTransform extends DoFn<Integer, Integer> {

		@ProcessElement
		public void processData(ProcessContext ctx) {
			
			Integer number = ctx.element();
			
			//System.out.println(number);
			
			if (number % 2 == 0) {
				ctx.output(EVEN_TAG, number);
			}
			else {
				ctx.output(ODD_TAG, number);
			}

		}
	}

}
