package com.serviceapi.configs;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class will be responsible to send response when authentication fails.
 * When authentication fails the request filter chain will be interrupted.
 */
@Component
public class AuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    /**
     * Realm name declared as constant
     */
    private static final String REALM = "apiMsg-controller";

    /**
     * Executes post property set
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName(REALM);
        super.afterPropertiesSet();
    }

    /**
     * Validates Basic authentication and sends
     * authentication error for invalid credentials
     * @param request {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @param authException {@link AuthenticationException}
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {

        response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        writer.println("Authentication Error: HTTP Status 401 - " + authException.getMessage());
    }
}
