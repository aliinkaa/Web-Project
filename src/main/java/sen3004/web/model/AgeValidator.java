package sen3004.web.model;

import java.time.LocalDate;
import java.time.Clock;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AgeValidator implements Validator
{
    @Override 
    public boolean supports(Class <?> clazz)
    {
        return Applicant.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors)
    {
        Applicant applicant = (Applicant) target;

        //calculating age.
        if(applicant.getDateOfBirth() == null)
        {
            errors.rejectValue("dateOfBirth", "custom.error.emptyDate");
        }
        else
        if(applicant.applicantAge() < 18)
        {
            errors.rejectValue("dateOfBirth", "custom.error.notOver18");
        }
        
    }
}
