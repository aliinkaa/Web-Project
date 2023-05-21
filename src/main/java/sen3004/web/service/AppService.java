package sen3004.web.service;

import java.util.List;

import jakarta.transaction.Transactional;
import sen3004.web.dao.ApplicantRepository;
import sen3004.web.dao.InterestRepository;
import sen3004.web.model.Applicant;
import sen3004.web.model.Interest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AppService {

	@Autowired
	private ApplicantRepository applicantRepository;

	@Autowired
	private InterestRepository interestRepository;

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
		interestRepository.deleteByApplicantId(id);
		applicantRepository.delete(id);
	}

	public void updateApplicant(Applicant applicant){
		applicantRepository.update(applicant);
	}

	public void createInterest(Interest interest) {

		interestRepository.create(interest);
	}

	public void deleteInterest(long id) {
		interestRepository.delete(id);
	}

	public void refreshApplicant(Applicant applicant)
	{
		applicantRepository.refresh(applicant);
	}

}
