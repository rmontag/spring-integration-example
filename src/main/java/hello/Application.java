package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

/**
 * kicks off the integration flow and also declares a handful of beans to support the integration flow. 
 * You also build the application into a standalone executable JAR file. 
 * 
 * We use Spring Boot’s SpringApplication to create the application context. 
 * Since we use an the XML namespace for the integration flow, notice that we use @ImportResource to load it into the application context.
 * 
 * @author rmontag
 *
 */
@SpringBootApplication
// For XML config
@ImportResource("/hello/integration.xml")
// For Java config
//@EnableIntegration
public class Application {
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext ctx = new SpringApplication(Application.class).run(args);
        System.out.println("Hit Enter to terminate");
        System.in.read();
        ctx.close();
    }

}
