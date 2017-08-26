package pltl.trio;

import pltl.bool.Formula;

public class Past implements Formula {

    private Formula f;
    private int offset;

    public Past(Formula f, int offset) {
        this.f = f;
        this.offset = offset;
    }

    public Formula getFormula() { 
    	return f;
    }
    
    public int getOffset() { 
    	return offset;
    }
    
    @Override
    public String toString() {
        return "(past " + f + " " + offset + ")";
    }
    
    @Override
	public boolean equals(Object o) {
		if (o instanceof Past)
			return (f.equals(((Past) o).getFormula()) && offset == ((Past) o).getOffset());
		
		return false;
	}

	public Formula get(int offset) {
		return f.get(offset - this.offset);
	}
    
}
