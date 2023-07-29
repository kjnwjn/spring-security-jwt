package net.quanpham.security.service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import net.quanpham.security.entity.Role;
import net.quanpham.security.entity.User;
import net.quanpham.security.repository.RoleRepo;
import net.quanpham.security.repository.UserRepo;

@AllArgsConstructor
@Service
public class AuthService implements AuthenticationProvider {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passEncoder;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String email = auth.getPrincipal().toString();
        String pwd = auth.getCredentials().toString();
        User user = userRepo.findByEmail(email)
        .orElseThrow(() -> new BadCredentialsException("User not found!"));
        if (passEncoder.matches(pwd, user.getPwd())) {
            List<Role> roles = roleRepo.findByUserId(user.getUserId());
            return UsernamePasswordAuthenticationToken.authenticated(user, "", roles);
        }
        throw new BadCredentialsException("Wrong password!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
    
}
