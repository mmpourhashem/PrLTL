package pltl.trio;

import pltl.PltlFormula;
import pltl.bool.And;
import pltl.bool.Formula;
import pltl.bool.PropAnd;

public class AlwP implements Formula {

    Formula f;

    public AlwP(Formula f) {
        this.f = f;
    }
    
    public Formula getFormula() {
    	return f;
    }
    
    public Formula get(int offset) {
		And and = new And();
		for (int time = offset; time >= 0; time--) {
			and.addFormula(f.get(time));
		}
		
		return and;
	}
    
    public Formula getProp(int offset) {
    	if (PltlFormula.outOfBound(offset))
    		return new PltlFormula.PropFalse();
    	
    	PropAnd pAnd = new PropAnd();
    	for (int time = offset; time >= 0; time--)
    		pAnd.addFormula(f.getProp(time));
    	
    	return pAnd;
    }
    
    @Override
    public String toString() {
        return "(alwp " + f + ")";
    }
    
    @Override
	public boolean equals(Object o) {
		if (o instanceof AlwP)
			return f.equals(((AlwP) o).getFormula());
		return false;
	}
    
}
