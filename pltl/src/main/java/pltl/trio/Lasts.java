package pltl.trio;

import pltl.PltlFormula;
import pltl.bool.And;
import pltl.bool.Formula;
import pltl.bool.PropAnd;

public class Lasts implements Formula{

    Formula f;
    int window;
    
    public Lasts(Formula f, int t) {
    	this.f = f;
    	this.window = t;
    }

    public Formula getFormula() {
    	return f;
    }
    
    public int getWindow() {
    	return window;
    }
    
    public Formula get(int offset) {
    	And and = new And();
    	for (int time = 1; time <= window; time++)
    		and.addFormula(f.get(offset + time));
    	return and;
    }

    public Formula getProp(int offset) {
    	if (PltlFormula.outOfBound(offset))
    		return new PltlFormula.PropFalse();

    	PropAnd pAnd = new PropAnd();
    	for (int time = 1; time <= window; time++)
    		pAnd.addFormula(f.getProp(offset + time));
    	
    	return pAnd;
    }
    
    @Override
    public String toString() {
        return "(lasts " + f + " " + window + ")";
    }
    
	@Override
	public boolean equals(Object o) {
		if (o instanceof Lasts)
			return (f.equals(((Lasts) o).getFormula()) && window == ((Lasts) o).getWindow());
		return false;
	}

}
