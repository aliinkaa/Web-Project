package sen3004.web.service;

import java.util.List;

import jakarta.transaction.Transactional;
import sen3004.web.dao.ApplicantRepository;
import sen3004.web.model.Applicant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AppService {

	@Autowired
	private ApplicantRepository applicantRepository;


	public List<Applicant> findAllApplicants() {
		return applicantRepository.findAll();
	}
	
	public Applicant findApplicantById(long id) {
		return applicantRepository.findById(id);
	}
	
	public List<Applicant> findApplicantByFirstName(String firstName) {
		return applicantRepository.findByFirstName(firstName);
	}

	public void createApplicant(Applicant person) {
		applicantRepository.create(person);
	}

	public void deleteApplicant(long id) {
		applicantRepository.delete(id);
	}

}
