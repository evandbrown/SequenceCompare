package com.evandbrown.levenshtein.model;

import javax.xml.bind.annotation.XmlRootElement;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.evandbrown.levenshtein.util.MD5Digest;

@Entity
@XmlRootElement(name = "lev-comp")
public class LevenshteinComparison {
	
	@Id
	private String id;
	private String string1;
	private String string2;
	private Integer distance;
	
	public LevenshteinComparison() { }
	
	public LevenshteinComparison(String string1, String string2) {
		this.setString1(string1);
		this.setString2(string2);
	}
	
	public String getId() {
		if(this.id == null) this.id = MD5Digest.get(string1 + string2);
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public void setString1(String string1) {
		this.string1 = string1;
	}

	public String getString1() {
		return string1;
	}

	public void setString2(String string2) {
		this.string2 = string2;
	}

	public String getString2() {
		return string2;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public Integer getDistance() {
		return new Integer(100);
	}
}


