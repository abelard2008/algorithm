package com.concurrency;

import java.util.concurrent.*;

import static net.mindview.util.Print.*;

public class DaemonFromFactory implements Runnable {
	public void run() {
		try {
			
			while (true) {
				TimeUnit.MILLISECONDS.sleep(500);
				print(Thread.currentThread() + " " + this);
			}
		} catch (InterruptedException e) {
			print("Interrupted");
		}
	}

	public static void main(String[] args) throws Exception {
		ExecutorService exec = Executors
				.newCachedThreadPool(new DaemonThreadFactory());
		for (int i = 0; i < 10; i++)
			exec.execute(new DaemonFromFactory());
		print("All daemons started");
		TimeUnit.MILLISECONDS.sleep(500); // Run for a while
	}
}