package net.quanpham.security.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import net.quanpham.security.entity.Role;
import net.quanpham.security.entity.User;
import net.quanpham.security.repository.RoleRepo;
import net.quanpham.security.repository.UserRepo;
import net.quanpham.security.util.JwtUtil;

// @AllArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    // public JwtFilter(){
    // super();
    // }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7, token.length());
            if (JwtUtil.validateJwtToken(token)) {
                Claims claims = JwtUtil.decodeJwtToken(token);
                String email = claims.get("email", String.class);
                User user = userRepo.findByEmail(email)
                        .orElseThrow(() -> new BadCredentialsException("User with email not found!"));
                List<Role> roles = roleRepo.findByUserId(user.getUserId());
                SecurityContextHolder.getContext()
                        .setAuthentication(UsernamePasswordAuthenticationToken.authenticated(user, "", roles));
            }
        }
        filterChain.doFilter(request, response);
    }
}
