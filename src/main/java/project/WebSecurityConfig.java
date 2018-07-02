package project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import project.security.JWTAuthentication;
import project.security.JWTAuthorization;
import project.security.JWTUtil;
import project.services.UserDetailsServiceImpl;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] PUBLIC_MATCHERS = {
            "/account/**"

    };
    private static final String[] PUBLIC_MATCHERSHTTPS = {
            "/**"

    };

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JWTUtil jwtUtil;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();

        http.requiresChannel()
                .antMatchers(PUBLIC_MATCHERSHTTPS).requiresSecure();

        http.authorizeRequests().antMatchers(PUBLIC_MATCHERS).permitAll().anyRequest().authenticated();

        http.addFilter(new JWTAuthentication(authenticationManager(), jwtUtil));

        http.addFilter(new JWTAuthorization(authenticationManager(), jwtUtil, userDetailsService));

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH"));
        source.registerCorsConfiguration("/**", config);
        return source;
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}