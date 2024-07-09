package it.epicode.valhallagaming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("it.epicode.valhallagaming.entity")
@EnableJpaRepositories("it.epicode.valhallagaming.repository")
public class ValhallagamingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ValhallagamingApplication.class, args);
	}

}
