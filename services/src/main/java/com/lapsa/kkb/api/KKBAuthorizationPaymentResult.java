package com.lapsa.kkb.api;

import java.io.Serializable;

import com.lapsa.country.Country;

public class KKBAuthorizationPaymentResult implements Serializable {
    private static final long serialVersionUID = -5554861649966310216L;

    private String reference;
    private String approvalCode;
    private String responseCode;
    private boolean secured;
    private String cardNumberMasked;
    private Country cardIssuerConutry;
    private String cardHash;

    public String getReference() {
	return reference;
    }

    public void setReference(String reference) {
	this.reference = reference;
    }

    public String getApprovalCode() {
	return approvalCode;
    }

    public void setApprovalCode(String approvalCode) {
	this.approvalCode = approvalCode;
    }

    public String getResponseCode() {
	return responseCode;
    }

    public void setResponseCode(String responseCode) {
	this.responseCode = responseCode;
    }

    public boolean isSecured() {
	return secured;
    }

    public void setSecured(boolean secured) {
	this.secured = secured;
    }

    public String getCardNumberMasked() {
	return cardNumberMasked;
    }

    public void setCardNumberMasked(String cardNumberMasked) {
	this.cardNumberMasked = cardNumberMasked;
    }

    public Country getCardIssuerConutry() {
	return cardIssuerConutry;
    }

    public void setCardIssuerConutry(Country cardIssuerConutry) {
	this.cardIssuerConutry = cardIssuerConutry;
    }

    public String getCardHash() {
	return cardHash;
    }

    public void setCardHash(String cardHash) {
	this.cardHash = cardHash;
    }
}
