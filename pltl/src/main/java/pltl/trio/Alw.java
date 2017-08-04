package pltl.trio;

import pltl.bool.Formula;

public class Alw implements Formula{

    public Formula f;

    public Alw(Formula f) {
        this.f = f;
    }

    public Formula getFormula(){
		return f;
	}
    
    @Override
    public String toString() {
        return "(alw " + f + ")";
    }
// TODO Check if equals function (similar to one in And and Not) is required for Alw and the rest of temporal operators.

	public boolean equals(Object o) {
		if (o instanceof Alw)
			return ((Alw) o).f.equals(((Alw) o).f);
		return false;
	}

}
