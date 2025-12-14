package com.demo.dto;

import java.io.Serializable;

public class Order implements Serializable{
	
	

	public int getOrderId() {
		return orderId;
	}

	public String getOrderType() {
		return orderType;
	}

	public Order(int orderId, String orderType) {
		super();
		this.orderId = orderId;
		this.orderType = orderType;
	}

	private int orderId;
	
	private String orderType;
	
	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderType=" + orderType + "]";
	}

}
