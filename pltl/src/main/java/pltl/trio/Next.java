package pltl.trio;

import pltl.PltlFormula;
import pltl.Smt2Formula;
import pltl.bool.BooleanFormulae;

public class Next implements BooleanFormulae{

    private BooleanFormulae f;

    public Next(BooleanFormulae f) {
        this.f = f;
    }

    public BooleanFormulae getFormula(){ 
    	return f;
    }
    
    public String getSemantics() {
		String s = ";" + this.toString() + "\n";
		int mainF = PltlFormula.add(this);
		int innerF = PltlFormula.add(f);
		for (int time = 0; time < PltlFormula.bound; time++){
			s += Smt2Formula.getOp("=" , Smt2Formula.getzot(time, mainF), Smt2Formula.getzot(time + 1, innerF)) + "\n";//prop
			s += Smt2Formula.getOp("=" , Smt2Formula.getzotp(time, mainF), Smt2Formula.getzotp(time + 1, innerF)) + "\n";//prob
		}
		return s;
	}


	@Override
    public String toString() {
        return "(next " + f + ")";     
    }
	
	@Override
	public boolean equals(Object o){
		if (o instanceof Next){
			return f.equals(((Next) o).getFormula());
		}
		
		return false;
	}
}
