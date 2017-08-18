package pltl;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import arith.ArithFormula;
import arith.Constant;
import arith.Op;
import pltl.bool.And;
import pltl.bool.Formula;
import pltl.bool.Not;
import pltl.bool.Or;
import pltl.trio.Next;
import pltl.trio.Predicate;
import pltl.trio.Yesterday;

public class PltlFormula {

	public static ArrayList<Formula> instances = new ArrayList<Formula>();
	public static ArrayList<CProb> cProbsTBP = new ArrayList<CProb>();
	//	public static ArrayList<Boolean> processed = new ArrayList<Boolean>();
	public static ArrayList<Formula> deps = new ArrayList<Formula>();
	//	public static ArrayList<ArrayList<BooleanFormulae>> depCluster = new ArrayList<ArrayList<BooleanFormulae>>();
	//	public static ArrayList<ArrayList<Integer>> deps = new ArrayList<ArrayList<Integer>>();
	public static int bound = 1;
	public static int mainF = 0;
	public static ArrayList<Prob> bayesNet = new ArrayList<Prob>();

	public static int add(Formula fma) {
		int i = getIndex(fma);
		if (i == -1) {
			if (fma instanceof Not && ((Not) fma).getFormula() instanceof Not)
				return add(((Not) ((Not) fma).getFormula()).getFormula());
			instances.add(fma);
			//			if (fma instanceof ProbAtom) {
			//				if (((ProbAtom) fma).getF11() != null)
			//					addToDepCluster(((ProbAtom) fma).getF11(), ((ProbAtom) fma).getF12());
			//				if (((ProbAtom) fma).getF21() != null)
			//					addToDepCluster(((ProbAtom) fma).getF21(), ((ProbAtom) fma).getF22());
			//			}
			return instances.size() - 1;
		}
		else
			return getIndex(fma);
	}

	public static void add (Formula...formulae) {
		for (Formula f: formulae)
			add(f);
	}

	public static void add(CProb cProb) {
		for (CProb c: cProbsTBP)
			if (cProb.equals(c))
				return;
		cProbsTBP.add(cProb);
	}

	//	private static BooleanFormulae pruneDeps(And and) {
	//		for (BooleanFormulae f: and.getFormulae())
	//	}


	public static Prob getProb(int time, Formula f) {
		if (f instanceof Next)
			return getProb(time + 1, ((Next) f).getFormula());
		else if (f instanceof Yesterday)
			return getProb(time - 1, ((Next) f).getFormula());

		// TODO Futr, Past, ...

		return new Prob(time, add(f));
	}

	public static Prob getProb(int time, int index) {
		return getProb(time, PltlFormula.get(index));
	}

	public static void addDep(Formula f) {
		deps.add(f);
	}

	private static ArrayList<Formula> getAtoms(Formula f) {
		ArrayList<Formula> bfs = new ArrayList<Formula>();
		if (f instanceof And)
			for (Formula bf:((And) f).getFormulae())
				bfs.addAll(getAtoms(bf));

		else if (f instanceof Or)
			for (Formula bf:((Or) f).getFormulae())
				bfs.addAll(getAtoms(bf));

		else if (f instanceof Not)
			bfs.addAll(getAtoms(((Not) f).getFormula()));

		else if (f instanceof Next) {
			bfs.addAll(getAtoms(((Next) f).getFormula()));
			bfs.add((Next) f);
		}

		else if (f instanceof Predicate)
			bfs.add((Predicate) f);
		//TODO until, since, ...

		return bfs;
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
		
		@Override
		public String toString() {
			return Smt2Formula.getzot(0, -2);
		}

		@Override
		public boolean equals(Object o) {
			return o instanceof False;
		}
	}

	/*		private static void addToDepCluster(BooleanFormulae f1, BooleanFormulae f2) {
			ArrayList<BooleanFormulae> bfs = new ArrayList<BooleanFormulae>();
			bfs.addAll(getAtoms(f1));
			bfs.addAll(getAtoms(f2));
			for (int i = 0; i < bfs.size() - 1; i++)
				addPairToDepCluster(bfs.get(i), bfs.get(i + 1));
		}

		private static void addPairToDepCluster(BooleanFormulae f1, BooleanFormulae f2) {
			if (getDepClusterIndex(f1) == -1 && getDepClusterIndex(f2) == -1) {
				ArrayList<BooleanFormulae> temp = new ArrayList<BooleanFormulae>();
				temp.add(f1);
				temp.add(f2);
				depCluster.add(temp);
			}
			else if (getDepClusterIndex(f1) > -1 && getDepClusterIndex(f2) == -1) {
				depCluster.get(getDepClusterIndex(f1)).add(f2);
			}
			else if (getDepClusterIndex(f1) == -1 && getDepClusterIndex(f2) > -1) {
				depCluster.get(getDepClusterIndex(f2)).add(f1);
			}
			else{
				int ai = getDepClusterIndex(f1);
				int bi = getDepClusterIndex(f2);
				if (ai != bi) {
					for (BooleanFormulae bf: depCluster.get(bi))
						depCluster.get(ai).add(bf);
					depCluster.remove(bi);
				}
			}
		}
		private static int getDepClusterIndex(BooleanFormulae f) {
			for (int i = 0; i < depCluster.size(); i++) {
				for (int j = 0; j < depCluster.get(i).size(); j++) { //It returns f's negation index if it exists. 
					if (f.equals(depCluster.get(i).get(j)) || (f instanceof Not && ((Not) f).getFormula().equals(depCluster.get(i).get(j))) || new Not(f).equals(depCluster.get(i).get(j)))
						return i;
				}
			}

			return -1;
		}
	//Returns ture, if it is expressible by zot-px.
		public static boolean isSimpleType(BooleanFormulae f) {
			return ()
		}
		private class FInstance{
			BooleanFormulae f;
			boolean probDone;
			boolean theNegDone;
			public FInstance(BooleanFormulaeZ) {
				this.f = f;
				this.probDone = fNumber;
				this.value = value;
			}
		}
	//Converts a formula to its bitvector representative with X (don't care) bits.
	public static String getZotpx(int time, BooleanFormulae f) {
		String s = "(zot-px " + time + " #b";
		StringBuilder bv1 = new StringBuilder("");
		StringBuilder bv2 = new StringBuilder("");
		for (int ins = instances.size() -1; ins >= 0 ; ins--) {
			bv1.append("1");
			bv2.append("1");
		}
		int n = instances.size();
		int fIndex = getIndex(f);

		if (fIndex != -1) {
			bv1.setCharAt(n - fIndex - 1, '1');
			bv2.setCharAt(n - fIndex - 1, '0');
		}
		else if (f instanceof And) {
			And and = (And) f;
			for (BooleanFormulae andFF:and.getFormulae()) {
				fIndex = getIndex(andFF);
				char andFFChar = '1';
				if (fIndex == -1) {
					if (andFF instanceof Not) {
						fIndex = getIndex(((Not) andFF).f);
						andFFChar = '0';
					}
					else
						System.out.println("The formula is not found in the PltlFormulae.instaces.");
				}
				bv1.setCharAt(n - fIndex - 1, andFFChar);
				bv2.setCharAt(n - fIndex - 1, '0');
			}
		}

		else if (f instanceof Or) {
		}

		else if (f instanceof Not) {

		}


		return s + bv1 + " #b" + bv2 + ")";
	}
	 */

}
