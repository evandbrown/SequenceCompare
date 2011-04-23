package com.evandbrown.sequence.util;

import java.net.URL;

import org.biojava3.alignment.Alignments.PairwiseSequenceAlignerType;
import org.biojava3.alignment.SimpleSubstitutionMatrix;
import org.biojava3.alignment.SimpleGapPenalty;
import org.biojava3.alignment.Alignments;
import org.biojava3.alignment.template.SequencePair;
import org.biojava3.alignment.template.SubstitutionMatrix;
import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.compound.AminoAcidCompound;
import org.biojava3.core.sequence.io.FastaReaderHelper;

import com.evandbrown.sequence.model.ProteinSeqComparison;
import com.evandbrown.sequence.web.UniProtRequest;

/**
 * Tools used to compare the protein sequences of various organisms. 
 * Organisms are identified by their UniProt ID, which is used to retrieve
 * protein sequences from the UniProt.org web service.
 */
public class ProteinSeqTools {
	
	/**
	 * Create a comparison of two protein sequences given a UniProtRequest
	 * that has two organism IDs
	 * @param uniProtRequest
	 * @return The result of the comparison.
	 */
	public ProteinSeqComparison calculate(UniProtRequest uniProtRequest) {
		try {
		// Extract the sequences
		ProteinSequence bioJavaSeq1 = getProteinSequenceForId(uniProtRequest.id1);
		ProteinSequence bioJavaSeq2 = getProteinSequenceForId(uniProtRequest.id2);
		
		// Do the alignment
		SubstitutionMatrix<AminoAcidCompound> matrix = new SimpleSubstitutionMatrix<AminoAcidCompound>();
		SequencePair<ProteinSequence, AminoAcidCompound> pair = Alignments
				.getPairwiseAlignment(bioJavaSeq1, bioJavaSeq2,
						PairwiseSequenceAlignerType.GLOBAL,
						new SimpleGapPenalty(), matrix);
		
		// Create ProteinSequence wrappers
		com.evandbrown.sequence.model.ProteinSeq proteinSeq1 = new com.evandbrown.sequence.model.ProteinSeq();
		proteinSeq1.setId(uniProtRequest.id1);
		proteinSeq1.setSequence(bioJavaSeq1.getSequenceAsString());
		
		com.evandbrown.sequence.model.ProteinSeq proteinSeq2 = new com.evandbrown.sequence.model.ProteinSeq();
		proteinSeq2.setId(uniProtRequest.id2);
		proteinSeq2.setSequence(bioJavaSeq2.getSequenceAsString());
		
		// Create and return a new ProteinSequenceComparison
		ProteinSeqComparison comparison = new ProteinSeqComparison();
		comparison.setId(proteinSeq1.getId() + "-" + proteinSeq2.getId());
		comparison.setSequenceOne(proteinSeq1);
		comparison.setSequenceTwo(proteinSeq2);
		comparison.setAlignment(pair.toString());
		comparison.setLevenshteinDistance(getLevenshteinDistance(bioJavaSeq1.getSequenceAsString(), bioJavaSeq2.getSequenceAsString()));
		
		return comparison;
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * Return a ProteinSequence object for a given UniProt ID
	 * @param uniProtId
	 * @return
	 * @throws Exception
	 */
	public ProteinSequence getProteinSequenceForId(String uniProtId)
			throws Exception {
		URL uniprotFasta = new URL(String.format(
				"http://www.uniprot.org/uniprot/%s.fasta", uniProtId));
		ProteinSequence seq = FastaReaderHelper.readFastaProteinSequence(
				uniprotFasta.openStream()).get(uniProtId);
		System.out.printf("id : %s %s%n%s%n", uniProtId, seq,
				seq.getOriginalHeader());
		return seq;
	}

	/**
	 * Calculate the Levenshtein Distance between two strings.
	 * @param s
	 * @param t
	 * @return
	 */
	public static int getLevenshteinDistance(String s, String t) {
		if (s == null || t == null) {
			throw new IllegalArgumentException("Strings must not be null");
		}

		/*
		 * The difference between this impl. and the previous is that, rather
		 * than creating and retaining a matrix of size s.length()+1 by
		 * t.length()+1, we maintain two single-dimensional arrays of length
		 * s.length()+1. The first, d, is the 'current working' distance array
		 * that maintains the newest distance cost counts as we iterate through
		 * the characters of String s. Each time we increment the index of
		 * String t we are comparing, d is copied to p, the second int[]. Doing
		 * so allows us to retain the previous cost counts as required by the
		 * algorithm (taking the minimum of the cost count to the left, up one,
		 * and diagonally up and to the left of the current cost count being
		 * calculated). (Note that the arrays aren't really copied anymore, just
		 * switched...this is clearly much better than cloning an array or doing
		 * a System.arraycopy() each time through the outer loop.)
		 * 
		 * Effectively, the difference between the two implementations is this
		 * one does not cause an out of memory condition when calculating the LD
		 * over two very large strings.
		 */

		int n = s.length(); // length of s
		int m = t.length(); // length of t

		if (n == 0) {
			return m;
		} else if (m == 0) {
			return n;
		}

		int p[] = new int[n + 1]; // 'previous' cost array, horizontally
		int d[] = new int[n + 1]; // cost array, horizontally
		int _d[]; // placeholder to assist in swapping p and d

		// indexes into strings s and t
		int i; // iterates through s
		int j; // iterates through t

		char t_j; // jth character of t

		int cost; // cost

		for (i = 0; i <= n; i++) {
			p[i] = i;
		}

		for (j = 1; j <= m; j++) {
			t_j = t.charAt(j - 1);
			d[0] = j;

			for (i = 1; i <= n; i++) {
				cost = s.charAt(i - 1) == t_j ? 0 : 1;
				// minimum of cell to the left+1, to the top+1, diagonally left
				// and up +cost
				d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1]
						+ cost);
			}

			// copy current distance counts to 'previous row' distance counts
			_d = p;
			p = d;
			d = _d;
		}

		// our last action in the above loop was to switch d and p, so p now
		// actually has the most recent cost counts
		return p[n];
	}
}
