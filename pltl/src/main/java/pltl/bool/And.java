package pltl.bool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import arith.ArithFormula;
import arith.Op;
import pltl.CProb;
import pltl.Parser;
import pltl.PltlFormula;
import pltl.Prob;
import pltl.ProbComparator;

public class And implements Formula{
	ArrayList<Formula> f = new ArrayList<Formula>();
	ArrayList<Prob> p = new ArrayList<Prob>();

	public And(Formula... formulae) {
		ArrayList<Formula> bfs = new ArrayList<Formula>();
		for (Formula bf:formulae)
			bfs.add(bf);
		setFormulae(bfs);
	}

	public And(ArrayList<Formula> formulae) {
		setFormulae(formulae);
	}

	public void setFormulae(ArrayList<Formula> formulae) {
		f = new ArrayList<Formula>();
		for (Formula bf:formulae)
			if (bf instanceof Prob)
				addProb((Prob) bf);
			else
				addFormula(bf);
	}

	public void addFormula(Formula fIn) {
		for (Formula fma: f)
			if (fma.equals(fIn))
				return;
		if (fIn != null)
			f.add(fIn);
//		prune();
	}

	public void addProb(Prob pIn) {
		for (Prob prob: p)
			if (prob.equals(pIn))
				return;
		if (pIn != null)
			p.add(pIn);
	}

	public void removeFormulae(int index) {
		f.remove(index);
	}

	public void setFormulae(int index, Formula newf) {
		f.set(index, newf);
	}

	public void addAllFormulae(ArrayList<Formula> bfs) {
		for (Formula bf:bfs)
			if (bf != null)
				addFormula(bf);
	}

	public void addAllProbs(ArrayList<Prob> probs) {
		for (Prob p:probs)
			if (p != null)
				addProb(p);
	}

	public ArrayList<Formula> getFormulae() {
		return f;
	}

	/**
	 * @returns a Prob for a conjunction of Probs. For example, if index of fma is 0, then (&& (zot-p 0 0) (zot-p 0 1) (zot-p 0 2)) is (zot-p 0 1) where 1 is the index of (&& (-p- a) (Futr (-p- a) 1) (Futr (-p- a) 2)) 
	 */
	public Prob getProb() {
		int minimumTime = p.get(0).getTime();

		for (int i = 1; i < p.size(); i++)
			if (p.get(i).getTime() < minimumTime)
				minimumTime = p.get(i).getTime();
		ArrayList<Formula> fmae = new ArrayList<Formula>();

		for (Prob prob: p)
			fmae.add(prob.getFormula(minimumTime));

		int andIndex = PltlFormula.add(new And(fmae));

		return new Prob(minimumTime, andIndex);
	}

	public int size() {
		return f.size();
	}

	public String getSemantics() {
		String s = "";
		int index = PltlFormula.add(this);
		for (int time = 0; time <= PltlFormula.bound; time++) {
			Prob mainF = new Prob(time, index);
			s += new ArithFormula(Op.EQ, mainF, getSemantics(time)).toString();
		}
		
		Set<Formula> bfSet = new HashSet<Formula>();
		for (Formula bf: f)
			bfSet.add(bf);
		return getPropSemantics() + s;
	}

	// It is getProbSemantics.
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Formula getSemantics(int time) {
		ArrayList<Prob> probs = new ArrayList<Prob>();
		for (Formula fma: f)
			probs.add(new Prob(time, PltlFormula.add(fma)));
		Collections.sort(probs, new ProbComparator());

		ArrayList<Prob> cdr = new ArrayList<Prob>();
		for (Prob p: probs)
			cdr.add(p);
		cdr.remove(0);
		if (probs.get(0).allAreParents(cdr) && probs.get(0).getParents().size() > cdr.size())
			return new ArithFormula(Op.PLUS, probs.get(0).getToSumWithParentSubset(cdr));

		return processProbAnd(probs);
	}

	private Formula processProbAnd(ArrayList<Prob> probs) {
		if (probs.size() == 0)
			return null;

		if (probs.size() == 1)
			return probs.get(0);

		ArrayList<Formula> firstPParents = new ArrayList<Formula>();// The parents of the first Prob.
		for (Prob p: probs)
			if (probs.get(0).hasParent(p))
				firstPParents.add(p);

		Formula left = probs.get(0);
		if (firstPParents.size() == 1)
			left = new CProb(probs.get(0), (Prob) firstPParents.get(0));
		else if (firstPParents.size() > 1)
			left = new CProb(probs.get(0), new And(firstPParents).getProb());

		probs.remove(0);

		return new ArithFormula(Op.MUL, left, processProbAnd(probs));
	}

	public String getPropSemantics() {
		String s = ";" + this.toString() + "\n";
		int mainF = PltlFormula.add(this);
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		for (Formula bf: f)
			indexes.add(PltlFormula.add(bf));
		for (int i = 0; i <= PltlFormula.bound; i++) {
			s += "(= (zot " + i + " " + mainF + ") (and";
			for (int j: indexes) {
				s += " (zot " + i + " " + j + ")";
			}
			s += "))\n";
		}

		return s;
	}

	/**
	 * @input
	 * (&& (-p- a) (&& (-p- b) (-p- c)))
	 * @return
	 * (&& (-p- a) (-p- b) (-p- c))
	 */
	public And getFlatAnd() {
		return new And(getFlatFormula(this));
	}

	private ArrayList<Formula> getFlatFormula(And and) {
		ArrayList<Formula> flatF = new ArrayList<Formula>();

		for (Formula fma: and.getFormulae())
			if (fma instanceof And)
				flatF.addAll(getFlatFormula((And) fma));
			else if (fma instanceof Or)
				flatF.add(((Or) fma).getFlatOr());
			else
				flatF.add(fma);

		return flatF;
	}

	public void prune() {
		f = getFlatAnd().getFormulae();
		for (Formula fma1: f)
			for (Formula fma2: f)
				if (fma1 instanceof Not) // If both AP and !AP are in the formula list, And collapses to False.
					if (((Not) fma1).getTheNeg() != null && ((Not) fma1).getTheNeg().equals(fma2)) { 
					f = new ArrayList<Formula>();
					f.add(new PltlFormula.False());
					return;
				}
		ArrayList<Formula> newF = new ArrayList<Formula>();
		for (Formula fma: f) {
			if (fma instanceof And) {
				if (((And) fma).collapsedToFalse()) { // If both AP and !AP are in the formula list of an inner And, this And collapses to False.
					f = new ArrayList<Formula>();
					f.add(new PltlFormula.False());
					return;
				}

				if (! ((And) fma).collapsedToTrue()) // If an inner formula is collapsed to True, it is deleted from the main formula list.
					newF.add(fma);
			}
			else if (fma instanceof Or) {
				if (((Or) fma).collapsedToFalse()) { // If both AP and !AP are in the formula list of an inner Or, this And collapses to False.
					f = new ArrayList<Formula>();
					f.add(new PltlFormula.False());
					return;
				}

				if (! ((Or) fma).collapsedToTrue()) // If an inner formula is collapsed to True, it is deleted from the main formula list.
					newF.add(fma);
			}
			else if (! (fma instanceof PltlFormula.True))
				newF.add(fma);
		}
		f = newF;
		if (f.size() == 0)
			f.add(new PltlFormula.True());
	}

	public boolean collapsedToTrue() {
		prune();
		return (f.size() == 1 && f.get(0).equals(new PltlFormula.True()));
	}

	public boolean collapsedToFalse() {
		prune();
		return (f.size() == 1 && f.get(0).equals(new PltlFormula.False()));
	}
	
	public Formula get(int offset) {
		And and = new And();
		for (Formula fma: f)
			and.addFormula(fma.get(offset));
		return and;
	}

	@Override
	public String toString() {
		if (f.isEmpty() && p.isEmpty())
			return "";

		String s = "(&&";
		for (int i = 0; i < f.size(); i++)
			s = s + " " + f.get(i).toString();

		for (int i = 0; i < p.size(); i++)
			s = s + " " + p.get(i).toString();

		return s + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof And) {
			ArrayList<Formula> a1 = this.getFormulae();
			ArrayList<Formula> a2 = ((And) o).getFormulae();
			return Parser.bfArrayEqual(a1, a2);
		}

		return false;
	}

}
