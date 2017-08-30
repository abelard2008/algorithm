package com.generics;
import static net.mindview.util.Print.*;
public abstract class Instrument {
	private int i; // Storage allocated for each
	public abstract void play(int n);
	public String what() { return "Instrument"; }
	public abstract void adjust();
}
abstract class Wind extends Instrument {
	public void play(int n) {
	print("Wind.play() " + n);
	}
	public String whatx() { return "Wind"; }
	public void adjustx() {}
	}