package com.fmetin.readingisgood.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    private final UserAuthService authService;
    private final DelegatedAuthenticationEntryPoint delegatedAuthenticationEntryPoint;

    @Autowired
    public SecurityConfiguration(UserAuthService authService, DelegatedAuthenticationEntryPoint delegatedAuthenticationEntryPoint) {
        this.authService = authService;
        this.delegatedAuthenticationEntryPoint = delegatedAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();
        String[] permittedUrls = {"/v1/create-customer", "/h2-console", "/h2-console/**"};
        http.httpBasic().authenticationEntryPoint(delegatedAuthenticationEntryPoint).and().exceptionHandling();
        http.authorizeHttpRequests()
                .anyRequest().permitAll();
//                .requestMatchers(permittedUrls).permitAll()
//                .and()
//                .authorizeHttpRequests().anyRequest().authenticated();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        String[] permittedUrls = {"/h2-console", "/h2-console/**"};
        return web -> web.ignoring().requestMatchers(permittedUrls);
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService);
    }
}