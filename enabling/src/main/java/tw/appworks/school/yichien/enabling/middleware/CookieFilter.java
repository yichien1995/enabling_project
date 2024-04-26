package tw.appworks.school.yichien.enabling.middleware;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

public class CookieFilter extends OncePerRequestFilter {

	private static final String COOKIE_NAME = "enabling";

	@Override
	protected void doFilterInternal(HttpServletRequest request,
	                                HttpServletResponse response,
	                                FilterChain filterChain) throws ServletException, IOException {
		Optional<Cookie> cookieAuth = Stream.of(Optional.ofNullable(request.getCookies())
						.orElse(new Cookie[0]))
				.filter(cookie -> COOKIE_NAME.equals(cookie.getName()))
				.findFirst();

		cookieAuth.ifPresent(cookie -> {
			SecurityContextHolder.getContext().setAuthentication(new PreAuthenticatedAuthenticationToken(cookie.getValue(), null));
		});

		filterChain.doFilter(request, response);
	}
}
