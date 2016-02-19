package com.eastflag.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
//@ImportResource("classpath:aaa/aaa.xml")
public class GameMainApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameMainApplication.class, args);
	}
}
