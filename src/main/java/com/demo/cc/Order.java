package com.demo.cc;

import java.io.Serializable;

public class Order implements Serializable{
	
	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderName=" + orderName + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer orderId;
	private String orderName;
	
	public Order (Integer orderId,String orderName) {
		this.orderId = orderId;
		this.orderName =orderName; 
	}
	
	public Integer getOrderId() {
		return orderId;
	}

	public String getOrderName() {
		return orderName;
	}


}
