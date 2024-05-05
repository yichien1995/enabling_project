package tw.appworks.school.yichien.enabling.middleware;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tw.appworks.school.yichien.enabling.dto.account.InstitutionUserDto;
import tw.appworks.school.yichien.enabling.service.impl.AuthenticationServiceImpl;
import tw.appworks.school.yichien.enabling.service.impl.SessionServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TherapistFilter extends OncePerRequestFilter {

	@Autowired
	private AuthenticationServiceImpl authenticationService;

	@Autowired
	private SessionServiceImpl sessionService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String requestUrl = request.getRequestURI();
		String domain = null;
		if (extractDomain(requestUrl) != null) {
			domain = extractDomain(requestUrl);

			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("enabling")) {
						String sessionId = cookie.getValue();
						List<InstitutionUserDto> institutionUserDtos =
								sessionService.getInstitutionUserDTOFromSession(sessionId);

						for (InstitutionUserDto data : institutionUserDtos) {
							if (data.getInstitutionDomain().equals(domain) && data.getRoleId() == 2) {
								authenticationService.authenticateWithRole("therapist");
							}
						}
					}
				}
			}
		}
		filterChain.doFilter(request, response);
	}

	private String extractDomain(String requestURI) {
		Pattern pattern = Pattern.compile("^/therapist/([^/]+)/?");
		Matcher matcher = pattern.matcher(requestURI);

		if (matcher.find()) {
			return matcher.group(1);
		} else {
			return null;
		}
	}
}
