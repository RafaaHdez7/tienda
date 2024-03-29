package backend.rafhergom.tfg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class BackendTienda2Application {

	public static void main(String[] args) {
		SpringApplication.run(BackendTienda2Application.class, args);
	}
	
}
