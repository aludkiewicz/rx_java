package com.hicollege.rxjava;

import java.util.stream.Stream;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class RxJavaPlayground {

	private static final String NEWLINE = "\r\n";

	public static void main(String[] args) {
		helloWorld();

		System.out.println("");
		System.out.println("---------------------");
		elegantHelloWorld();

		System.out.println("");
		System.out.println("---------------------");
		observableFromInfiniteStream();

		System.out.println("");
		System.out.println("---------------------");
		mergingObservablesAsync(false);

		System.out.println("");
		System.out.println("---------------------");
		mergingObservablesAsync(true);

		System.out.println("");
		System.out.println("---------------------");
		mergingWithErrorAndFallback();

		System.out.println("");
		System.out.println("---------------------");
		zippingObservables();


		//Backpressure example
		//		Observable<Integer> myObs1 = Observable.create(t -> {
		//			int i = 0;
		//			while(true) {
		//				t.onNext(i++);
		//			}
		//		});
		//
		//		Observable<Double> myObs2 = Observable.create(t -> {
		//			int i = 0;
		//			while(true) {
		//				t.onNext(i++);
		//			}
		//		});

	}

	private static void zippingObservables() {
		Observable<String> myObs1 = Observable.create(t -> {
			t.onNext("One");
			t.onNext("Two");
			t.onNext("Three");
			t.onNext("Four");
			t.onNext("Five");
		});

		Observable<Integer> myObs2 = Observable.create(t -> {
			t.onNext(1);
			t.onNext(2);
			t.onNext(3);
			t.onNext(4);
			t.onCompleted();
		});
		// Note that "Five" never gets emitted!
		Observable
		.zip(myObs1.subscribeOn(Schedulers.io()), myObs2.subscribeOn(Schedulers.io()), (str ,num) -> str + " " + num.toString())
		.toBlocking()
		.forEach(str -> System.out.println(str));
	}

	private static void mergingWithErrorAndFallback() {
		/*
		 * Really nice error handling! If an error occurs, we can use the onErrorReturn to
		 * fall back to e.g. an error value
		 */
		Observable<String> myObs1 = Observable.create(t -> {
			t.onNext("1");
			sleep(150);
			t.onNext("2");
			sleep(150);
			t.onNext("3a");
			sleep(150);
			t.onNext("4");
		});
		Observable<Integer> mapped = myObs1.map(s -> Integer.parseInt(s)).onErrorReturn(e -> 0);

		Observable<Integer> myObs2 = Observable.create(t -> {
			sleep(30);
			t.onNext(500);
			sleep(90);
			t.onNext(600);
			sleep(90);
			t.onNext(700);
			sleep(90);
			t.onNext(800);
			sleep(90);
			t.onNext(900);
			sleep(90);
			t.onCompleted();
		});

		// Note the "0" in the print out - I.e. the default error value.
		Observable
		.merge(mapped.subscribeOn(Schedulers.io()), myObs2.subscribeOn(Schedulers.io()))
		.toBlocking()
		.forEach(s -> System.out.println(s + NEWLINE));
	}

	private static void mergingObservablesAsync(boolean mergeAsync) {
		/*
		 * Combining two observables sync/async
		 */
		Observable<String> myObs1 = Observable.create(t -> {
			t.onNext("1");
			sleep(150);
			t.onNext("2");
			sleep(150);
			t.onNext("3");
			sleep(150);
			t.onNext("4");
			sleep(150);
			t.onNext("5");
			sleep(150);
			t.onCompleted();
		});

		Observable<String> myObs2 = Observable.create(t -> {
			sleep(30);
			t.onNext("A");
			sleep(90);
			t.onNext("B");
			sleep(90);
			t.onNext("C");
			sleep(90);
			t.onNext("D");
			sleep(90);
			t.onNext("E");
			sleep(90);
			t.onCompleted();
		});

		/*
		 * I.e. two streams:
		 *
		 * Time ---->
		 * 1-------2-------3-------4-------5-------|
		 * -A---B----C----D----E |
		 * Result in the order
		 * 1 A B 2 C D 3 E 4 5
		 *
		 */

		// Writes out 1 A B 2 C D 3 E 4 5
		if(mergeAsync) {
			Observable
			// Note the Schedulers.io() calls to parallellize and perform these tasks asynchronously!
			.merge(myObs1.subscribeOn(Schedulers.io()), myObs2.subscribeOn(Schedulers.io()))
			.reduce((s1, s2) -> s1.concat(" ").concat(s2))
			.toBlocking()
			.forEach(s -> System.out.println(s + NEWLINE));
		} else {
			// This is synchronous, i.e. none of the observables will run in parallell. So the merge will first
			// get all values it can from the first, then the second. So this prints 1 2 3 4 5 A B C D E.
			// I.e. it is sequential

			Observable
			.merge(myObs1, myObs2) // This will run on the main thread
			.reduce((s1, s2) -> s1.concat(" ").concat(s2))
			.forEach(s -> System.out.println(s + NEWLINE));
		}
	}

	private static void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (Exception e) {
		}
	}

	private static void observableFromInfiniteStream() {
		/*
		 * Creating an observable of an infinite stream with a certain delay
		 */
		Observable<Stream<String>> myObservable2 = Observable.create(t -> {
			t.onNext(EventProducer.produceStringsWithDelay(100));
			t.onCompleted();
		});

		Action1<? super Stream<String>> onNextAction = strm -> strm.limit(20).forEach(s -> System.out.println(s));
		Action1<Throwable> onErrorAction = ex -> System.out.println(ex);
		Action0 onCompletedAction = () -> System.out.println("Done!");
		myObservable2.subscribe(onNextAction, onErrorAction, onCompletedAction);
	}

	private static void elegantHelloWorld() {
		/*
		 * Shorter, more elegant & declarative, Hello world
		 */
		Observable<String> myObservable = Observable.just("Hello World!");
		myObservable.subscribe(s -> System.out.println(s + " v2"));
	}

	private static void helloWorld() {
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
	}
}

