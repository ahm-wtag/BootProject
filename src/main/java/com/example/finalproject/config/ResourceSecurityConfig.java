package com.example.finalproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.finalproject.security.JwtAuthenticationFilter;
import com.example.finalproject.security.JwtTokenVerifier;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .csrf()
        .disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilter(new JwtAuthenticationFilter(authenticationManager()))
        .addFilterAfter(new JwtTokenVerifier(), JwtAuthenticationFilter.class)
        .authorizeRequests()
        .anyRequest()
        .authenticated();
  }

  @Override
  public void configure(WebSecurity web) {
    web.ignoring()
        .antMatchers(HttpMethod.POST, "/api/customers");

    web.ignoring()
        .antMatchers(HttpMethod.GET, "/api/posts/**", "/api/customers/{customerId}/**");
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(10);
  }

  ////    @Bean
  //    CorsConfigurationSource corsConfigurationSource() {
  //        final CorsConfiguration configuration = new CorsConfiguration();
  //        configuration.setAllowedOrigins(ImmutableList.of("http://localhost:4200"));
  //        configuration.setAllowedMethods(ImmutableList.of("HEAD",
  //                "GET", "POST", "PUT", "DELETE", "PATCH","OPTIONS"));
  //
  //        configuration.setAllowCredentials(true);
  //
  //        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Content-Type"));
  //
  //        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
  //        source.registerCorsConfiguration("/**", configuration);
  //
  //        return source;
  //
  //    }
}
