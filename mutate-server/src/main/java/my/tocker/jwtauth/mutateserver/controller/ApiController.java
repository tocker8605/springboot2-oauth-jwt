package my.tocker.jwtauth.mutateserver.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    @RequestMapping("/user")
    public String user() {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "Hello Auth User! : " + principal;
    }

}
