package com.concurrency;

import java.util.concurrent.*;
import static net.mindview.util.Print.*;

public class EvenChecker implements Runnable {
	private IntGenerator generator;
	private final int id;

	public EvenChecker(IntGenerator g, int ident) {
		generator = g;
		id = ident;
		System.out.println("generator Canceled ? " + g.isCanceled());
	}

	public void run() {
		while (!generator.isCanceled()) {
			int val = generator.next();
			//print(Thread.currentThread() + " " + this);
			System.out.println("val " + val);
			if (val % 2 != 0) {
				print(Thread.currentThread() + " " + this);
				System.out.println(val + " not even!" + " id " + id);
				generator.cancel(); // Cancels all EvenCheckers
			}
		}
	}

	// Test any type of IntGenerator:
	public static void test(IntGenerator gp, int count) {
		System.out.println("Press Control-C to exit");
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < count; i++)
			exec.execute(new EvenChecker(gp, i));
		exec.shutdown();
	}

	// Default value for count:
	public static void test(IntGenerator gp) {
		test(gp, 5);
	}
}