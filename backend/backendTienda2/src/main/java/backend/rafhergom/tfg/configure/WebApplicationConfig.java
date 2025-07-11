package backend.rafhergom.tfg.configure;

import javax.sql.DataSource;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import backend.rafhergom.tfg.repository.UsuarioRepository;

@Configuration
public class WebApplicationConfig implements WebMvcConfigurer {
	 private final DataSource dataSource;
	    
	    public WebApplicationConfig(DataSource dataSource) {
	        this.dataSource = dataSource;
	    }

	    @Bean
	    public UserDetailsService userDetailsService() {
	        JdbcDaoImpl jdbcDao = new JdbcDaoImpl();
	        jdbcDao.setUsersByUsernameQuery("select nombre, contrasena, activo from tfg.usuario where nombre = ?");
	        jdbcDao.setAuthoritiesByUsernameQuery("select nombre, rol from tfg.usuario where nombre = ?");
	        jdbcDao.setDataSource(dataSource);
	        return jdbcDao;
	    }

	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	    
	    @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	        return config.getAuthenticationManager();
	    }

	    @Bean
	    AuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

	        authProvider.setUserDetailsService(userDetailsService());
	        authProvider.setPasswordEncoder(passwordEncoder());

	        return authProvider;
	    }
		@Override
		public void addViewControllers(ViewControllerRegistry registry) {
		    registry.addViewController("/notFound").setViewName("/tfg/swagger-ui/");
		}
		
		
		@Bean
		public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> containerCustomizer() {
		    return container -> {
		        container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND,
		                "/notFound"));
		    };
		  }

}