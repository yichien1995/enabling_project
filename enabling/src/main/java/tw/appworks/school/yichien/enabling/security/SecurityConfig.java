package tw.appworks.school.yichien.enabling.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	public static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

	private final PasswordEncoder passwordEncoder;

	public SecurityConfig(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http
//				.csrf(csrf -> csrf.disable())// Postman are not including a CSRF token in request
//				.authorizeHttpRequests(authorizeRequests ->
//						authorizeRequests
//								.requestMatchers("/admin", "/admin/**")
//								.hasAuthority("admin")
//								.requestMatchers("/myinstitution")
//								.hasAuthority("user")
//								.anyRequest()
//								.permitAll()
//				);
//		return http.build();
//	}


	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf.disable()) // Postman are not including a CSRF token in request
				.authorizeRequests(authorizeRequests ->
						authorizeRequests
								.requestMatchers("/**").permitAll() // Allow access to / without authentication
								.anyRequest().authenticated() // All other requests require authentication
				)
				.addFilterAt(new UsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // Use form login
		return http.build();
	}
}
