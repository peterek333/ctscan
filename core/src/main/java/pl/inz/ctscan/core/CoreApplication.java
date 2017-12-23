package pl.inz.ctscan.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "pl.inz.ctscan" })
@EnableAutoConfiguration
//@EnableMongoAuditing
//@EnableMongoRepositories(basePackages = "pl.inz.ctscan.db")
//@EntityScan("pl.inz.ctscan.model")
//@EnableJpaRepositories("pl.inz.ctscan.db")
public class CoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}
}
