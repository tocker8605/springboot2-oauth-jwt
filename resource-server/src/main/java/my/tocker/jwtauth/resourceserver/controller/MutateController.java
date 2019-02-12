package my.tocker.jwtauth.resourceserver.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mutate")
public class MutateController {

    @RequestMapping("/tockeronly")
    @PreAuthorize("hasRole('TOCKER')")
    public String tockeronly() {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "for tocker : " + principal;
    }

    @RequestMapping("/permission")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'TOCKER')")
    public String notfree() {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "Has permition : " + principal;
    }

    @RequestMapping("/free")
    public String free() {
        return "Free Permition";
    }

}
