package pltl.trio;

import pltl.PltlFormula;
import pltl.bool.Or;
import pltl.bool.Formula;

public class Som implements Formula {

    Formula f;

    public Som(Formula f) {
        this.f = f;
    }
    
    public Formula getFormula() {
    	return f;
    }
    
    public Formula get(int offset) {
    	Or or = new Or();
    	for (int time = 0; time <= PltlFormula.bound; time++)
    		or.addFormula(f.get(time));
    	return or;
    }

    @Override
    public String toString() {
        return "(som " + f + ")";
    }
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Som)
			return f.equals(((Som) o).getFormula());
		return false;
	}
	
}
