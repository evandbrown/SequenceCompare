package com.evandbrown.sequence.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * A ProteinSeq object represents a specific protein sequence in the 
 * UniProt database. ProteinSeq entities are persisted to SimpleDB while
 * their sequences are stored in S3 via the Lob attribute.
 * 
 * @author Evan D. Brown
 *
 */
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
