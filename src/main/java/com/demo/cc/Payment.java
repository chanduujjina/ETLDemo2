package com.demo.cc;

import java.io.Serializable;

public class Payment implements Serializable{
	
	
	@Override
	public String toString() {
		return "Payment [orderId=" + orderId + ", status=" + status + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Integer getOrderId() {
		return orderId;
	}

	public String getStatus() {
		return status;
	}

	private Integer orderId;
    private String status;

    public Payment(Integer orderId, String status) {
        this.orderId = orderId;
        this.status = status;
    }

}
