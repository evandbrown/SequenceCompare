package com.evandbrown.sequence.service;

import java.net.URL;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.spaceprogram.simplejpa.EntityManagerFactoryImpl;
import com.evandbrown.sequence.model.ProteinSeq;
import com.evandbrown.sequence.model.ProteinSeqComparison;
import com.evandbrown.sequence.web.UniProtRequest;

/**
 * Service used to do CRUD operations on ProteinSequence and
 * ProteinSequenceComparison objects
 * 
 * @author Evan D. Brown
 * 
 */
public class ProteinSeqService {
	EntityManagerFactoryImpl factory;

	/**
	 * Create a JPA factory with a new instance.
	 */
	public ProteinSeqService() {
		factory = new EntityManagerFactoryImpl("sequenceComparison", null);
	}

	/**
	 * Save a ProteinSequenceComparison, including the two ProteinSequence
	 * objects it is comparing.
	 * 
	 * @param comparison
	 * @return
	 */
	public void saveProteinSeqComparison(ProteinSeqComparison comparison) {
		EntityManager em = null;

		try {
			em = factory.createEntityManager();
			em.persist(comparison);
			em.persist(comparison.getSequenceOne());
			em.persist(comparison.getSequenceTwo());
			em.flush();
		} finally {
			if (em != null)
				em.close();
		}
	}

	/**
	 * Retrieve all ProteinSeqComparison objects in the database
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ProteinSeqComparison> getProteinSeqComparisons() {
		EntityManager em = null;

		try {
			em = factory.createEntityManager();
			Query query = em.createQuery("select p from com.evandbrown.sequence.model.ProteinSeqComparison p");
            return (List<ProteinSeqComparison>)query.getResultList();
		} finally {
			if (em != null)
				em.close();
		}
	}

}
