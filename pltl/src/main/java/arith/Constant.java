package arith;

import pltl.bool.Formula;

public class Constant implements Formula{

	public float value;

	public Constant(float d) {
		this.value = d;
	}

	@Override
	public String toString() {
		return Float.toString(value);
	}

	public Formula get(int offset) {
		return null;// Not required.
	}

	public Formula getProp(int offset) {
		return null;// Not required.
	}
	
}
