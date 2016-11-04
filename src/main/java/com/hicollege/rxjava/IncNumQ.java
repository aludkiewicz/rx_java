package com.hicollege.rxjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IncNumQ {

	final static String incNumQ = "rx-java-inc";

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(IncNumQ.class, IncNumQ.incNumQ);
	}

}