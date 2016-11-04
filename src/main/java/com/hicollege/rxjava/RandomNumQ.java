package com.hicollege.rxjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RandomNumQ {

	final static String randNumQ = "rx-java-random-nums";

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(RandomNumQ.class, randNumQ);
	}

}