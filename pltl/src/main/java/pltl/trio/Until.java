package pltl.trio;

import pltl.bool.Formula;

public class Until implements Formula {

    public Formula f1;
    public Formula f2;

    public Until(Formula f1, Formula f2) {
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
        return "(until " + f1 + " " + f2 + ")";
    }
    
	@Override
	public boolean equals(Object o){
		if (o instanceof Until)
			return f1.equals(((Until) o).getFormula1()) && f2.equals(((Until) o).getFormula2());
		
		return false;
	}
}
