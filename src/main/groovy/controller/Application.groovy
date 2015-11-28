package controller

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.core.env.ConfigurableEnvironment

@SpringBootApplication
// Note Application must be in same package as controller
public class Application {

    public static void main(String[] args) {

        def ctx = SpringApplication.run(Application.class, args)

    }

}
