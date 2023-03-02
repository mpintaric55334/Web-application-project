package hr.fer.progi.sinappsa.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurity {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
        throws Exception {
        http.httpBasic().disable();
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/").permitAll();

        return http.build();
    }

}
