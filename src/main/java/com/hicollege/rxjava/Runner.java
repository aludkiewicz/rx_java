package com.hicollege.rxjava;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * This class sends out messages to the rabbitMQ queues
 */
@Component
public class Runner implements CommandLineRunner {

	private final RabbitTemplate rabbitTemplate;
	private ExecutorService executor;

	private static final String[] alphabet =
		{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
		"m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x",
		"y", "z", "å", "ä", "ö", "A", "B", "C", "D", "E", "F", "G",
		"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
		"T", "U", "V", "W", "X", "Y", "Z", "Å", "Ä", "Ö"};

	public Runner(RabbitTemplate rabbitTemplate, ConfigurableApplicationContext context) {
		this.rabbitTemplate = rabbitTemplate;
		this.executor = Executors.newFixedThreadPool(3);
	}

	@Override
	public void run(String... args) throws Exception {
		switch (args[0]) {
		case RandomNumQ.randNumQ:
			executor.submit(() -> {
				while (true) {
					rabbitTemplate.convertAndSend(RandomNumQ.randNumQ, ThreadLocalRandom.current().nextInt(0, 1001));
					sleep(500);
				}
			});
			break;
		case IncNumQ.incNumQ:
			executor.submit(() -> {
				int i = 0;
				while (true) {
					rabbitTemplate.convertAndSend(IncNumQ.incNumQ, i++);
					sleep(450);
				}
			});
			break;
		case AlphabetQ.alphabetQ:
			executor.submit(() -> {
				int i = 0;
				while (true) {
					String letter = alphabet[i++ % alphabet.length];
					rabbitTemplate.convertAndSend(AlphabetQ.alphabetQ, letter);
					sleep(570);
				}
			});
			break;
		default:
			throw new IllegalStateException("Unknown queue!");
		}
	}

	public void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}
}