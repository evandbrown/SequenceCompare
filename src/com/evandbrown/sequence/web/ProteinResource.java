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

import com.spaceprogram.simplejpa.EntityManagerFactoryImpl;
import com.evandbrown.sequence.model.ProteinSequenceComparison;
import com.evandbrown.sequence.service.ProteinSequenceService;

@Path("protein")
public class ProteinResource {
	// Protein sequence services
	ProteinSequenceService proteinService = new ProteinSequenceService();
	
	@GET
	@Path("/{organism1}/{organism2}")
	@Produces("application/json")
	public ProteinSequenceComparison get(@PathParam("organism1") String organism1, 
			@PathParam("organism2") String organism2) {
		return proteinService.calculate(new UniProtRequest(organism1, organism2));
	}

	@PUT
	@Consumes("application/json")
	public Response create(UniProtRequest uniProtRequest) {
		try {
			EntityManagerFactoryImpl factory = new EntityManagerFactoryImpl("sequenceComparison", null);
			EntityManager em = factory.createEntityManager();
			
			ProteinSequenceComparison comparison = proteinService.calculate(uniProtRequest);
			
			em.persist(comparison);
			em.close();
			URI location = new URI(comparison.getId());
			return Response.created(location).build();
			
		} catch (Exception ex) {
			return Response.serverError().build();
		}
	}
}
