package io.firogence.mobile_banking_channel_request_processor.configurations.security;

import com.google.gson.Gson;
import io.firogence.mobile_banking_channel_request_processor.dtos.GenericResponse;
import io.firogence.mobile_banking_channel_request_processor.exceptions.handlers.ExceptionResponse;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * @author Alex Kiburu
 */
public class IpFilter implements Filter {

    private final Gson gson = new Gson();

    private final List<String> allowedIps;

    public IpFilter(List<String> allowedIps) {
        this.allowedIps = allowedIps;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String clientIp = getClientIpAddress(httpRequest);

        if (!allowedIps.contains(clientIp)) {
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            ExceptionResponse error = ExceptionResponse.builder()
                    .message("Access denied: IP [" + clientIp + "] not allowed")
                    .status("1001")
                    .build();
//            httpResponse.getWriter().write("Access denied: IP [" + clientIp + "] not allowed");
            httpResponse.getWriter().write(gson.toJson(error));
            return;
        }

        chain.doFilter(request, response);
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }

        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }

        return request.getRemoteAddr();
    }
}