package tw.appworks.school.yichien.enabling.middleware;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);
	@Value("${prefix.domain}")
	private String domainPrefix;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		logger.info("Request: {}", request);
		logger.info("Exception: {}", String.valueOf(authException));
		String requestUrl = request.getRequestURI();
		String domain = extractDomain(requestUrl);
		String redirectUrl = domainPrefix + domain + "/homepage.html";
		response.sendRedirect(redirectUrl);
	}

	private String extractDomain(String requestURI) {
		Pattern pattern = Pattern.compile("^/admin/([^/]+)/?");
		Matcher matcher = pattern.matcher(requestURI);

		Pattern pattern2 = Pattern.compile("^/therapist/([^/]+)/?");
		Matcher matcher2 = pattern2.matcher(requestURI);

		if (matcher.find()) {
			return matcher.group(1);
		} else if (matcher2.find()) {
			return matcher2.group(1);
		} else {
			return null;
		}
	}
}
