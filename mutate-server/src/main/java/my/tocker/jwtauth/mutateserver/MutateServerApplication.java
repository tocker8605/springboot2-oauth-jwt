package my.tocker.jwtauth.mutateserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "my.tocker.jwtauth")
public class MutateServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MutateServerApplication.class, args);
    }
}
