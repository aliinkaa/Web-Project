package sen3004.web.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.Clock;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "applicant")
public class Applicant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotEmpty
	//@Size(min = 5, max = 50)
	@Column(name = "fname")
	private String firstName;

	//@Size(min = 5, max = 50)
	@NotEmpty
	//@Size(min = 5, max = 50)
	@Column(name = "lname")
	private String lastName;

	@Past
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	@Column(name = "dob")
	private LocalDate dateOfBirth;

	@Email
	@NotEmpty
	//@Size(min = 5, max = 50)
	@Column(name = "email")
	private String email;

	//will be set depending on "how you hear about us" selection.
	@Column(name = "source")
	@NotEmpty
	private String source;

	@Column(name = "phone")
	@Size(min = 10, max=10)
	//@NotEmpty
	private String phone;

	//private List<String> checkBoxSelection;

	/*@Column(name = "experience_years")
	private Integer experience;*/

	public String getFullName() {
		return String.format("%s %s", firstName, lastName);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int applicantAge()
	{
		LocalDate currentDate = LocalDate.now(Clock.systemDefaultZone());
		return Period.between(dateOfBirth, currentDate).getYears();
	}

	/*public Integer getExperience() {
		return experience;
	}

	public void setExperience(Integer experience) {
		this.experience = experience;
	}*/

	/*public List<String> getCheckBoxSelection() {
		return checkBoxSelection;
	}

	public void setCheckBoxSelection(List<String> checkboxSelection) {
		this.checkBoxSelection = checkboxSelection;
	}*/

}
