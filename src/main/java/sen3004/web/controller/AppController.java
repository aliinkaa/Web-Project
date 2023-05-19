package sen3004.web.controller;

import jakarta.validation.Valid;
import sen3004.web.model.AgeValidator;
import sen3004.web.model.Applicant;
import sen3004.web.service.AppService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController {

	@Autowired
	private AppService service;

	@Autowired
	AgeValidator ageValidator;

	@GetMapping({ "/applicant/new"})
	public ModelAndView newApplicant() {
		ModelAndView mv = new ModelAndView("application_form");
		mv.addObject("applicant", new Applicant());

		return mv;
	}

	@PostMapping("/applicant/add")
	public ModelAndView addApplicant(@Valid @ModelAttribute Applicant applicant, BindingResult result) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("applicant", applicant);

		ageValidator.validate(applicant, result);

		if (result.hasErrors())
			mv.setViewName("application_form");
		else {
			mv.setViewName("person-list");
			service.createApplicant(applicant);
			mv.addObject("applicants", service.findAllApplicants());
		}

		return mv;
	}

	@GetMapping({ "/list-applicants"})
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView("person-list");
		mv.addObject("applicants", service.findAllApplicants());

		return mv;
	}
	
	@GetMapping("/find-applicant-by-first-name/{name}")
	public ModelAndView listByFirstName(@PathVariable String name) {
		ModelAndView mv = new ModelAndView("person-list");
		mv.addObject("applicant", service.findApplicantByFirstName(name));

		return mv;
	}

	@GetMapping({ "/applicant/view/{id}"})
	public ModelAndView viewApplicant(@PathVariable long id) {
		ModelAndView mv = new ModelAndView("view-person");
		Applicant applicant = service.findApplicantById(id);
		mv.addObject("applicant", applicant);

		return mv;
	}

	@GetMapping("/applicant/delete/{id}")
	public ModelAndView deleteApplicant(@PathVariable long id) {
		ModelAndView mv = new ModelAndView("person-list");
		service.deleteApplicant(id);
		mv.addObject("applicants", service.findAllApplicants()); 
		return mv;
	}

}
