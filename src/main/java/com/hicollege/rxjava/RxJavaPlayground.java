package com.hicollege.rxjava;

import rx.Observable;
import rx.Subscriber;

public class RxJavaPlayground {

	public static void main(String[] args) {
		/*
		 * Hello world. Kindof a lot of boilerplate!
		 */
		Observable<String> myObservable = Observable.create(
				t -> {
					t.onNext("Hello World!");
					t.onCompleted();
				});

		Subscriber<String> mySub = new Subscriber<String>() {

			@Override
			public void onCompleted() {}

			@Override
			public void onError(Throwable e) {}

			@Override
			public void onNext(String t) {
				System.out.println(t + " v1");
			}
		};
		myObservable.subscribe(mySub);

		/*
		 * Shorter, more elegant & declarative, Hello world
		 */
		myObservable = Observable.just("Hello World!");
		myObservable.subscribe(s -> System.out.println(s + " v2"));



		/*
		 * Calling a web service
		 */
	}
}

