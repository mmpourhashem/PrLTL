package pltl.trio;

import pltl.bool.Formula;

public class Past implements Formula {

    private Formula f;
    private int t;

    public Past(Formula f, int t) {
        this.f = f;
        this.t = t;
    }

    public Formula getFormula(){ 
    	return f;
    }
    
    public int getInt(){ 
    	return t;
    }
    
    @Override
    public String toString() {
        return "(past " + f + " " + t + ")";
    }
}
