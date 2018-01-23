/**
 * @author Mohammad Mehdi Pourhashem Kallehbasti
 */

package pltl.trio;

import pltl.DepTempFormula;
import pltl.PltlFormula;
import pltl.Prob;
import pltl.bool.Formula;

public class Next implements Formula, DepTempFormula{

    private Formula f;

    public Next(Formula f) {
        this.f = f;
    }

    public Formula getFormula(){ 
    	return f;
    }
    
    public Prob getProbForDep(int offset, boolean neg) {
    	return ((DepTempFormula) f).getProbForDep(offset + 1, neg);
    }
    
    public Formula get(int offset) {
    	if (PltlFormula.outOfBound(offset) || PltlFormula.outOfBound(offset + 1))
    		return new PltlFormula.False();

    	return f.get(offset + 1);
    }
    
    public Formula getProp(int offset) {
    	if (PltlFormula.outOfBound(offset) || PltlFormula.outOfBound(offset + 1))
    		return new PltlFormula.PropFalse();
    	
    	return f.getProp(offset + 1);
    }
    
	@Override
    public String toString() {
        return "(next " + f + ")";     
    }
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Next)
			return f.equals(((Next) o).getFormula());
		
		return false;
	}

}
