package com.usermanagement.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Inject the authenticationEntryPoint so we can delegate 401 responses consistently.
     */
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        String token = null;
        String username = null;

        try {
            if (header != null && header.startsWith("Bearer ")) {
                token = header.substring(7);
                // getUsernameFromToken may throw an exception for malformed/expired tokens
                username = jwtTokenProvider.getUsernameFromToken(token);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // validateToken may also throw for invalid/expired tokens, or return false
                if (jwtTokenProvider.validateToken(token)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    // token invalid -> treat as authentication error
                    throw new InsufficientAuthenticationException("Invalid or expired JWT token");
                }
            }

            // proceed normally
            filterChain.doFilter(request, response);

        } catch (AuthenticationException ex) {
            // clear context and delegate to AuthenticationEntryPoint (produces 401 JSON)
            SecurityContextHolder.clearContext();
            authenticationEntryPoint.commence(request, response, ex);
            // do NOT continue the filter chain after an authentication failure
        } catch (Exception ex) {
            // any unexpected exception: clear context and send a 401 with message
            SecurityContextHolder.clearContext();
            AuthenticationException authEx =
                    new InsufficientAuthenticationException("Authentication failed: " + ex.getMessage(), ex);
            authenticationEntryPoint.commence(request, response, authEx);
        }
    }
}
