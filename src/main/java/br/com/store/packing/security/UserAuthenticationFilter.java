package br.com.store.packing.security;

import br.com.store.packing.domain.entity.User;
import br.com.store.packing.domain.repository.UserRepository;
import br.com.store.packing.service.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    private final List<String> publicRoutes = List.of(
            "/v3/api-docs",
            "/v3/api-docs.yaml",
            "/swagger-ui",
            "/swagger-ui/index.html",
            "/users/login"
    );


    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(request);

        if (token != null) {
            String subject = jwtTokenService.getSubjectFromToken(token);
            User user = repository.findByEmail(subject).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            UserDetailsImpl userDetails = new UserDetailsImpl(user);
            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            if (!publicRoutes.stream().anyMatch(request.getRequestURI()::contains)){
                System.out.println(request.getRequestURI());
                throw new RuntimeException("Missing auth token");
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.replace("Bearer ", "");
        }

        return null;
    }
}
