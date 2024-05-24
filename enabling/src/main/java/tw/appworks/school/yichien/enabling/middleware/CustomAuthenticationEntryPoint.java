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
import java.util.Enumeration;
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
        String clientIpAddress = getClientIpAddress(request);
        String requestMethod = request.getMethod();
        String requestUrl = request.getRequestURL().toString();
        String queryString = request.getQueryString();
        String requestHeaders = getRequestHeaders(request);
        String requestedFileName = getRequestedFileName(requestUrl);

        logger.info("Request from IP: {}", clientIpAddress);
        logger.info("Request Method: {}", requestMethod);
        logger.info("Request URL: {}", requestUrl);
        if (queryString != null) {
            logger.info("Query String: {}", queryString);
        }
        logger.info("Requested File: {}", requestedFileName);
        logger.info("Request Headers: {}", requestHeaders);
        logger.info("Exception: {}", String.valueOf(authException));


        String requestUri = request.getRequestURI();
        String domain = extractDomain(requestUri);
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

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        if (xForwardedForHeader == null) {
            return request.getRemoteAddr();
        }
        // In case of multiple proxies, the first IP in the list is the actual client IP
        return xForwardedForHeader.split(",")[0];
    }

    private String getRequestHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        StringBuilder headersBuilder = new StringBuilder();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headersBuilder.append(headerName).append(": ").append(request.getHeader(headerName)).append("\n");
        }
        return headersBuilder.toString();
    }

    private String getRequestedFileName(String requestUrl) {
        int lastSlashIndex = requestUrl.lastIndexOf('/');
        if (lastSlashIndex != -1 && lastSlashIndex < requestUrl.length() - 1) {
            return requestUrl.substring(lastSlashIndex + 1);
        }
        return "";
    }
}
