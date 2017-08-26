package pltl.trio;

import pltl.PltlFormula;
import pltl.bool.And;
import pltl.bool.Formula;
import pltl.bool.Or;

public class Until implements Formula {

	private Formula f1;
	private Formula f2;

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
	
	public Formula get(int offset) {
		Or or = new Or();//TODO check offset > bound
		or.addFormula(f2.get(offset));
		for (int time = offset + 1; time <= PltlFormula.bound; time++) {
			And and = new And();
			for (int a = offset; a < time; a++)
				and.addFormula(f1.get(a));
			and.addFormula(f2.get(time));
			or.addFormula(and);
		}
		
		return or;
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
