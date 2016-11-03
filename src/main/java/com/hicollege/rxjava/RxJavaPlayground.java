package com.hicollege.rxjava;

import java.util.stream.Stream;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

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
		 * Creating an observable of an infinite stream
		 */
		Observable<Stream<String>> myObservable2 = Observable.create(t -> {
			t.onNext(EventProducer.produceStringsWithDelay(100));
			t.onCompleted();
		});

		Action1<? super Stream<String>> onNextAction = strm -> strm.limit(20).forEach(s -> System.out.println(s));
		Action1<Throwable> onErrorAction = ex -> System.out.println(ex);
		Action0 onCompletedAction = () -> System.out.println("Derp");
		myObservable2.subscribe(onNextAction, onErrorAction, onCompletedAction);



		/*
		 * Calling a web service
		 */
	}
}

