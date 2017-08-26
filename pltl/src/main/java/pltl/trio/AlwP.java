package pltl.trio;

import pltl.bool.And;
import pltl.bool.Formula;

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
		for (int time = 0; time <= offset; time++) {
			and.addFormula(f.get(time));
		}
		
		return and;
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
