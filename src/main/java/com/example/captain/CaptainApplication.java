package com.example.captain;

import com.example.captain.stream.CaptainStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(CaptainStream.class)
public class CaptainApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaptainApplication.class, args);
	}

}

