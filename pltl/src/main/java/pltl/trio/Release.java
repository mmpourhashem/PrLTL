package pltl.trio;

import pltl.bool.Formula;

public class Release implements Formula {

    Formula f1;
    Formula f2;

    public Release(Formula f1, Formula f2) {
        this.f1 = f1;
        this.f2 = f2;
    }

	public Formula getFormula1() {
		return f1;
	}
	
	public Formula getFormula2() {
		return f2;
	}
	
	@Override
	public String toString() {
		return "(release " + f1 + " " + f2 + ")";
	}
	
	@Override
	public boolean equals(Object o){
		if (o instanceof Release)
			return f1.equals(((Release) o).getFormula1()) && f2.equals(((Release) o).getFormula2());
		
		return false;
	}
}