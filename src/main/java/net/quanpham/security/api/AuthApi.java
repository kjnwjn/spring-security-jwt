package net.quanpham.security.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import net.quanpham.security.entity.User;
import net.quanpham.security.util.JwtUtil;

@AllArgsConstructor
@RestController
@RequestMapping("auth")
public class AuthApi {
    @GetMapping("token")
    public ResponseEntity<String> getToken() {
        Map<String, Object> claims = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof User) {
            User user = (User) auth.getPrincipal();
            claims.put("email", user.getEmail());
            claims.put("roles", auth.getAuthorities());
            return ResponseEntity.ok(JwtUtil.genJwt(claims));
        }
        return ResponseEntity.status(401).build();
    }
}
