package pltl.trio;

import pltl.bool.And;
import pltl.bool.Formula;
import pltl.bool.Or;

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
		for (int time = 0; time <= offset; time++) {
			or.addFormula(f.get(time));
		}
		
		return or;
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
