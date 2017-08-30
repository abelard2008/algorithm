package com.generics;

import java.util.Arrays;
import java.util.List;

	public enum State {
	    DISABLED, PRIMORDIAL, INITIALIZED, LOADED, STARTED, STOPPED, PAUSED;
		public static List<State> list() {
			return Arrays.asList(State.values());
		}
}
