package com.hicollege.rxjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AlphabetQ {

	final static String alphabetQ = "rx-java-alphabet";

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(AlphabetQ.class, AlphabetQ.alphabetQ);
	}

}