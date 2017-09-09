package pltl.trio;

import pltl.PltlFormula;
import pltl.bool.Formula;
import pltl.bool.Or;
import pltl.bool.PropOr;

public class SomP implements Formula {

    Formula f;

    public SomP(Formula f) {
        this.f = f;
    }
    
    public Formula getFormula() {
    	return f;
    }
    
    public Formula get(int offset) {
		Or or = new Or();
		for (int time = offset; time >= 0; time--) {
			or.addFormula(f.get(time));
		}
		
		return or;
	}
    
    public Formula getProp(int offset) {
    	if (PltlFormula.outOfBound(offset))
    		return new PltlFormula.PropFalse();
    	
    	PropOr pOr = new PropOr();
    	for (int time = offset; time >= 0; time--)
    		pOr.addFormula(f.getProp(time));
    	
    	return pOr;
    }
    
    @Override
    public String toString() {
        return "(somp " + f + ")";
    }
    
    @Override
	public boolean equals(Object o) {
		if (o instanceof SomP)
			return f.equals(((SomP) o).getFormula());
		return false;
	}

}
