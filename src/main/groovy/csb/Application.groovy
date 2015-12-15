package csb
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan( basePackages=[ "csb.controller" ] )
// Note Application must be in same package as controller
public class Application {

    public static void main( String[] args ) {

        def ctx = SpringApplication.run( Application.class, args )

    }

}
