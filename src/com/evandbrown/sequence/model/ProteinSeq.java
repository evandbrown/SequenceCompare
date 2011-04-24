package com.evandbrown.sequence.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class ProteinSeq {
	@Id
	private String id;
	private String sequence;
	private String name;
	private String organism;
	
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

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setOrganism(String organism) {
		this.organism = organism;
	}

	public String getOrganism() {
		return organism;
	}
}
