package net.quanpham.security;

import java.util.List;
import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import net.quanpham.security.entity.Role;
import net.quanpham.security.repository.RoleRepo;

@SpringBootApplication
public class SecurityApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SecurityApplication.class, args);
		RoleRepo roleRepo = context.getBean(RoleRepo.class);
		List<Role> roles = roleRepo.findByUserId(UUID.fromString("c0384cd6-859c-4b8f-8e7b-b6248c7fd1db"));
	}

}
