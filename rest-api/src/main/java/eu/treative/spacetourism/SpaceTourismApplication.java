package eu.treative.spacetourism;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class SpaceTourismApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpaceTourismApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("SPACE API ROCKET WITH TOURISTS IS RUNNING");
	}
}
