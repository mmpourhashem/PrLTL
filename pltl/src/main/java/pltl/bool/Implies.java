package pltl.bool;

import pltl.PltlFormula;

public class Implies implements Formula{

    private Formula f1;
    private Formula f2;

    public Implies(Formula f1, Formula f2){
        this.f1 = f1;
        this.f2 = f2;
    }
    
    public Formula getFormula1() {
    	return f1;
    }
    
    public Formula getFormula2() {
    	return f2;
    }
    
    public Formula get(int offset) {
    	if (PltlFormula.outOfBound(offset))
			return new PltlFormula.False();
		
    	return new Implies(f1.get(offset), f2.get(offset));
    }
    
    public Formula getProp(int offset) {
    	if (PltlFormula.outOfBound(offset))
    		return new PltlFormula.PropFalse();
		
    	PropOr pOr = new PropOr();
    	pOr.addFormula(new PropNot(f1).getProp(offset));
    	pOr.addFormula(f2.getProp(offset));

    	return pOr;
    }
    
    @Override
    public String toString(){
        return "(-> " + f1 + " " + f2 + ")";
    }

    @Override
	public boolean equals(Object o){
		if (o instanceof Implies){
			Implies implO = (Implies) o;
			return (f1.equals(implO.getFormula1()) && f2.equals(implO.getFormula2()));
		}
		
		return false;
	}
    
}
