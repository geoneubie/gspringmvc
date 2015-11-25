package controller

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
// Note Application must be in same package as controller
public class Application {

    public static void main(String[] args) {
        def ctx = SpringApplication.run(Application.class, args)

        println("Let's inspect the beans provided by Spring Boot:")

        def beanNames = []
        beanNames = ctx.getBeanDefinitionNames()
        Arrays.sort(beanNames)

        beanNames.each {
            println "${it}"
        }
    }

}
