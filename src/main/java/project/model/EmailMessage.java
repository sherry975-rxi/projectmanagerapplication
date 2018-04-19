package project.model;

import org.springframework.hateoas.ResourceSupport;

import java.util.Objects;

public class EmailMessage extends ResourceSupport {

    private String emailAddress;
    private String subject;
    private String body;



    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmailMessage)) return false;
        if (!super.equals(o)) return false;
        EmailMessage that = (EmailMessage) o;
        return Objects.equals(emailAddress, that.emailAddress) &&
                Objects.equals(subject, that.subject) &&
                Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), emailAddress, subject, body);
    }
}
