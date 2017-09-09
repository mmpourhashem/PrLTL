package pltl.trio;

import arith.ArithFormula;
import arith.Constant;
import arith.Op;
import pltl.PltlFormula;
import pltl.Prob;
import pltl.Smt2Formula;
import pltl.bool.Formula;

public class Dist implements Formula {

    private Formula formula;
    private int offset;
    public Dist(Formula formula, int offset) {
        this.formula = formula;
        this.offset = offset;
    }

    public Formula getFormula() { 
    	return formula;
    }
    
    public int getOffset() { 
    	return offset;
    }
    
    public String getSemantics() {
		String s = ";" + this.toString() + "\n";
		int mainF = PltlFormula.add(this);
		int innerF = PltlFormula.add(formula);
		for (int time = 0; time <= PltlFormula.bound; time++)
			if (time + offset >= 0) {
//				s += Smt2Formula.getOp("=" , Smt2Formula.getzot(time, mainF), Smt2Formula.getzot(time + offset, innerF)) + "\n";//prop
				s += Smt2Formula.getOp("=" , Smt2Formula.getzot(time, mainF), PltlFormula.get(innerF).getProp(time + offset).toString()) + "\n";//prop
				s += new ArithFormula(Op.EQ, new Prob(time, mainF), new Prob(time + offset, innerF)) + "\n";//prob
			}
			else {
				s += Smt2Formula.getOp("not", Smt2Formula.getzot(time, mainF)) + "\n";//prop
				s += new ArithFormula(Op.EQ, new Prob(time, mainF), new Constant((float) 0)) + "\n";//prob
			}
		
		return s;
	}
    
    public Formula get(int offset) {
    	return new Dist(formula, this.offset + offset);//TODO Is it possible to convert all formula to Probs here?
    }
    
    public Formula getProp(int offset) {
    	if (PltlFormula.outOfBound(this.offset) || PltlFormula.outOfBound(this.offset + offset))
    		return new PltlFormula.PropFalse();
    	
    	return formula.getProp(this.offset + offset);
    }
    
    @Override
    public String toString() {
        return "(dist " + formula + " " + offset + ")";
    }
    
    @Override
	public boolean equals(Object o) {
		if (o instanceof Dist)
			return (formula.equals(((Dist) o).getFormula()) && offset == ((Dist) o).getOffset());
		
		return false;
	}
    
}
