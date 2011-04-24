package com.evandbrown.sequence.util;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.biojava3.core.sequence.ProteinSequence;

public class UniProtProteinSequence {
	private ProteinSequence proteinSequence;
	private String name;
	private String organism;

	public UniProtProteinSequence(ProteinSequence proteinsEquence) {
		this.proteinSequence = proteinsEquence;
		setOtherProperties();
	}

	public ProteinSequence getProteinSequence() {
		return proteinSequence;
	}

	public String getName() {
		return name;
	}

	public String getOrganism() {
		return organism;
	}

	/**
	 * Extract organism and protein names from UniProt response
	 */
	private void setOtherProperties() {
		Pattern p = Pattern
				.compile("(.*\\ )([A-Za-z0-9\\ ]*)(OS=)([A-Za-z\\ ]+)(GN=).*");
		Matcher m = p.matcher(proteinSequence.getDescription());

		if (m.matches()) {
			name = m.group(1);
			organism = m.group(4);
		}
	}

}
