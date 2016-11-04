package com.hicollege.rxjava;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import rx.Observable;


@SpringBootApplication
public class QueueSubscriber {

	public static void main(final String... args) throws Exception {
		ConnectionFactory cf = new CachingConnectionFactory("localhost");

		RabbitTemplate template = new RabbitTemplate(cf);

		Observable<Message> msgObs = Observable.create( sub -> {
			while(true) {
				Message msg = template.receive(RandomNumQ.randNumQ);
				if(msg == null) {
					continue;
				}
				sub.onNext(msg);
			}
		});

		msgObs.toBlocking().forEach(m -> System.out.println(m));
	}
}