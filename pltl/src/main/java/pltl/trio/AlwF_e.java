package pltl.trio;

import pltl.PltlFormula;
import pltl.bool.Formula;
import pltl.bool.PropAnd;

public class AlwF_e implements Formula {

    Formula f;

    public AlwF_e(Formula f) {
        this.f = f;
    }

    @Override
    public String toString() {
        return "(alwf_e " + f + ")";
    }

	public Formula get(int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	public Formula getProp(int offset) {
    	if (PltlFormula.outOfBound(offset) || offset == PltlFormula.bound)
    		return new PltlFormula.PropFalse();
    	
		PropAnd pAnd = new PropAnd();
		for (int time = offset + 1; time <= PltlFormula.bound; time++)
			pAnd.addFormula(f.getProp(time));
		
		return pAnd;
	}
}
