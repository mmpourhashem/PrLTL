package pltl.trio;

import pltl.bool.And;
import pltl.bool.Formula;

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
