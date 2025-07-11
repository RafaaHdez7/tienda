package backend.rafhergom.tfg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "Backend Tienda API",
        version = "1.0",
        description = "API para gestionar la tienda backend"
    )
)
public class BackendTienda2Application {

    public static void main(String[] args) {
        SpringApplication.run(BackendTienda2Application.class, args);
    }
}
