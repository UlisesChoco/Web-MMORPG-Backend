package com.chocolatada.tower;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class TowerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TowerApplication.class, args);
	}

}
