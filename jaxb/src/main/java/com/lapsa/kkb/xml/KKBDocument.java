package com.lapsa.kkb.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "document")
public class KKBDocument implements Serializable {
    private static final long serialVersionUID = 6600231531045791922L;

    @XmlElementRef
    private KKBMerchant merchant;

    @XmlElementRef
    private KKBMerchantSign merchantSign;

    @XmlElementRef
    private KKBBank bank;

    @XmlElementRef
    private KKBBankSign bankSign;

    // GENERATED

    public KKBMerchant getMerchant() {
	return merchant;
    }

    public void setMerchant(KKBMerchant merchant) {
	this.merchant = merchant;
    }

    public KKBMerchantSign getMerchantSign() {
	return merchantSign;
    }

    public void setMerchantSign(KKBMerchantSign merchantSign) {
	this.merchantSign = merchantSign;
    }

    public KKBBank getBank() {
	return bank;
    }

    public void setBank(KKBBank bank) {
	this.bank = bank;
    }

    public KKBBankSign getBankSign() {
	return bankSign;
    }

    public void setBankSign(KKBBankSign bankSign) {
	this.bankSign = bankSign;
    }
}
