package pltl;


import java.util.ArrayList;

import arith.ArithFormula;
import arith.Constant;
import arith.Op;
import pltl.bool.And;
import pltl.bool.Formula;
import pltl.bool.Not;
import pltl.bool.Or;
import pltl.trio.Dist;
import pltl.trio.Predicate;
import pltl.trio.Until;
import pltl.trio.Yesterday;

public class PltlFormula {

	public static ArrayList<Formula> instances = new ArrayList<Formula>();
	// To keep formulae's main string before simplifications. See Parser.java.<Simplification rationale>
	private static ArrayList<String> instancesString = new ArrayList<String>();
	public static ArrayList<CProb> cProbsTBP = new ArrayList<CProb>();
	//	public static ArrayList<Boolean> processed = new ArrayList<Boolean>();
	public static ArrayList<Formula> deps = new ArrayList<Formula>();
	//	public static ArrayList<ArrayList<BooleanFormulae>> depCluster = new ArrayList<ArrayList<BooleanFormulae>>();
	//	public static ArrayList<ArrayList<Integer>> deps = new ArrayList<ArrayList<Integer>>();
	public static int bound = 2;
	public static int mainF = 0;
	public static ArrayList<Prob> bayesNet = new ArrayList<Prob>();

	public static int add(Formula fma) {
//TODO <!-- del
		int ff;		if (fma == null)
			ff=0;
		if (fma instanceof Not && ((Not) fma).getFormula() == null)
			ff=1;
		if (fma instanceof Yesterday)
			ff=1;
		if (fma instanceof Until)
			ff=1;
		if (fma instanceof Or)
			ff=2;
//		-->
		fma = Probability.process(fma); //check getProp for all formulae not to convert (yesterday (next a)) to a!//FIXME
		// <!-- To avoid (dist (dist (-p- a) i) j) and have (dist (-p- a) i+j) instead. 
//		if (fma instanceof Dist && ((Dist) fma).getFormula() instanceof Dist)
//			while (((Dist) fma).getFormula() instanceof Dist)
//				fma = new Dist(((Dist) ((Dist) fma).getFormula()).getFormula(), ((Dist) fma).getOffset() + ((Dist) ((Dist) fma).getFormula()).getOffset());
		if (fma instanceof Dist)
			fma = processDist((Dist) fma);
		//-->
		// <!-- To avoid (dist (-p- a) 0) and have (-p- a) instead.
		if (fma instanceof Dist && ((Dist) fma).getOffset() == 0)
			fma = ((Dist) fma).getFormula();
		//-->
		int i = getIndex(fma);
		if (i == -1) {
			if (fma instanceof Not && ((Not) fma).getFormula() instanceof Not)
				return add(((Not) ((Not) fma).getFormula()).getFormula());
			instances.add(fma);
			instancesString.add("");
			return instances.size() - 1;
		}
		else
			return getIndex(fma);
	}

	public static void add (Formula...formulae) {
		for (Formula f: formulae)
			add(f);
	}
	
	public static void add(ArrayList<Formula> formulae) {
		for (Formula f: formulae)
			add(f);		
	}


	public static void add(CProb cProb) {
		for (CProb c: cProbsTBP)
			if (cProb.equals(c))
				return;
		cProbsTBP.add(cProb);
	}

	public static void setFormulaString(Formula f, String s) {
		int index = add(f);
		instancesString.set(index, s);
	}

	public static String getFormulaString(int index) {
		return instancesString.get(index);
	}

	public static Prob getProb(int time, Formula f) {
		//		if (f instanceof Next)
		//			return getProb(time + 1, ((Next) f).getFormula());
		//		else if (f instanceof Yesterday)
		//			return getProb(time - 1, ((Next) f).getFormula());
		//		if (f instanceof Futr)
		//			return getProb(time + ((Futr) f).getInt(), ((Futr) f).getFormula());
		//		if (f instanceof Past)
		//			return getProb(time - ((Past) f).getInt(), ((Past) f).getFormula());
		if (f instanceof Dist)
			return getProb(time + ((Dist) f).getOffset(), ((Dist) f).getFormula());

		return new Prob(time, add(f));
	}

//	public static Prob getProb(int time, int index) {
//		return getProb(time, PltlFormula.get(index));
//	}

	public static void addDep(Formula f) {
		deps.add(f);
	}

	public static int getIndex(Formula fma) {
		for (int i = 0; i < instances.size(); i++)
			if (instances.get(i).equals(fma))
				return i;
		return -1;
	}

	public static Formula get(int index) {
		return instances.get(index);
	}

	public static Op getProbOp(String s) {
		if (s.equals("<"))
			return Op.LT;
		if (s.equals("<="))
			return Op.LTE;
		if (s.equals("="))
			return Op.EQ;
		if (s.equals(">"))
			return Op.GT;
		if (s.equals(">="))
			return Op.GTE;
		if (s.equals("+"))
			return Op.PLUS;
		if (s.equals("-"))
			return Op.MINUS;
		if (s.equals("*"))
			return Op.MUL;

		return null;
	}

	public static ArrayList<Predicate> getPredicates() {
		ArrayList<Predicate> p = new ArrayList<Predicate>();
		for (Formula f:instances)
			if (f instanceof Predicate)
				p.add((Predicate) f);
		return p;
	}

	public static void addBayes(Prob depTI) {
		for (Prob ti: bayesNet) {
			Prob predicateProb = depTI.getPredicateProb();
			if (ti.equals(predicateProb)) {
				ti.addParent(depTI.getThisParents());
				return;
			}
		}

		bayesNet.add(depTI);
	}

	public static boolean isParentOf(Prob parent, Prob depF) {
		for (Prob ti:bayesNet)
			if (ti.equals(depF) && depF.hasParent(parent))
				return true;

		return false;
	}

	public static Formula getDNF(Formula formula1) {
		Formula formula = getNNF(getFlatFormula(formula1));

		if (formula instanceof Or) {
			Or or = (Or) formula;
			or.prune();
			Or newOr = new Or();

			for (Formula f: or.getFormulae()) {
				newOr.addFormula(getDNF(f));
			}

			newOr.prune();
			return newOr.getFlatOr();
		}

		if (formula instanceof And) {
			And and = (And) formula;
			and.prune();
			if (and.getFormulae().size() == 1)
				return getDNF(and.getFormulae().get(0));

			int firstOrIndex = -1;
			for (int i = 0; i < and.getFormulae().size(); i++)
				if (and.getFormulae().get(i) instanceof Or) {
					firstOrIndex = i;
					break;
				}
			And newAnd = new And();
			for (int i = 0; i < and.getFormulae().size(); i++)
				if (i != firstOrIndex)
					newAnd.addFormula(getDNF(and.getFormulae().get(i)));
			if (firstOrIndex == -1) {
				newAnd.prune();
				return newAnd.getFlatAnd();
			}
			else
				return getDNF(distributeAnd(and.getFormulae().get(firstOrIndex), newAnd));
		}

		return formula;
	}

	/**
	 * @param formula1
	 * @return
	 * Pushes negations to the atomic formulae and returns the Negation Normal Form.
	 */

	public static Formula getNNF(Formula formula1) {
		Formula formula = getFlatFormula(formula1);
		if (formula instanceof And) {
			And newAnd = new And();
			for (Formula f: ((And) formula).getFormulae())
				newAnd.addFormula(getNNF(f));
			return newAnd.getFlatAnd();
		}

		if (formula instanceof Or) {
			Or newOr = new Or();
			for (Formula f: ((Or) formula).getFormulae())
				newOr.addFormula(getNNF(f));
			return newOr.getFlatOr();
		}

		if (formula instanceof Not) {
			Not not = (Not) formula;
			if (not.getFormula() instanceof Not)
				return getNNF(((Not) not.getFormula()).getFormula());

			//De Morgan's law
			if (not.getFormula() instanceof And) {
				Or newOr = new Or();
				for (Formula f: ((And) not.getFormula()).getFormulae())
					newOr.addFormula(getNNF(new Not(f)));

				return newOr.getFlatOr();
			}

			//De Morgan's law
			if (not.getFormula() instanceof Or) {
				And newAnd = new And();
				for (Formula f: ((Or) not.getFormula()).getFormulae())
					newAnd.addFormula(getNNF(new Not(f)));

				return newAnd.getFlatAnd();
			}
		}

		//Returns the atomic formula, the ultimate level of negation being pushed.
		return formula;
	}

	/**
	 * 
	 * @param distribute1
	 * @param context1
	 * @return
	 * Distributed formula
	 * 
	 * For example, distributeSFormula((-p- a), (|| (-p- b) (&& (-p- c) (-p- d)))) returns
	 * (|| (&& (-p- a) (-p- b)) (&& (-p- a) (-p- c) (-p- d)))
	 */
	public static Formula distributeAnd(Formula distribute1, Formula context1) {
		Formula context = getFlatFormula(context1);
		Formula distribute = getFlatFormula(distribute1);

		if (distribute instanceof Or) {
			Or newOr = new Or();
			for (Formula f: ((Or) distribute).getFormulae())
				newOr.addFormula(distributeAnd(f, context));
			newOr = newOr.getFlatOr();
			newOr.prune();
			return newOr;
		}

		//		if (distribute instanceof And) {
		//			And newAnd = new And();
		//			for (Formula f: ((And) distribute).getFormulae())
		//				newAnd.addFormula(distributeAnd(f, context));
		//			return newAnd.getFlatAnd();
		//		}
		//		assertFalse(distribute instanceof And);

		if (context instanceof And) {
			And and = (And) context;
			and.addFormula(distribute);
			and = and.getFlatAnd();
			and.prune();
			return and;
		}

		if (context instanceof Or) {
			Or newOr = new Or();
			for (Formula f:((Or) context).getFormulae())
				newOr.addFormula(distributeAnd(distribute, f));

			newOr = newOr.getFlatOr();
			newOr.prune();
			return newOr;
		}
		And and = new And(distribute, context).getFlatAnd();
		and.prune();
		return and;
	}

	private static Formula getFlatFormula(Formula formula) {
		if (formula instanceof And)
			return ((And) formula).getFlatAnd();

		if (formula instanceof Or)
			return ((Or) formula).getFlatOr();

		return formula;
	}
	
	public static boolean outOfBound(int offset) {
		return (offset > bound || offset < 0);
	}
	
	/**
	 * @param fma
	 * A Dist formula
	 * @return
	 * To avoid (dist (dist (-p- a) i) j) and have (dist (-p- a) i+j) instead. 
	 */
	public static Formula processDist(Formula f) {
		if (! (f instanceof Dist))
			return f;
		Dist fma = ((Dist) f);
//		if (PltlFormula.outOfBound(fma.getOffset()) || fma.getFormula() instanceof PltlFormula.False) // to false is commented because it recognizes (yesterday (-p- a)) as false.
//			return new PltlFormula.False();
		
		if (fma.getFormula() instanceof Dist) {
//			if (PltlFormula.outOfBound(fma.getOffset() + ((Dist) fma.getFormula()).getOffset()) || ((Dist) fma.getFormula()).getFormula() instanceof PltlFormula.False)
//				return new PltlFormula.False();
			while (fma.getFormula() instanceof Dist) {
//				if (PltlFormula.outOfBound(fma.getOffset() + ((Dist) fma.getFormula()).getOffset()) || ((Dist) fma.getFormula()).getFormula() instanceof PltlFormula.False)
//					return new PltlFormula.False();
				fma = new Dist(((Dist) fma.getFormula()).getFormula(), fma.getOffset() + ((Dist) fma.getFormula()).getOffset());
			}
		}
		
		return fma;
	}

	/**
	 * @param formula
	 * @return
	 * True, if the flattened formula is in Disjunctive Normal Form.
	 */
	public static boolean isDNF(Formula formula1) {
		Formula formula = getNNF(getFlatFormula(formula1));

		if (formula instanceof And)
			for (Formula f: ((And) formula).getFormulae())
				if (f instanceof Or || f instanceof And)
					return false;

		if (formula instanceof Or)
			for (Formula f: ((Or) formula).getFormulae())
				if (f instanceof And)
					if (! isDNF((And) f))
						return false;
		return true;
	}

	public static class True implements Formula {
		public String getSemantics() {
			String s = "; " + toString() + "\n";
			for (int time = 0; time <= bound; time++)
				s += new ArithFormula(Op.EQ, new Prob(time, add(this)), new Constant((float) 1));
			return s;
		}

		public String toString(int time) {
			return Smt2Formula.getzot(time, -1);
		}

		@Override
		public String toString() {
			return Smt2Formula.getzot(0, -1);
		}

		@Override
		public boolean equals(Object o) {
			return o instanceof True;
		}

		public Formula get(int offset) {
//			return this;
			return new Prob(0, -1);
		}

		public Formula getProp(int offset) {
			return new PropTrue();
		}

	}

	public static class False implements Formula {
		public String getSemantics() {
			String s = "; " + toString() + "\n";
			for (int time = 0; time <= bound; time++)
				s += new ArithFormula(Op.EQ, new Prob(time, add(this)), new Constant((float) 0));
			return s;
		}

		public String toString(int time) {
			return Smt2Formula.getzot(time, -2);
		}
		
		public Formula get(int offset) {
//			return this;
			return new Prob(0,-2);
		}

		@Override
		public String toString() {
			return Smt2Formula.getzot(0, -2);
		}

		@Override
		public boolean equals(Object o) {
			return o instanceof False;
		}

		public Formula getProp(int offset) {
			return new PropFalse();
		}
		
	}
	
	public static class PropTrue implements Formula {
		public String toString(int time) {
			return "true"; // This is the "true" keyword for SMT solvers.
		}

		@Override
		public String toString() {
			return "true"; // This is the "true" keyword for SMT solvers.
		}

		@Override
		public boolean equals(Object o) {
			return o instanceof PropTrue;
		}

		public Formula get(int offset) {
			if (PltlFormula.outOfBound(offset))
				return new PltlFormula.False();
			
			return this;
		}

		public Formula getProp(int offset) {
			return this;
		}

	}

	public static class PropFalse implements Formula {
		public String toString(int time) {
			return "false"; // This is the "false" keyword for SMT solvers.
		}

		@Override
		public String toString() {
			return "false"; // This is the "false" keyword for SMT solvers.
		}

		@Override
		public boolean equals(Object o) {
			return o instanceof PropFalse;
		}

		public Formula get(int offset) {
			return this;
		}

		public Formula getProp(int offset) {
			return this;
		}

	}
	
	public static void reset() {
		instances.clear();
		instancesString.clear();
		cProbsTBP.clear();
		deps.clear();
		bayesNet.clear();
		mainF = 0;
	}
}
