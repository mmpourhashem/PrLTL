package pltl.trio;

import pltl.bool.Formula;

public class Futr implements Formula {

    private Formula f;
    private int offset;

    public Futr(Formula f, int offset) {
        this.f = f;
        this.offset = offset;
    }

    public Formula getFormula() { 
    	return f;
    }
    
    public int getOffset() { 
    	return offset;
    }
    
    public Formula get(int offset) {
    	return f.get(offset + this.offset);
    }
    
    @Override
    public String toString() {
        return "(futr " + f + " " + offset + ")";
    }
    
    @Override
	public boolean equals(Object o) {
		if (o instanceof Futr)
			return (f.equals(((Futr) o).getFormula()) && offset == ((Futr) o).getOffset());
		
		return false;
	}
    
}
