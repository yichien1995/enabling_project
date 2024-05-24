package tw.appworks.school.yichien.enabling.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tw.appworks.school.yichien.enabling.middleware.AdminFilter;
import tw.appworks.school.yichien.enabling.middleware.CustomAuthenticationEntryPoint;
import tw.appworks.school.yichien.enabling.middleware.TherapistFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    private final AdminFilter adminFilter;

    private final TherapistFilter therapistFilter;

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    public SecurityConfig(AdminFilter adminFilter,
                          TherapistFilter therapistFilter, CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.adminFilter = adminFilter;
        this.therapistFilter = therapistFilter;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers("/admin", "/admin/**").hasAuthority("admin")
                                .requestMatchers("/therapist", "/therapist/**").hasAuthority("therapist")
                                .anyRequest().permitAll()
                )
                .addFilterAfter(adminFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(therapistFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(customAuthenticationEntryPoint)
                );
        return http.build();
    }

}
