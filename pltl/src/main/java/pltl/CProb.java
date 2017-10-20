package pltl;

import static org.junit.Assert.assertFalse;
import java.util.ArrayList;

import arith.ArithFormula;
import arith.Constant;
import arith.Op;
import pltl.bool.And;
import pltl.bool.Formula;
import pltl.bool.Not;
import pltl.bool.Or;

public class CProb implements Formula {

	private Prob prob1;
	private Prob prob2;

	public Prob getProb2() {
		return prob2;
	}

	public Prob getProb1() {
		return prob1;
	}

	public CProb(Prob prob1, Prob prob2) {
		this.prob1 = prob1;
		this.prob2 = prob2;
	}

	public String getSemantics() {
		String s = ";" + toString() + "\n";
		s += getComplementarySemantics(); //Adds complementary semantics if required.
		s += Smt2Formula.getRangeConstraints(this);

		//		 If the CProb is produced automatically, the right side of it must be all parents.
		//		assertTrue(prob1.allAreParents(prob2)); I should not see a conjunction of non-parents here, unless user inserted an abnormal formulae like P(fma1|fma2), where fma2 is not a parent of fma1.

		assertFalse(prob1.getPlainFormula() instanceof And || prob1.getPlainFormula() instanceof Or);
		assertFalse(prob2.getPlainFormula() instanceof Or);

		Formula formula2 = prob2.getPlainFormula();
		if (formula2 instanceof And) {
			ArrayList<Prob> prob2Probs = new ArrayList<Prob>();
			for (Formula f: ((And) formula2).getFormulae())
				prob2Probs.add(new Prob(prob2.getTime(), PltlFormula.add(f)));
			if (prob1.allAreParents(prob2Probs)) // If prob2 is a conjunction of prob1's parents, there is nothing to add.
				return s;

			// I should not see a conjunction of non-parents here, unless user inserted an abnormal formulae like P(fma1|fma2), where fma2 is not a parent of fma1.
			ArrayList<Formula> onlyParents = new ArrayList<Formula>();
			for (Prob p: prob2Probs)
				if (prob1.hasParent(p))
					onlyParents.add(p.getPlainFormula());
			Formula right = null;

			if (onlyParents.size() == 0) // If none of right formulae is a parent
				return s + new ArithFormula(Op.EQ, this, prob1).toString();
			if (onlyParents.size() == 1)
				right = onlyParents.get(0);
			else
				right = new And(onlyParents);

			return s + new ArithFormula(Op.EQ, this, new CProb(prob1, new Prob(prob2.getTime(), PltlFormula.add(right)))).toString();
		}

		//If prob1 and prob2 are unrelated and not complex formulae P(prob1|prob2) = P(prob1)
		if (! prob1.hasParent(prob2))
			return s + new ArithFormula(Op.EQ, this, prob1);
		assertFalse(prob2.hasParent(prob1)); //They must be ordered. I should only see P(child|parent) here.

		// P(fma1|fma2) + P(!fma1|fma2) = 1
		Not notprob1F = new Not(prob1.getPlainFormula());
		Prob notProb1 = new Prob(prob1.getTime(), PltlFormula.add(notprob1F));
		s += new ArithFormula(Op.EQ, new Constant((float) 1), new ArithFormula(Op.PLUS, this, new CProb(notProb1, prob2))) + "\n";

		if ((prob1.hasParent(prob2) && prob1.getParents().size() == 1) || (prob2.hasParent(prob1) && prob2.getParents().size() == 1)) // If prob2 is the single parent of prob1 or vice versa, there is nothing new to add.  
			return s;

		if (prob1.hasParent(prob2) && prob1.getParents().size() > 1) {// P(prob1|prob2) = P(prob1&prob2) * P(prob2)
			s += new ArithFormula(Op.EQ, new And(prob1, prob2).getProb(), new ArithFormula(Op.MUL, this, prob2));
		}

		return s;
	}

	/**
	 * 
	 * @return
	 * Returns complementary semantics if required.
	 * For example, if I am processing (fma1|fma2) and (!fma1|fma2) is used somewhere,
	 * it returns (fma1|fma2) + (!fma1|fma2) = 1.0
	 */
	private String getComplementarySemantics() {
		Formula prob1NotF = new Not(prob1.getPlainFormula());
		if (((Not) prob1NotF).getFormula() instanceof Not) // To avoid (!! (!! (-p- a))) and look for (-p- a) instead.
			prob1NotF = ((Not) ((Not) prob1NotF).getFormula()).getFormula();
		
		int prob1NotFIndex = PltlFormula.getIndex(prob1NotF);
		if ( prob1NotFIndex > -1) {
			Prob prob1Not = new Prob(prob1.getTime(), prob1NotFIndex);
			for (CProb cp: PltlFormula.cProbsTBP)
				if (cp.getProb1().equals(prob1Not) && cp.getProb2().equals(prob2))
					return new ArithFormula(Op.EQ, new Constant((float) 1), new ArithFormula(Op.PLUS, this, cp)).toString() + "\n";
		}
		
		return "";
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

	public Formula get(int offset) {
		return null;
	}

	public Formula getProp(int offset) {
		return null;// Not required.
	}
}

