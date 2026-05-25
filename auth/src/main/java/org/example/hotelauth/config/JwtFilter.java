package org.example.hotelauth.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.hotelauth.service.JwtService;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if(header == null){
            filterChain.doFilter(
                    request,
                    response
            );
            return;
        }

        assert header != null;
        String token = header.substring(7);
        if(jwtService.validate(token)){
            String username = jwtService.extractUsername(token);
            Authentication auth = UsernamePasswordAuthenticationToken.authenticated(username, null, List.of());
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }
}
