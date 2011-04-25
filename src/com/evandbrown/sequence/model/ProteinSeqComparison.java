package com.evandbrown.sequence.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Lob;
import javax.xml.bind.annotation.XmlRootElement;

import com.evandbrown.sequence.web.UniProtRequest;

/**
 * A comparison of two protein sequences. The comparison contains the sequence
 * calculated when ProteinSeq 1 is aligned to ProteinSeq 2. Additionally, the
 * Levenshtein Distance of the two sequences is calculated.
 * 
 * Data is persisted to SimpleDB. The alignment contains a pointer to the actual
 * sequence which is persisted to S3 via the @Lob attribute.
 * 
 * The object is also eligible for JAXB serialization (XML or JSON) as indicated
 * by the @XmlRootElement attribute.
 * 
 * @author Evan D. Brown
 * 
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


