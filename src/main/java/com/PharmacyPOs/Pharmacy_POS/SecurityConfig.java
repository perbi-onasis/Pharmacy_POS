//package com.PharmacyPOs.Pharmacy_POS;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
//
//@Configuration
////@EnableWebSecurity
//public  class SecurityConfig extends WebSecurityConfigurerAdapter{
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception{
//        http
//                .authorizeRequests()
//                    .antMatchers("/public/**").permitAll
//                    .anyRequests().authenticated()
//                    .and()
//                .formLogin()
//                .disable();
//    }
//}
