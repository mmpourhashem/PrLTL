package pltl.trio;

import pltl.PltlFormula;
import pltl.bool.And;
import pltl.bool.Formula;
import pltl.bool.PropAnd;

public class Alw implements Formula{

    public Formula f;

    public Alw(Formula f) {
        this.f = f;
    }

    public Formula getFormula(){
		return f;
	}

    public Formula get(int offset) {
    	if (PltlFormula.outOfBound(offset))
			return new PltlFormula.False();
    	
    	And and = new And();
    	for (int time = 0; time <= PltlFormula.bound; time++)
    		and.addFormula(f.get(time));
    	return and;
    }
    
    public Formula getProp(int offset) {
    	if (PltlFormula.outOfBound(offset))
    		return new PltlFormula.PropFalse();
    	
    	PropAnd pAnd = new PropAnd();
    	for (int time = 0; time <= PltlFormula.bound; time++)
    		pAnd.addFormula(f.getProp(time));
    	
    	return pAnd;
    }
    
    @Override
    public String toString() {
        return "(alw " + f + ")";
    }

    @Override
	public boolean equals(Object o) {
		if (o instanceof Alw)
			return f.equals(((Alw) o).getFormula());
		return false;
	}

}
