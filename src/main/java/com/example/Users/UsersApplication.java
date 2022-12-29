package com.example.Users;

import com.example.Users.Seeders.SportsSeeder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UsersApplication {
	public static void main(String[] args) {
		SpringApplication.run(UsersApplication.class, args);
	}
		@Bean
		public SportsSeeder sportss() {
			return new SportsSeeder();
		}
}
