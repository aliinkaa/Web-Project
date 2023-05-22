package sen3004.web.controller;

import jakarta.validation.Valid;
import sen3004.web.model.AgeValidator;
import sen3004.web.model.Applicant;
import sen3004.web.model.FormData;
import sen3004.web.model.Interest;
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
		mv.addObject("formData", new FormData());

		return mv;
	}

	@PostMapping("/applicant/add")
	public ModelAndView addApplicant(@ModelAttribute FormData formData, @Valid @ModelAttribute Applicant applicant, BindingResult result) {
		ModelAndView mv = new ModelAndView();
		//applicant = service.findApplicantById(applicant.getId());
		
		ageValidator.validate(applicant, result);

		if (result.hasErrors())
			mv.setViewName("application_form");
		else 
		{
			mv.setViewName("application_result");
			service.createApplicant(applicant);

			if(formData.getCheckBoxSelection() != null && !formData.getCheckBoxSelection().isEmpty())
			{
				var checkBox = formData.getCheckBoxSelection();
				for (String box : checkBox) 
				{
					Interest i = new Interest(applicant);
					System.out.println("IMDAT " + i.getApplicant().getId());
					i.setTopic(box);
					service.createInterest(i);
				}

				service.refreshApplicant(applicant);
				
			}

			mv.addObject("applicant", applicant);
		}

		return mv;
	}

	@PostMapping("/applicant/update/{id}")
	public ModelAndView updateApplicant(@ModelAttribute FormData formData, @PathVariable long id, @Valid @ModelAttribute Applicant applicant, BindingResult result) {
		ModelAndView mv = new ModelAndView();

		//applicant = service.findApplicantById(id);
		mv.addObject("applicant", applicant);

		ageValidator.validate(applicant, result);

		if (result.hasErrors())
			mv.setViewName("applicant_edit");
		else 
		{
			Applicant oldApplicant = service.findApplicantById(id);	
			if(oldApplicant != null)
			{
				oldApplicant.setFirstName(applicant.getFirstName());
				oldApplicant.setLastName(applicant.getLastName());
				oldApplicant.setEmail(applicant.getEmail());
				oldApplicant.setPhone(applicant.getPhone());
				oldApplicant.setSource(applicant.getSource());
				oldApplicant.setDateOfBirth(applicant.getDateOfBirth());

				//updating interests
				service.resetApplicantInterests(oldApplicant);

				if(formData.getCheckBoxSelection() != null && !formData.getCheckBoxSelection().isEmpty())
				{
					var checkBox = formData.getCheckBoxSelection();
					for (String box : checkBox) 
					{
						Interest i = new Interest(oldApplicant);
						i.setTopic(box);
						service.createInterest(i);
					}				
				}

				service.updateApplicant(oldApplicant);
				service.refreshApplicant(oldApplicant);
				mv.setViewName("applicant_view");
				mv.addObject("applicant", oldApplicant);
			}
			
		}

		return mv;
	}

	@GetMapping({ "/list-applicants"})
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView("applicant_list");
		mv.addObject("applicants", service.findAllApplicants());

		return mv;
	}
	
	/*@GetMapping("/find-applicant-by-first-name/{name}")
	public ModelAndView listByFirstName(@PathVariable String name) {
		ModelAndView mv = new ModelAndView("person-list");
		mv.addObject("applicant", service.findApplicantByFirstName(name));

		return mv;
	}*/

	@GetMapping({ "/applicant/result/{id}"})
	public ModelAndView resultApplicant(@PathVariable long id) {
		ModelAndView mv = new ModelAndView("application_result");
		Applicant applicant = service.findApplicantById(id);
		mv.addObject("applicant", applicant);

		return mv;
	}

	@GetMapping({ "/applicant/view/{id}"})
	public ModelAndView viewApplicant(@PathVariable long id) {
		ModelAndView mv = new ModelAndView("applicant_view");
		Applicant applicant = service.findApplicantById(id);
		mv.addObject("applicant", applicant);

		return mv;
	}

	@GetMapping({ "/applicant/edit/{id}"})
	public ModelAndView editApplicant(@PathVariable long id) {
		ModelAndView mv = new ModelAndView("applicant_edit");
		Applicant applicant = service.findApplicantById(id);
		FormData formData = new FormData();
		mv.addObject("applicant", applicant);
		mv.addObject("formData", formData);

		return mv;
	}

	@GetMapping("/applicant/delete/{id}")
	public ModelAndView deleteApplicant(@PathVariable long id) {
		ModelAndView mv = new ModelAndView("applicant_list");
	
		service.deleteApplicant(id);
		mv.addObject("applicants", service.findAllApplicants()); 
		return mv;
	}

}
