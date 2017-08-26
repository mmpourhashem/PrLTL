package pltl.trio;

import pltl.PltlFormula;
import pltl.bool.And;
import pltl.bool.Formula;

public class AlwF implements Formula {

	Formula f;

	public AlwF(Formula f) {
		this.f = f;
	}

	public Formula getFormula() {
		return f;
	}

	public Formula get(int offset) {
		And and = new And();
		for (int time = offset; time <= PltlFormula.bound; time++) {
			and.addFormula(f.get(time));
		}
		
		return and;
	}

	@Override
	public String toString() {
		return "(alwf " + f + ")";
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof AlwF)
			return f.equals(((AlwF) o).getFormula());
		return false;
	}

}
