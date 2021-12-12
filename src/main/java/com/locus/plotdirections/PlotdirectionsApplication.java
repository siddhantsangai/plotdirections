package com.locus.plotdirections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.locus")
@SpringBootApplication
public class PlotdirectionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlotdirectionsApplication.class, args);
	}
}
