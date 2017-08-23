package pltl.trio;

import pltl.bool.Formula;

public class Next implements Formula{

    private Formula f;

    public Next(Formula f) {
        this.f = f;
    }

    public Formula getFormula(){ 
    	return f;
    }
    
	@Override
    public String toString() {
        return "(next " + f + ")";     
    }
	
	@Override
	public boolean equals(Object o){
		if (o instanceof Next)
			return f.equals(((Next) o).getFormula());
		
		return false;
	}
}
