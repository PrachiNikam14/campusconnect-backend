package campusconnect.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CampusconnectBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CampusconnectBackendApplication.class, args);
	}

}
