package pltl.trio;

import pltl.bool.Formula;

public class Yesterday implements Formula {

	Formula f;

	public Yesterday(Formula f) {
		this.f = f;
	}

	public Formula getFormula(){ 
		return f;
	}
	
	public Formula get(int offset) {
    	return f.get(offset - 1);
    }

	@Override
	public String toString() {
		return "(yesterday " + f + ")";     
	}

	@Override
	public boolean equals(Object o){
		if (o instanceof Yesterday){
			return f.equals(((Yesterday) o).getFormula());
		}

		return false;
	}

}
