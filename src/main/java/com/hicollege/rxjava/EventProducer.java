package com.hicollege.rxjava;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class EventProducer {

	public static Stream<String> produceStrings() {
		Stream<String> stream = Stream.generate(() -> generateStringsWithDelay(0));
		return stream;
	}

	public static Stream<String> produceStringsWithDelay(int delayInMillis) {
		Stream<String> stream = Stream.generate(() -> generateStringsWithDelay(delayInMillis));
		return stream;
	}

	private static String generateStringsWithDelay(int millis) {
		if(millis > 0) {
			try {
				Thread.sleep(Math.round(millis * Math.random()));
			} catch (InterruptedException e) {}
		}
		return Integer.toString(ThreadLocalRandom.current().nextInt(0, 100 + 1));
	}


}
