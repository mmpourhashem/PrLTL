package pltl.bool;

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
    	return new Implies(f1.get(offset), f2.get(offset));
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
