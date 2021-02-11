package com.julien.dmJava;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DmJavaApplication implements CommandLineRunner {

	public static void main(String[] args) {

		SpringApplication.run(DmJavaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		BDD.main(args);
		Menu.menuChoice();

	}
}
