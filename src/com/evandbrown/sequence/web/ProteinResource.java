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

@Path("protein")
public class ProteinResource {
	// Protein sequence service for retrieving and persisting ProteinSequenceComparisons
	ProteinSeqService proteinService = new ProteinSeqService();
	
	// Toolkit for doing protein sequence comparisons
	ProteinSeqTools proteinTools = new ProteinSeqTools();
	
	@GET
	@Path("{organism1}/{organism2}")
	@Produces("application/json")
	public ProteinSeqComparison getComparison(@PathParam("organism1") String organism1, 
			@PathParam("organism2") String organism2) {
		return proteinTools.calculate(new UniProtRequest(organism1, organism2));
	}

	@GET
	@Path("list")
	@Produces("application/json")
	public List<ProteinSeqComparison> getList() {
		return proteinService.getProteinSeqComparisons();
	}
	
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
