package pltl.trio;

import pltl.bool.Formula;

public class WithinF implements Formula{

	Formula f;
	int t;
	
	public WithinF(Formula f, int t) {
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
       return "(withinf " + f + " " + t + ")";
    }

	public Formula get(int offset) {
		// TODO Auto-generated method stub
		return null;
	}
}

