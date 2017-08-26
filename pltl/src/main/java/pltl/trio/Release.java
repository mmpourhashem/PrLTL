package pltl.trio;

import pltl.PltlFormula;
import pltl.bool.And;
import pltl.bool.Formula;
import pltl.bool.Or;

public class Release implements Formula {

	private Formula f1;
	private Formula f2;

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
	
	public Formula get(int offset) {
		Or or = new Or();
		or.addFormula(new And(f1.get(offset), f2.get(offset)));
		for (int time = offset + 1; time <= PltlFormula.bound; time++) {
			And and = new And();
			for (int b = offset; b <= time; b++)
				and.addFormula(f2.get(b));
			and.addFormula(f1.get(time));
			or.addFormula(and);
		}
		
		return or;
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