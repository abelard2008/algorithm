package com.concurrency;

import java.util.concurrent.*;
public class SerialNumberGenerator {
	private static volatile int serialNumber = 0;

	public static  int nextSerialNumber() {
		return serialNumber++; // Not thread-safe
	}
}