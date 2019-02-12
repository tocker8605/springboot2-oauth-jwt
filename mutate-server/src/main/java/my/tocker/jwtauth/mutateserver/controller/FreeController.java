package my.tocker.jwtauth.mutateserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/free")
public class FreeController {

    @RequestMapping("/user")
    public String user() {
        return "Hello Free User!";
    }

}
