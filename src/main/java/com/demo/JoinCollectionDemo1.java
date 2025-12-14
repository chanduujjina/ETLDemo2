package com.demo;

import java.util.ArrayList;
import java.util.List;
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

import com.demo.dto.Order;
import com.demo.dto.Payment;

public class JoinCollectionDemo1 {
	
	private static final TupleTag<Order> ORDER_MAP = new TupleTag<>() {};
	
	private static final TupleTag<Payment> PAYMENT_MAP = new TupleTag<>() {};
	
	public static void main(String[] args) {
		
		Pipeline p = Pipeline.create();
		
		//dateset 1
		
		List<Order> orders = new ArrayList<>();
		orders.add(new Order(1, "Mobile"));
		orders.add(new Order(2, "Ear Phone"));
		orders.add(new Order(3, "Laptop"));
		
		PCollection<Order> orderCollection= p.apply("Load Order Data", Create.of(orders));
		
		//dataset 2
		List<Payment> paymentList = new ArrayList<>();
		paymentList.add(new Payment(1, "Paid"));
		paymentList.add(new Payment(2, "Failed"));
		
		PCollection<Payment> paymentCollection= p.apply("Load Order Data", Create.of(paymentList));
		
		PCollection<KV<Integer, Order>> orderMap= orderCollection.apply(MapElements.into(TypeDescriptors.kvs(TypeDescriptors.integers(), TypeDescriptor.of(Order.class))).via(order -> KV.of(order.getOrderId(), order)));
		
		PCollection<KV<Integer, Payment>> paymentMap = paymentCollection.apply(MapElements.into(TypeDescriptors.kvs(TypeDescriptors.integers(), TypeDescriptor.of(Payment.class))).via(payment -> KV.of(payment.getOrderId(), payment)));
		
		
		PCollection<KV<Integer, CoGbkResult>> resultCollection = KeyedPCollectionTuple.of(ORDER_MAP, orderMap).and(PAYMENT_MAP, paymentMap).apply(CoGroupByKey.create());
		
		resultCollection.apply("Inner Join", ParDo.of(new DoFn<KV<Integer, CoGbkResult>, Void>() {
			
			@ProcessElement
			public void processData(@Element KV<Integer, CoGbkResult> map) {
			
				Integer key = map.getKey();
				
				System.out.println("key : "+key);
				
				
				Iterable<Order> orderItr = map.getValue().getAll(ORDER_MAP);
				
				List<Order> orderList = StreamSupport.stream(orderItr.spliterator(),false).toList();
				
				List<Payment> paymentList = StreamSupport.stream(map.getValue().getAll(PAYMENT_MAP).spliterator(),false).toList();
				
				System.out.println("order list:"+orderList);
				
				System.out.println("payment list:"+paymentList);
				
			}
			
		}));
		
		p.run().waitUntilFinish();
	}

}
