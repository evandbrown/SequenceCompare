package com.evandbrown.sequence.web;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * An object used to identify a comparison to be performed on two
 * sequences identified by their UniProt IDs. The @XmlRootElement
 * indicates support for JAXB serialization.
 * 
 * @author Evan D. Brown
 *
 */
@XmlRootElement(name = "uniprot-request")
public class UniProtRequest {
	public String id1;
	public String id2;
	
	public UniProtRequest() { }
	
	public UniProtRequest(String id1, String id2) {
		this.id1 = id1;
		this.id2 = id2;
	}
}
