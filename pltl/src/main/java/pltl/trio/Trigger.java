package pltl.trio;

import pltl.bool.Formula;

public class Trigger implements Formula {

    Formula f1;
    Formula f2;

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