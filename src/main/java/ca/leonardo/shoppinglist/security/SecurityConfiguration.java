package ca.leonardo.shoppinglist.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/createUser").permitAll()
			.antMatchers("/").permitAll()
			.antMatchers("/test/**").hasRole("USER")
			.anyRequest().authenticated()
			.and()
			.formLogin().loginPage("/login").permitAll()
			.and()
			.logout()
				.invalidateHttpSession(true)
				.clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login?logout")
				.permitAll();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() { 
	    return NoOpPasswordEncoder.getInstance(); 
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) 
			throws Exception {
	 	auth.jdbcAuthentication()
	 		.dataSource(dataSource);
	}
}
