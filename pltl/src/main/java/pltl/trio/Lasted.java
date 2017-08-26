package pltl.trio;

import pltl.bool.And;
import pltl.bool.Formula;

public class Lasted implements Formula{

    Formula f;
    int window;

    public Lasted(Formula f, int t) {
        this.f = f;
        this.window = t;
    }

    public Formula getFormula() {
    	return f;
    }
    
    public int getInt() {
    	return window;
    }
    
    @Override
    public String toString() {
        return "(lasted " + f + " " + window + ")";
    }

	public Formula get(int offset) {
		And and = new And();
		for (int time = 1; time <= window; time++)
			and.addFormula(f.get(offset - time));
		return and;
	}

}
