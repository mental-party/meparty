package com.teammental.merest.testappbasicauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class TestApplicaationBasicAuthConfiguration
    extends WebSecurityConfigurerAdapter {


  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder
                                  authenticationManagerBuilder)
      throws Exception {

    authenticationManagerBuilder
        .inMemoryAuthentication()
        .withUser(Constants.USERNAME)
        .password("{noop}" + Constants.PASSWORD)
        .authorities("ROLE_USER");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf()
        .disable()
        .authorizeRequests()
        .anyRequest()
        .authenticated()
        .and()
        .httpBasic()
        .authenticationEntryPoint(customAuthenticationEntryPoint());

  }


  @Bean
  public CustomAuthenticationEntryPoint customAuthenticationEntryPoint() {
    return new CustomAuthenticationEntryPoint();
  }
}
