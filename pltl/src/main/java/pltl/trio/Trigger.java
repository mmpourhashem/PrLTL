package pltl.trio;

import pltl.PltlFormula;
import pltl.bool.And;
import pltl.bool.Formula;
import pltl.bool.Or;
import pltl.bool.PropAnd;
import pltl.bool.PropOr;

public class Trigger implements Formula {

	private Formula f1;
	private Formula f2;

    public Trigger(Formula f1, Formula f2) {
        this.f1 = f1;
        this.f2 = f2;
    }

    public Formula getFormula2() {
    	return f2;
    }
    
    public Formula getFormula1() {
    	return f1;
    }
    
    public Formula get(int offset) {
    	if (PltlFormula.outOfBound(offset))
			return new PltlFormula.False();
    	
    	if (offset == 0)
    		return f2.get(0);
    	
    	return new And(f2.get(offset), new Or(f1.get(offset), this.get(offset - 1))); 
    }

    public Formula getProp(int offset) {
    	if (PltlFormula.outOfBound(offset))
			return new PltlFormula.PropFalse();
    	
    	if (offset == 0)
    		return f2.getProp(0);
    	
    	return new PropAnd(f2.getProp(offset), new PropOr(f1.getProp(offset), this.getProp(offset - 1))); 
    }

    @Override
    public String toString() {
        return "(trigger " + f1 + " " + f2 + ")";
    }
    
    @Override
	public boolean equals(Object o){
		if (o instanceof Trigger)
			return f1.equals(((Trigger) o).getFormula1()) && f2.equals(((Trigger) o).getFormula2());
		
		return false;
	}
}