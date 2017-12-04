/**
 * @author Mohammad Mehdi Pourhashem Kallehbasti
 */

package pltl.bool;

import pltl.PltlFormula;

public class PropNot implements Formula{
	private Formula f;

	public PropNot(Formula f) {
		this.f = f;
	}

	public Formula getFormula(){
		return f;
	}

	public Formula get(int offset) {
		//TODO
		return null;
	}
	
	public Formula getProp(int offset) {
		if (PltlFormula.outOfBound(offset))
    		return new PltlFormula.PropFalse();
		
		return new PropNot(f.getProp(offset));
	}

	@Override
	public String toString() {
		return "(not " + f.toString() + ")";
	}

	@Override
	public boolean equals(Object o){
		if (o instanceof PropNot)
			return f.equals(((PropNot) o).getFormula());

		return false;
	}
}
