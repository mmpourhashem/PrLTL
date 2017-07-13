package pltl.trio;

import pltl.bool.BooleanFormulae;

public class Alw implements BooleanFormulae{

    public BooleanFormulae f;

    public Alw(BooleanFormulae f) {
        this.f = f;
    }

    public BooleanFormulae getFormula1(){
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
