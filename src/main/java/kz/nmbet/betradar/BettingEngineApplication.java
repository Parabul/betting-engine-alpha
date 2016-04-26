package kz.nmbet.betradar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = "kz.nmbet.betradar")
@EnableScheduling
public class BettingEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(BettingEngineApplication.class, args);
	}
}
