package com.demo.cc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.join.CoGbkResult;
import org.apache.beam.sdk.transforms.join.CoGroupByKey;
import org.apache.beam.sdk.transforms.join.KeyedPCollectionTuple;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TupleTag;
import org.apache.beam.sdk.values.TypeDescriptor;
import org.apache.beam.sdk.values.TypeDescriptors;

public class JoinPatternsDemo {

	static TupleTag<Order> orderTag = new TupleTag<Order>() {
	};
	static TupleTag<Payment> paymentTag = new TupleTag<Payment>() {
	};

	public static void main(String[] args) {

		Pipeline p = Pipeline.create();

		PCollection<Order> orderCollection = p.apply("Load order data",
				Create.of(new Order(1, "Order-A"), new Order(2, "Order-B"), new Order(3, "Order-C")));

		PCollection<Payment> paymentCollection = p.apply("Load payment data",
				Create.of(new Payment(1, "PAID"), new Payment(2, "FAILED")));

		// group orderCollection by order id

		PCollection<KV<Integer, Order>> orders = orderCollection
				.apply(MapElements.into(TypeDescriptors.kvs(TypeDescriptors.integers(), TypeDescriptor.of(Order.class)))
						.via(order -> KV.of(order.getOrderId(), order)));

		PCollection<KV<Integer, Payment>> payments = paymentCollection.apply(
				MapElements.into(TypeDescriptors.kvs(TypeDescriptors.integers(), TypeDescriptor.of(Payment.class)))
						.via(payment -> KV.of(payment.getOrderId(), payment)));

		// coGroupByKey

		PCollection<KV<Integer, CoGbkResult>> joined = KeyedPCollectionTuple.of(orderTag, orders)
				.and(paymentTag, payments).apply(CoGroupByKey.create());

		PCollection<String> finalString = joined.apply("InnerJoin",
				ParDo.of(new DoFn<KV<Integer, CoGbkResult>, String>() {

					@ProcessElement
					public void processElement(ProcessContext c) {

						CoGbkResult result = c.element().getValue();

						List<Order> orders = StreamSupport.stream(result.getAll(orderTag).spliterator(), false)
								.collect(Collectors.toList());
						System.out.println("orders"+orders);

						List<Payment> payments = StreamSupport.stream(result.getAll(paymentTag).spliterator(), false)
								.collect(Collectors.toList());
						
						System.out.println("payments"+payments);

						if (orders.isEmpty() || payments.isEmpty()) {
							return; 
						}

						for (Order o : orders) {
							for (Payment p : payments) {
								c.output("INNER: " + o.getOrderName() + " -> " + p.getStatus());
							}
						}
					}
				}));
		
		finalString.apply("print final output", ParDo.of(new DoFn<String, Void>() {
			
			@ProcessElement
			public void processData(@Element String input) {
				System.out.println(input);
			}
		}));
		
		p.run().waitUntilFinish();

	}

}
