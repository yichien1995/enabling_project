package tw.appworks.school.yichien.enabling.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

	private final PasswordEncoder passwordEncoder;

	public SecurityConfig(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
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
