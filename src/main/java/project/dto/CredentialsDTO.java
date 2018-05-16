package project.dto;

import java.io.Serializable;

public class CredentialsDTO implements Serializable {
    static final long serialVersionUID = 62L;

    private String email;
    private String password;

    public CredentialsDTO() {
        //empty constructor because...
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
