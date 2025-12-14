package com.demo.dto;

import java.io.Serializable;

public class Payment implements Serializable{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Payment(int orderId, String paymentStatus) {
		super();
		this.orderId = orderId;
		this.paymentStatus = paymentStatus;
	}

	public int getOrderId() {
		return orderId;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	private int orderId;
	
	private String paymentStatus;
	
	@Override
	public String toString() {
		return "Payment [orderId=" + orderId + ", paymentStatus=" + paymentStatus + "]";
	}

}
