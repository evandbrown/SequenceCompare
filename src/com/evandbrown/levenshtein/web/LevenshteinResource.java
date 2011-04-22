package com.evandbrown.levenshtein.web;

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
import com.evandbrown.levenshtein.model.LevenshteinComparison;

@Path("levenshtein")
public class LevenshteinResource {

	@GET
	@Path("/{compid}")
	@Produces("application/json")
	public LevenshteinComparison get(@PathParam("compid") String compId) {
		LevenshteinComparison lc = new LevenshteinComparison("foo", "foo");
		lc.setId(compId);
		
		return lc;
	}

	@PUT
	@Consumes("application/json")
	public Response create(LevenshteinComparison lc) {
		try {
			EntityManagerFactoryImpl factory = new EntityManagerFactoryImpl("sequenceComparison", null);
			EntityManager em = factory.createEntityManager();
			
			em.persist(lc);
			
			URI location = new URI(lc.getId());
			return Response.created(location).build();
			
		} catch (Exception ex) {
			return Response.serverError().build();
		}
	}
}
