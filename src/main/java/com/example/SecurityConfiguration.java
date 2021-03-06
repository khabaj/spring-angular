package com.example;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by Krystian on 2016-03-26.
 */
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource dataSource;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
        		.and()
                .authorizeRequests()
                .antMatchers("/index.html", "/home.html", "/login.html", "/", "/webjars/**", "/login", "/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and().logout().logoutSuccessUrl("/")
                .and().csrf().disable();
                /*.and().addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
                .csrf().csrfTokenRepository(csrfTokenRepository());*/
        
        http.headers().frameOptions().disable();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.jdbcAuthentication().dataSource(dataSource)
    		.usersByUsernameQuery("select login, password, 'true' from user where login=?")
    		.authoritiesByUsernameQuery(
    				"select user.login, role.role_name from user, role where user.login=? and user.role_id=role.role_id");
    }

}
