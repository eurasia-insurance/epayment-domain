package com.lapsa.kkb.core;

import java.io.Serializable;

public class KKBPaymentCustomer implements Serializable {
    private static final long serialVersionUID = -7637967627189263288L;

    private String customerName;
    private String customerMail;
    private String customerPhone;

    public String getCustomerName() {
	return customerName;
    }

    public void setCustomerName(String customerName) {
	this.customerName = customerName;
    }

    public String getCustomerMail() {
	return customerMail;
    }

    public void setCustomerMail(String customerMail) {
	this.customerMail = customerMail;
    }

    public String getCustomerPhone() {
	return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
	this.customerPhone = customerPhone;
    }

}
