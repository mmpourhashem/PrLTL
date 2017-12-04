/**
 * @author Mohammad Mehdi Pourhashem Kallehbasti
 */

package pltl.bool;

import pltl.PltlFormula;

public class PropIff implements Formula{

	private Formula f1;
	private Formula f2;

	public PropIff(Formula f1, Formula f2){
		this.f1 = f1;
		this.f2 = f2;
	}
	
	public Formula getFormula1() {
		return f1;
	}
	
	public Formula getFormula2() {
		return f2;
	}
	
	public Formula get(int offset) {
		return null;// Not required.
	}

	@Override
	public String toString(){
		return "(= " + f1 + " " + f2 + ")";
	}

	@Override
	public boolean equals(Object o){
		if (o instanceof PropIff){
			PropIff iffO = (PropIff) o;
			return ((f1.equals(iffO.getFormula1()) && f2.equals(iffO.getFormula2())) || (f2.equals(iffO.getFormula1()) && f1.equals(iffO.getFormula2())));
		}
		
		return false;
	}

	public Formula getProp(int offset) {
		if (PltlFormula.outOfBound(offset))
    		return new PltlFormula.PropFalse();
		
		return new PropIff(f1.getProp(offset), f2.getProp(offset));
	}

}
