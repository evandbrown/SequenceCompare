package com.evandbrown.sequence.web;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Response;
import javax.ws.rs.PathParam;
import javax.persistence.EntityManager;
import java.net.URI;
import java.util.List;

import com.spaceprogram.simplejpa.EntityManagerFactoryImpl;
import com.evandbrown.sequence.model.ProteinSeqComparison;
import com.evandbrown.sequence.service.ProteinSeqService;
import com.evandbrown.sequence.util.ProteinSeqTools;

/**
 * A Jersey REST resource that exposes services related to protein sequence comparison. Operations
 * include retrieving all archived comparisons, retrieving a specific comparison of two specific
 * sequences, and requesting a new comparison of two sequences. All sequences are identified by their
 * UniProt ID.
 * 
 * The root resource exists at /comparisons/protein (/comparisons/* is the Jersey root)
 * 
 * @author Evan D. Brown
 *
 */
@Path("protein")
public class ProteinResource {
	// Protein sequence service for retrieving and persisting ProteinSequenceComparisons
	ProteinSeqService proteinService = new ProteinSeqService();
	
	// Toolkit for doing protein sequence comparisons
	ProteinSeqTools proteinTools = new ProteinSeqTools();
	
	/**
	 * Retrieve a comparison of two sequences by invoking /comparisons/protein/a12345/b12345
	 * 
	 * @param seq1 UniProt ID of the reference sequence.
	 * @param seq2 UniProt ID of the sequence to compare to.
	 * @return
	 */
	@GET
	@Path("{seq1}/{seq2}")
	@Produces("application/json")
	public ProteinSeqComparison getComparison(@PathParam("seq1") String seq1, 
			@PathParam("seq2") String seq2) {
		return proteinTools.calculate(new UniProtRequest(seq1, seq2));
	}

	/**
	 * Retrieve a list of all existing sequence comparisons.
	 * 
	 * @return
	 */
	@GET
	@Path("list")
	@Produces("application/json")
	public List<ProteinSeqComparison> getList() {
		return proteinService.getProteinSeqComparisons();
	}
	
	/**
	 * Request a new sequence comparison. As SimpleDB is eventually consistent, the created
	 * URI returned in the response may not be immediately available, but it will be...eventually.
	 * 
	 * @param uniProtRequest A JSON object specifying the two UniProt sequences to compare.
	 * 
	 * @return
	 */
	@PUT
	@Consumes("application/json")
	public Response create(UniProtRequest uniProtRequest) {
		try {

			ProteinSeqComparison comparison = proteinTools.calculate(uniProtRequest);
			proteinService.saveProteinSeqComparison(comparison);
			
			URI location = new URI(comparison.getId());
			return Response.created(location).build();
			
		} catch (Exception ex) {
			return Response.serverError().build();
		}
	}
}
