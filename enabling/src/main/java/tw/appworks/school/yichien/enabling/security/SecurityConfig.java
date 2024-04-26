package tw.appworks.school.yichien.enabling.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tw.appworks.school.yichien.enabling.middleware.AdminFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	public static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

	private final PasswordEncoder passwordEncoder;

	private final AdminFilter adminFilter;

	public SecurityConfig(PasswordEncoder passwordEncoder, AdminFilter adminFilter) {
		this.passwordEncoder = passwordEncoder;
		this.adminFilter = adminFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf.disable())
				.authorizeRequests(authorizeRequests ->
						authorizeRequests
								.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
								.requestMatchers("/admin", "/admin/**").hasAuthority("admin")
								.anyRequest().permitAll()
				)
				.addFilterAfter(adminFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

}
