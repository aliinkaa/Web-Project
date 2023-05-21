package sen3004.web.model;

import jakarta.persistence.*;

@Entity
@Table(name="interest")
public class Interest 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "topic")
    private String topic;

    @ManyToOne
    @JoinColumn(name="applicantid")
    private Applicant applicant;

    public Interest()
    {

    }

    public Interest(Applicant applicant)
    {
        this.applicant = applicant;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }
}
