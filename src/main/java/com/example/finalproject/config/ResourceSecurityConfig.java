package com.example.finalproject.config;

import com.example.finalproject.jwt.JwtAuthenticationFilter;
import com.example.finalproject.jwt.JwtTokenVerifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ResourceSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilterAfter( new JwtTokenVerifier(), JwtAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/get_token").permitAll()
                .antMatchers(HttpMethod.GET,"/api/posts/**").hasAnyRole("ADMIN","READER")
                .antMatchers("/api/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        UserDetails admin =  User.builder()
                .username("admin")
                .password(passwordEncoder().encode("password"))
                .roles("ADMIN")
                .build();
        UserDetails reader = User.builder()
                .username("reader")
                .password(passwordEncoder().encode("password"))
                .roles("READER")
                .build();

        return new InMemoryUserDetailsManager(admin,reader);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }


}
