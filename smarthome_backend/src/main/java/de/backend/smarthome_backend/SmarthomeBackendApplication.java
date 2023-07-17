package de.backend.smarthome_backend;

import de.backend.smarthome_backend.service.HandschakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class SmarthomeBackendApplication {
	StaticSmartHome smartHome = new StaticSmartHome();
	public static void main(String[] args) {
		SpringApplication.run(SmarthomeBackendApplication.class, args);
	}
}
