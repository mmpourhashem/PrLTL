package pltl.trio;

import pltl.PltlFormula;
import pltl.bool.And;
import pltl.bool.Formula;

public class Alw implements Formula{

    public Formula f;

    public Alw(Formula f) {
        this.f = f;
    }

    public Formula getFormula(){
		return f;
	}

    public Formula get(int offset) {
    	And and = new And();
    	for (int time = 0; time <= PltlFormula.bound; time++)
    		and.addFormula(f.get(time));
    	return and;
    }
    
    @Override
    public String toString() {
        return "(alw " + f + ")";
    }
// TODO Check if equals function (similar to one in And and Not) is required for Alw and the rest of temporal operators.

    @Override
	public boolean equals(Object o) {
		if (o instanceof Alw)
			return f.equals(((Alw) o).getFormula());
		return false;
	}

}
