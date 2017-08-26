package pltl.trio;

import pltl.PltlFormula;
import pltl.bool.Formula;
import pltl.bool.Or;

public class SomF implements Formula {

    Formula f;

    public SomF(Formula f) {
        this.f = f;
    }
    
    public Formula getFormula() {
    	return f;
    }

    public Formula get(int offset) {
    	Or or = new Or();
    	for (int time = offset; time <= PltlFormula.bound; time++) {
    		or.addFormula(f.get(time));
    	}
    	
    	return or;
    }
    
    @Override
    public String toString() {
        return "(somf " + f + ")";
    }

    //TODO: implements equals for all operators.
    @Override
	public boolean equals(Object o) {
		if (o instanceof SomF)
			return f.equals(((SomF) o).getFormula());
		return false;
	}
}
