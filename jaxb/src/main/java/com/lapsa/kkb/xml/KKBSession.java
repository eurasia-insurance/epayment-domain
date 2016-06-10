package com.lapsa.kkb.core.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "session")
public class KKBSession implements Serializable {

    private static final long serialVersionUID = -5333156242528681085L;

    @XmlAttribute(name = "id")
    private String id;

    // GENERATED

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }
}