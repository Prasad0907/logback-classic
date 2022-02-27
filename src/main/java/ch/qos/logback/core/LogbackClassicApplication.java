package ch.qos.logback.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ch.qos.logback.core.service.LogbackServiceImpl;

@SpringBootApplication
public class LogbackClassicApplication implements CommandLineRunner{
	private static final Logger LOGGER = LoggerFactory.getLogger(LogbackClassicApplication.class);
	
	@Autowired
	LogbackServiceImpl service;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(LogbackClassicApplication.class);
		app.run(args);
	}

	@Override
	public void run(String... args) {
		if (args.length < 1) {
			LOGGER.error("Please specify the filepath to analyse.");
			return;
		}
		service.analyse(args[0]);
	}

}
