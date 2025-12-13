package com.demo;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;

public class TextIoProcessor {
	
	public static void main(String[] args) {
		
		Pipeline pipeline = Pipeline.create();
		
		pipeline.apply("Read Input File", TextIO.read().from("Sample.txt"))
		.apply("Upper Case",ParDo.of( new DoFn<String, String>() {
			
			@ProcessElement
			public void processData(@Element String line ,OutputReceiver<String> receiver) {
				receiver.output(line.toUpperCase());
			}
			
		})).apply("Write To File", TextIO.write().to("output").withSuffix(".txt"))
		;
		
		pipeline.run().waitUntilFinish();
		
	}

}
