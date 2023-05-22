package sen3004.web.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sen3004.web.model.Interest;

@Repository
public class InterestRepository 
{
    @PersistenceContext
    private EntityManager entityManager;
    
    public List<Interest> findAll() {
		return entityManager.createQuery("from interest", Interest.class).getResultList();
	}

	public Interest findById(long id) {
		return entityManager.find(Interest.class, id);
	}

	public void create(Interest interest) {
		entityManager.persist(interest);
	}

	public void delete(long id) {
		entityManager.remove(entityManager.getReference(Interest.class, id));
	}

	public void deleteByApplicantId(long id) {
		entityManager.createQuery("delete from Interest where applicant.id = :id")
		.setParameter("id", id).executeUpdate();
	}

    public List<Interest> findByApplicantId(long id) {
		return entityManager.createQuery("from Interest where applicant.id = :id", Interest.class)
		.setParameter("id", id).getResultList();
	}
}
