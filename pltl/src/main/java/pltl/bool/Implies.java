package pltl.bool;

public class Implies implements BooleanFormulae{

    BooleanFormulae f1;
    BooleanFormulae f2;

    public Implies(BooleanFormulae f1, BooleanFormulae f2){
        this.f1=f1;
        this.f2=f2;
    }
    
    @Override
    public String toString(){
        return "(-> " + f1 + " " + f2 + ")";
    }

    @Override
	public boolean equals(Object o){
		if (o instanceof Implies){
			Implies implO = (Implies) o;
			return (f1.equals(implO.f1) && f2.equals(implO.f2));
		}
		return false;
	}
}
