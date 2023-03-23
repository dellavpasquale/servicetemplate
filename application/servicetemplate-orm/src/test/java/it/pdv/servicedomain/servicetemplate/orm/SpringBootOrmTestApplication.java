package it.pdv.servicedomain.servicetemplate.orm;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringBootOrmTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootOrmTestApplication.class, args);
	}

}