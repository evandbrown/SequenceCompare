package com.evandbrown.sequence.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.Lob;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.evandbrown.sequence.web.UniProtRequest;

/**
 * A ProteinSequenceComparison object contains data about the comparison
 * of two protein sequences, where a sequence is identified by its
 * UniProt ID.
 */
@XmlRootElement(name = "protein-comparison")
@Entity
public class ProteinSequenceComparison {
	
	@Id
	private String id;
	private String uniProtId1;
	private String uniProtId2;
	private String alignment;
	private String levenshteinDistance;
	
	/**
	 * Empty constructor
	 */
	public ProteinSequenceComparison() { }
	
	/**
	 * Create a comparison using the UniProt information from a 
	 * UniProtRequest
	 * @param uniProtRequest
	 */
	public ProteinSequenceComparison(UniProtRequest uniProtRequest) {
		uniProtId1 = uniProtRequest.id1;
		uniProtId2 = uniProtRequest.id2;
		id = String.format("%s-%s", uniProtId1, uniProtId2);
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public void setOrganism1(String organism1) {
		this.uniProtId1 = organism1;
	}

	public String getOrganism1() {
		return uniProtId1;
	}

	public void setOrganism2(String organism2) {
		this.uniProtId2 = organism2;
	}

	public String getOrganism2() {
		return uniProtId2;
	}

	public void setAlignment(String alignment) {
		this.alignment = alignment;
	}

	@Lob
	public String getAlignment() {
		return alignment;
	}

	public void setLevenshteinDistance(String levenshteinDistance) {
		this.levenshteinDistance = levenshteinDistance;
	}

	public String getLevenshteinDistance() {
		return levenshteinDistance;
	}
	
	@Transient
	@XmlTransient
	public Integer getLevenshteinDistanceInt() {
		return Integer.parseInt(levenshteinDistance);
	}
	
	public void setLevenshteinDistanceInt(Integer distance) {
		this.levenshteinDistance = distance.toString();
	}

}


