package kz.nmbet.betradar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "kz.nmbet.betradar")
public class BettingEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(BettingEngineApplication.class, args);
	}
}
