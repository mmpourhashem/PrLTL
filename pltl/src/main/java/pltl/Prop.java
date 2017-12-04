/**
 * @author Mohammad Mehdi Pourhashem Kallehbasti
 */

package pltl;

import pltl.bool.Formula;
import pltl.trio.Predicate;

public class Prop implements Formula {
	private int time, index, depth = -1;

	public Prop(int time, int index) {
		this.time = time;
		this.index = index;
	}

	public int getTime() {
		return time;
	}

	public int getIndex() {
		return index;
	}

	public Formula getPlainFormula() {
		return PltlFormula.get(index); 
	}
	
	public Formula get(int offset) {
		return null;
	}
	
	public Formula getProp(int offset) {
		return null;
	}

	@Override
	public String toString() {
		return "(zot " + time + " " + index + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Prop && ((Prop) o).getTime() == time && ((Prop) o).getIndex() == index)
			return true;
		return false;
	}
}
