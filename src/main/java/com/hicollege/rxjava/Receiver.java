package com.hicollege.rxjava;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Receiver {

	private CountDownLatch latch = new CountDownLatch(1);

	private List<Object> list = new ArrayList<Object>();

	public void receiveMessage(Object message) {
		System.out.println("Received <" + message.toString()+ ">");
		list.add(message);
		latch.countDown();
	}

	public void test() {

	}

	public CountDownLatch getLatch() {
		return latch;
	}

}