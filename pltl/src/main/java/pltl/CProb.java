package pltl;

import arith.ArithFormula;
import arith.Constant;
import arith.Op;
import pltl.bool.And;
import pltl.bool.Formula;
import pltl.bool.Not;

public class CProb implements Formula {

	private Prob prob1;
	private Prob prob2;

	public CProb(Prob prob1, Prob prob2) {
		this.prob1 = prob1;
		this.prob2 = prob2;
	}

	public String getSemantics() {
		String s = "";
		s += new ArithFormula(false, Op.GTE, this, new Constant(0)).toString() + " " + new ArithFormula(false, Op.LTE, this, new Constant(1)).toString() + "\n";
		if ((prob1.hasParent(prob2) && prob1.getParents().size() == 1) || (prob2.hasParent(prob1) && prob2.getParents().size() == 1)) // If prob2 is the single parent of prob1 or vice versa, there is nothing new to add.  
			return s;
		if (prob1.hasParent(prob2) && prob1.getParents().size() > 1) {// P(prob1|prob2) = P(prob1&prob2)*P(prob2)
			s += new ArithFormula(Op.EQ, this, new ArithFormula(Op.MUL, new And(prob1, prob2).getProb(), prob2));
		}

		//FIXME Produce (a|anyParentsCombination) + (!a|anyParentsCombination) = 1.0,  
		return s;
	}

	@Override
	public String toString() {
		return "(zot-cp " + prob1.toSeqString() + " " + prob2.toSeqString() + ")";
	}

	@Override
	public boolean equals(Object o){
		if (o instanceof CProb){
			CProb c = (CProb) o;
			return (c.prob1.equals(prob1) && c.prob2.equals(prob2));
		}
		
		return false;
	}
}

