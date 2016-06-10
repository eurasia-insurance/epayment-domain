package com.lapsa.kkb.core.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "department")
@XmlAccessorType(XmlAccessType.FIELD)
public class KKBDepartment implements Serializable {
    private static final long serialVersionUID = -1021696352173990619L;

    // merchant_id - ID продавца в платежной системе
    @XmlAttribute(name = "merchant_id", required = true)
    private String merchantId;

    // amount - сумма оплаты данному продавцу
    @XmlAttribute(name = "amount", required = true)
    private int amount;

    // abonent_id - дополнительные поля для продавца, можно не указывать
    @XmlAttribute(name = "abonent_id", required = false)
    private String abonentId;

    // terminal - дополнительные поля для продавца, можно не указывать
    @XmlAttribute(name = "terminal", required = true)
    private String terminal;

    // phone - дополнительные поля для продавца, можно не указывать
    @XmlAttribute(name = "phone", required = false)
    private String phone;

    // RL - дополнительное поле, для транспортных компаний- Номер брони, можно
    // не указывать. Транслуруется по всем отчетам и выпискам
    @XmlAttribute(name = "RL", required = false)
    private String airticketBookingNumber;

    // GENERATED

    public String getMerchantId() {
	return merchantId;
    }

    public void setMerchantId(String merchantId) {
	this.merchantId = merchantId;
    }

    public int getAmount() {
	return amount;
    }

    public void setAmount(int amount) {
	this.amount = amount;
    }

    public String getAbonentId() {
	return abonentId;
    }

    public void setAbonentId(String abonentId) {
	this.abonentId = abonentId;
    }

    public String getTerminal() {
	return terminal;
    }

    public void setTerminal(String terminal) {
	this.terminal = terminal;
    }

    public String getPhone() {
	return phone;
    }

    public void setPhone(String phone) {
	this.phone = phone;
    }

    public String getAirticketBookingNumber() {
	return airticketBookingNumber;
    }

    public void setAirticketBookingNumber(String airticketBookingNumber) {
	this.airticketBookingNumber = airticketBookingNumber;
    }
}
