package com.hicollege.events;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.nio.client.HttpAsyncClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller returning the a few events
 */
@RestController
@RequestMapping(value = "/generator", method = RequestMethod.GET)
public class EventController {

	private HttpAsyncClient client;
	private boolean stopped;

	@RequestMapping(value = "/start")
	public void start() throws Exception {

		this.stopped = false;
		client = HttpAsyncClients.createMinimal();
		while(!stopped) {
			HttpPost post = new HttpPost("http://127.0.0.1/events");
			StringEntity entity = new StringEntity("Derp " + Math.random());
			post.addHeader("content-type", "application/json");
			post.setEntity(entity);
			client.execute(post, new FutureCallback<HttpResponse>() {

				@Override
				public void failed(Exception ex) {
				}

				@Override
				public void completed(HttpResponse result) {
				}

				@Override
				public void cancelled() {
				}
			});
			sleep(5);
		}
	}

	private void sleep(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			// Do nothing
		}
	}

	@RequestMapping(value = "/stop")
	public void stop() throws Exception {
		this.stopped = true;
	}
}