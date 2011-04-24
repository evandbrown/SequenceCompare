package com.evandbrown.sequence.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Lob;
import javax.xml.bind.annotation.XmlRootElement;

import com.evandbrown.sequence.web.UniProtRequest;

/**
 * A ProteinSequenceComparison object contains data about the comparison
 * of two protein sequences, where a sequence is identified by its
 * UniProt ID.
 */
@XmlRootElement(name = "proteinComparisons")
@Entity
public class ProteinSeqComparison {
	
	@Id
	private String id;
	private ProteinSeq sequenceOne;
	private ProteinSeq sequenceTwo;
	private String alignment;
	private Integer levenshteinDistance;
	
	/**
	 * A comparison of two protein sequences
	 * @param uniProtRequest
	 */
	public ProteinSeqComparison() { }
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public void setAlignment(String alignment) {
		this.alignment = alignment;
	}

	@Lob
	public String getAlignment() {
		return alignment;
	}

	public void setLevenshteinDistance(Integer levenshteinDistance) {
		this.levenshteinDistance = levenshteinDistance;
	}

	public Integer getLevenshteinDistance() {
		return levenshteinDistance;
	}

	public void setSequenceOne(ProteinSeq sequenceOne) {
		this.sequenceOne = sequenceOne;
	}

	@ManyToOne(cascade=CascadeType.ALL)
	public ProteinSeq getSequenceOne() {
		return sequenceOne;
	}

	public void setSequenceTwo(ProteinSeq sequenceTwo) {
		this.sequenceTwo = sequenceTwo;
	}

	@ManyToOne(cascade=CascadeType.ALL)
	public ProteinSeq getSequenceTwo() {
		return sequenceTwo;
	}

}


