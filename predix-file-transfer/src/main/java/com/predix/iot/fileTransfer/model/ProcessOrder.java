package com.predix.iot.fileTransfer.model;

public enum ProcessOrder {
	BY_NAME("n"),
	BY_SIZE("s"),
	BY_LAST_MODIFIED_DATE("ld");
	
	private String orderType;
	
	ProcessOrder(String orderType) {
        this.orderType = orderType;
    }
	
    public String orderCode() {
        return orderType;
    }
}
