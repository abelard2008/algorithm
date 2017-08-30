package com.concurrency;

import java.util.concurrent.*;

public class SleepingTask extends LiftOff {
	public void run() {
		try {
			while (CountDown-- > 0) {
				System.out.print(status());
				// Old-style:
				// Thread.sleep(100);
				// Java SE5/6-style:
				TimeUnit.MILLISECONDS.sleep(100);
			}
		} catch (InterruptedException e) {
			System.err.println("Interrupted");
		}
	}

	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 5; i++)
			exec.execute(new SleepingTask());
		exec.shutdown();
	}
}
