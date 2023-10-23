package backend.rafhergom.tfg;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class BackendTienda2Application {

	public static void main(String[] args) {
		SpringApplication.run(BackendTienda2Application.class, args);
	}
	 @Bean
	    public WebMvcConfigurer corsConfigurer() {
	        return new WebMvcConfigurer() {
	            @Override
	            public void addCorsMappings(CorsRegistry registry) {
	                // Permitir cualquier origen, método y encabezado (esto desactiva la seguridad CORS)
	                registry.addMapping("/**")
	                        .allowedOrigins("*")
	                        .allowedMethods("*")
	                        .allowedHeaders("*");
	            }
	        };
	    }
}
