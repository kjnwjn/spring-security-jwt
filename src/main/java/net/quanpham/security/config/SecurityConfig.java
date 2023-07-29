package net.quanpham.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import net.quanpham.security.filter.JwtFilter;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;

@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true, prePostEnabled = true)
@Configuration
public class SecurityConfig {

    // private final UserRepo userRepo;
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                    reqs -> 
                        reqs.antMatchers("/api/v1/**")
                        .authenticated()
                        .antMatchers("/v3/api-docs", "/swagger-ui.html")
                        .permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form.loginProcessingUrl("/login")).httpBasic(withDefaults())
                .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    PasswordEncoder bcryptEncoder() {
        return new BCryptPasswordEncoder();
    }
}
