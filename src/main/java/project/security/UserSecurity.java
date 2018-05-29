package project.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import project.dto.UserDTO;
import project.model.Profile;
import project.model.User;

import java.util.ArrayList;
import java.util.Collection;

public class UserSecurity implements UserDetails {
    static final long serialVersionUID = 84L;

    private Integer id;
    private String email;
    private String password;
    private Collection<SimpleGrantedAuthority> authorities;


    public UserSecurity() {
        //empty constructor because...
    }

    public UserSecurity(User user) {
        this.id = user.getUserID();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getUserProfile().getDescription()));
    }


    public Integer getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean hasProfile(Profile profile) {
        return getAuthorities().contains(new SimpleGrantedAuthority(profile.getDescription()));
    }

    public UserDTO getPrincipalAsDTO() {
        UserDTO output = new UserDTO();
        output.setIdNumber(this.id.toString());
        output.setEmail(this.email);
        output.setFunction(this.authorities.toArray()[0].toString());

        return output;
    }
}
