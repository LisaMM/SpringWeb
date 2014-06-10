package be.vdab.dao;

import javax.persistence.*;

import org.springframework.stereotype.Repository;

import be.vdab.entities.Werknemer;

@Repository
public class WerknemerDAOImpl implements WerknemerDAO {
	private EntityManager entityManager;

	@Override
	public Iterable<Werknemer> findAll() {
		TypedQuery<Werknemer> query =
				entityManager.createNamedQuery("Werknemer.findAll", Werknemer.class);
		return query.getResultList();
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
