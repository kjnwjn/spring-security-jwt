package net.quanpham.security.api;

import javax.annotation.security.RolesAllowed;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
public class UserApi {

    @RolesAllowed("ADMIN")
    @GetMapping("hello")
    public String hello() {
        return "hello";
    }
}
