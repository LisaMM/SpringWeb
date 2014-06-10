package be.vdab.dao;

import javax.persistence.*;

import org.springframework.stereotype.Repository;

import be.vdab.entities.Filiaal;

@Repository
class FiliaalDAOImpl implements FiliaalDAO {
	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void create(Filiaal filiaal) {
		entityManager.persist(filiaal);
	}

	@Override
	public Filiaal read(long id) {
		return entityManager.find(Filiaal.class, id);
	}

	@Override
	public void update(Filiaal filiaal) {
		entityManager.merge(filiaal);
	}

	@Override
	public void delete(long id) {
		Filiaal filiaal = entityManager.find(Filiaal.class, id);
		entityManager.remove(filiaal);
	}

	@Override
	public Iterable<Filiaal> findAll() {
		TypedQuery<Filiaal> query = entityManager.createNamedQuery(
				"Filiaal.findAll", Filiaal.class);
		return query.getResultList();
	}

	@Override
	public Iterable<Filiaal> findByPostcodeBetween(int van, int tot) {
		TypedQuery<Filiaal> query = entityManager.createNamedQuery(
				"Filiaal.findByPostcodeBetween", Filiaal.class);
		query.setParameter("van", van);
		query.setParameter("tot", tot);
		return query.getResultList();
	}

	@Override
	public long findAantalFilialen() {
		TypedQuery<Number> query = entityManager.createNamedQuery(
				"Filiaal.findAantal", Number.class);
		return query.getSingleResult().longValue();
	}

	@Override
	public Filiaal findByNaam(String naam) {
		TypedQuery<Filiaal> query = entityManager.createNamedQuery(
				"Filiaal.findByNaam", Filiaal.class);
		query.setParameter("naam", naam);
		try {
			return query.getSingleResult();
		} catch (NoResultException ex) { // geen record gevonden
			return null;
		}
	}
}
