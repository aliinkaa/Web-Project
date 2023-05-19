package sen3004.web.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sen3004.web.model.Applicant;

import org.springframework.stereotype.Repository;

@Repository
public class ApplicantRepository {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Applicant> findAll() {
		return entityManager.createQuery("from Applicant", Applicant.class).getResultList();
	}

	public Applicant findById(long id) {
		return entityManager.find(Applicant.class, id);
	}

	public List<Applicant> findByFirstName(String firstName) {
		return entityManager.createQuery("from Applicant where firstName = :first", Applicant.class)
		.setParameter("first", firstName).getResultList(); 
	}

	public void create(Applicant applicant) {
		entityManager.persist(applicant);
	}

	public void delete(long id) {
		entityManager.remove(entityManager.getReference(Applicant.class, id));
	}

	public void update(Applicant applicant) {
		entityManager.merge(applicant);
	}

}
