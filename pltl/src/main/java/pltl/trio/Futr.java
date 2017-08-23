package pltl.trio;

import pltl.bool.Formula;

public class Futr implements Formula {

    private Formula f;
    private int t;

    public Futr(Formula f, int t) {
        this.f = f;
        this.t = t;
    }

    public Formula getFormula() { 
    	return f;
    }
    
    public int getInt() { 
    	return t;
    }
    
    @Override
    public String toString() {
        return "(futr " + f + " " + t + ")";
    }
    
    @Override
	public boolean equals(Object o) {
		if (o instanceof Futr)
			return (f.equals(((Futr) o).getFormula()) && t == ((Futr) o).getInt());
		
		return false;
	}
    
}
