package com.predix.iot.fileTransfer;

import java.io.FileNotFoundException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * This is custom adaptor to pickup file
 * 
 * @author Eric Huynh
 * @version 1.0
 *
 */
@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages = "com.predix.iot")
public class Application {

	public static void main(String[] args) throws FileNotFoundException {
		SpringApplication.run(Application.class, args);
	}
}
