package pltl.trio;

import pltl.DepTempFormula;
import pltl.PltlFormula;
import pltl.Prob;
import pltl.bool.Formula;

public class Yesterday implements Formula, DepTempFormula{

	Formula f;

	public Yesterday(Formula f) {
		this.f = f;
	}

	public Formula getFormula(){ 
		return f;
	}

	public Prob getProbForDep(int offset, boolean neg) {
    	return ((DepTempFormula) f).getProbForDep(offset - 1, neg);
    }
	
	public Formula get(int offset) {
		if (PltlFormula.outOfBound(offset) || PltlFormula.outOfBound(offset - 1))
			return new PltlFormula.False();
		
		return f.get(offset - 1);
	}

	public Formula getProp(int offset) {
		if (PltlFormula.outOfBound(offset) || PltlFormula.outOfBound(offset - 1))
			return new PltlFormula.PropFalse();
		
		return f.getProp(offset - 1);
	}

	@Override
	public String toString() {
		return "(yesterday " + f + ")";     
	}

	@Override
	public boolean equals(Object o){
		if (o instanceof Yesterday){
			return f.equals(((Yesterday) o).getFormula());
		}

		return false;
	}

}
