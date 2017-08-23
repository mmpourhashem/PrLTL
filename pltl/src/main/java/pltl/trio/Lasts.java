package pltl.trio;

import pltl.bool.Formula;

public class Lasts implements Formula{

    Formula f;
    int t;
    
    public Lasts(Formula f, int t) {
    	this.f = f;
    	this.t = t;
    }

    public Formula getFormula() {
    	return f;
    }
    
    public int getInt() {
    	return t;
    }

    @Override
    public String toString() {
        return "(lasts " + f + " " + t + ")";
    }

}
