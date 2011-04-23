package com.evandbrown.sequence.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.Lob;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
public class ProteinSeq {
	@Id
	private String id;
	private String sequence;
	
	public ProteinSeq() { }

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	@Lob
	public String getSequence() {
		return sequence;
	}
}
