package tw.appworks.school.yichien.enabling.service.impl;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
public class AuthenticationServiceImpl {
	public void authenticateWithAdminRole() {
		Authentication authentication = createAdminAuthentication();
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private Authentication createAdminAuthentication() {
		return new UsernamePasswordAuthenticationToken(
				new UserDetails() {
					@Override
					public Collection<? extends GrantedAuthority> getAuthorities() {
						return Collections.singletonList(new SimpleGrantedAuthority("admin"));
					}

					@Override
					public String getPassword() {
						return null;
					}

					@Override
					public String getUsername() {
						return null;
					}

					@Override
					public boolean isAccountNonExpired() {
						return true;
					}

					@Override
					public boolean isAccountNonLocked() {
						return true;
					}

					@Override
					public boolean isCredentialsNonExpired() {
						return true;
					}

					@Override
					public boolean isEnabled() {
						return true;
					}
				},
				null,
				Collections.singletonList(new SimpleGrantedAuthority("admin"))
		);
	}
}
