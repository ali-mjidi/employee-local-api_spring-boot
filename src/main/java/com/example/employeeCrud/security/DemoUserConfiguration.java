package com.example.employeeCrud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class DemoUserConfiguration {

	//	Using for default db table and field name that spring recognizes

	//	@Bean
	public UserDetailsManager defaultUserDetailsManager(DataSource dataSource) {
		return new JdbcUserDetailsManager(dataSource);
	}

	// Using for custom db table and field names
	@Bean
	public UserDetailsManager userDetailsManager(DataSource dataSource) {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

		jdbcUserDetailsManager.setUsersByUsernameQuery(
				"SELECT user_id, pw, active FROM members WHERE user_id = ?"
		);

		jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
				"SELECT user_id, role FROM members WHERE user_id = ?"
		);

		return jdbcUserDetailsManager;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(configurer ->
				configurer
						.requestMatchers(HttpMethod.GET, "/api/employees").hasRole("EMPLOYEE")
						.requestMatchers(HttpMethod.GET, "/api/employees/**").hasRole("EMPLOYEE")
						.requestMatchers(HttpMethod.POST, "/api/employees").hasRole("MANAGER")
						.requestMatchers(HttpMethod.PUT, "/api/employees").hasRole("MANAGER")
						.requestMatchers(HttpMethod.PATCH, "/api/employees/**").hasRole("MANAGER")
						.requestMatchers(HttpMethod.DELETE, "/api/employees/**").hasRole("ADMIN")
		);

		// use HTTP basic authentication
		http.httpBasic(Customizer.withDefaults());

		// disable Cross Site Request Forgery (CSRF)
		http.csrf(AbstractHttpConfigurer::disable);

		return http.build();
	}
}
