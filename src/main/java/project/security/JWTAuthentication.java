package project.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import project.dto.CredentialsDTO;
import project.restcontroller.RestAccountController;
import project.restcontroller.RestUserController;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class JWTAuthentication extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private JWTUtil jwtUtil;

    public JWTAuthentication(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) {

        try {
            CredentialsDTO creds = new ObjectMapper().readValue(req.getInputStream(), CredentialsDTO.class);

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>());

            return authenticationManager.authenticate(token);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {

        Integer userID = ((UserSecurity) auth.getPrincipal()).getId();
        String email = ((UserSecurity) auth.getPrincipal()).getUsername();
        String token = jwtUtil.generateToken(email);
        res.addHeader("Authorization", "Bearer " + token);

        Link userDetails = linkTo(RestUserController.class).slash("users").slash(userID).withRel("myAccount").withType(HttpMethod.GET.name());

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.writeValue(res.getOutputStream(), userDetails);


    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response, AuthenticationException failed) throws IOException{

        CredentialsDTO creds = new ObjectMapper().readValue(request.getInputStream(), CredentialsDTO.class);


        ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
        oos.writeObject(creds);
        oos.close();

        Link userDetails = linkTo(RestAccountController.class).slash("logIn").withRel("checkValid");

        //response.sendRedirect(userDetails.getHref());


        response.setStatus(HttpStatus.UNAUTHORIZED.value());

    }
}
