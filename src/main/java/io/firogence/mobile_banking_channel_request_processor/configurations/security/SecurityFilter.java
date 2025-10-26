package io.firogence.mobile_banking_channel_request_processor.configurations.security;

import io.firogence.mobile_banking_channel_request_processor.exceptions.InvalidAuthenticationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author Alex Kiburu
 */
@Slf4j
@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            // Log details before throwing the exception
            log.warn("❌ Failed authentication attempt: IP = {}, Path = {}",
                    request.getRemoteAddr(), request.getRequestURI());

            throw new InvalidAuthenticationException("Authentication failed: Invalid credentials or session expired.");
        }

        // Log the authenticated user
        String username = authentication.getName();
        log.info("✅ Custom Filter: Authenticated user = {}, Path = {}", username, request.getRequestURI());

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}