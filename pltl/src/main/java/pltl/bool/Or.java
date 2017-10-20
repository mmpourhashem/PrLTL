package pltl.bool;

import java.util.ArrayList;
import arith.ArithFormula;
import arith.Constant;
import arith.Op;
import pltl.Parser;
import pltl.PltlFormula;
import pltl.Prob;
import pltl.Probability;

public class Or implements Formula{
	ArrayList<Formula> f = new ArrayList<Formula>();
	ArrayList<Prob> p = new ArrayList<Prob>();

	public Or(Formula... formulae) {
		ArrayList<Formula> bfs = new ArrayList<Formula>();
		for (Formula bf:formulae)
			bfs.add(bf);
		setFormulae(bfs);
	}

	public Or(ArrayList<Formula> formulae) {
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

	public ArrayList<Formula> getFormulae() {
		return f;
	}

	//	public Prob getProb() {
	//		int minimumTime = p.get(0).getTime();
	//
	//		for (int i = 1; i < p.size(); i++)
	//			if (p.get(i).getTime() < minimumTime)
	//				minimumTime = p.get(i).getTime();
	//		ArrayList<Formula> fmae = new ArrayList<Formula>();
	//
	//		for (Prob prob: p)
	//			fmae.add(prob.getFormula(minimumTime));
	//
	//		int andIndex = PltlFormula.add(new And(fmae));
	//
	//		return new Prob(minimumTime, andIndex);
	//	}

	public int size() {
		return f.size();
	}


	public String getSemantics() {
		return getPropSemantics() + getProbSemantics();
	}

	//	private String getBVProbSemantics() {
	//		String s = "";
	//		And newAnd = new And();
	//		for (BooleanFormulae bf: getFormulae())
	//			newAnd.addFormulae(new Not(bf));
	//		for (int time = 0; time <= PltlFormula.bound; time++) {
	//			s += Smt2Formula.getSmt2Eq(PltlFormula.getZotpx(time, this), Smt2Formula.getSmt2Minus("1.0", PltlFormula.getZotpx(time, newAnd)));
	//		}
	//		return s;
	//	}

	public String getPropSemantics() {
		String s = ";" + this.toString() + "\n";
		int mainF = PltlFormula.add(this);
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		for (Formula bf: f)
			indexes.add(PltlFormula.add(bf));
		for (int i = 0; i <= PltlFormula.bound; i++) {
			s += "(= (zot " + i + " " + mainF + ") (or";
			for (int j: indexes) {
				s += " (zot " + i + " " + j + ")"; 
			}
			s += "))\n";
		}

		return s;
	}

	public String getProbSemantics() {
		if (Probability.hasProbExp(this))
			return "";
		String s = "";
		for (int time = 0; time <= PltlFormula.bound; time++) {
			Prob mainProb = new Prob(time, PltlFormula.add(this));
			if (collapsedToFalse())
				s += new ArithFormula(Op.EQ, mainProb, new Constant((float) 0)).toString();
			else if (collapsedToTrue())
				s += new ArithFormula(Op.EQ, mainProb, new Constant((float) 1)).toString();
			else
				s += new ArithFormula(Op.EQ, mainProb, getRecOr(this, time)).toString();
		}

		return s;
	}

	/**
	 * @param formula
	 * A DNF formula. (|| f1 f2 ... fn)
	 * @return
	 * f1 + fma + (&& f1 cdr), where cdr = (|| f2 ... fn)
	 */
	private Formula getRecOr(Formula formula, int time) {
		if (formula instanceof And)
			return ((And) formula).getSemantics(time);

		if (formula instanceof Or) {
			if (((Or) formula).size() == 1)
				return getRecOr(((Or) formula).getFormulae().get(0), time);

			Formula f1 = ((Or) formula).getFormulae().get(0);
			Or cdr = new Or();
			Or f1cdr = new Or();
			for (int i = 1; i < ((Or) formula).getFormulae().size(); i++) {
				cdr.addFormula(((Or) formula).getFormulae().get(i));
				And tempAnd = new And(f1, ((Or) formula).getFormulae().get(i));
				tempAnd.prune();
				//				f1cdr.addFormula(new And(f1, ((Or) formula).getFormulae().get(i)).getFlatAnd());
				f1cdr.addFormula(tempAnd.getFlatAnd());
			}

			return new ArithFormula(Op.PLUS, getRecOr(f1, time), new ArithFormula(Op.MINUS, getRecOr(cdr, time), getRecOr(f1cdr, time)));
		}
		return new Prob(time, PltlFormula.add(formula));
	}

	/**
	 * @input
	 * (&& (-p- a) (&& (-p- b) (-p- c)))
	 * @return
	 * (&& (-p- a) (-p- b) (-p- c))
	 */
	public Or getFlatOr() {
		return new Or(getFlatFormula(this));
	}

	private ArrayList<Formula> getFlatFormula(Or or) {
		ArrayList<Formula> flatF = new ArrayList<Formula>();

		for (Formula fma: or.getFormulae())
			if (fma instanceof Or)
				flatF.addAll(getFlatFormula((Or) fma));
			else if (fma instanceof And)
				flatF.add(((And) fma).getFlatAnd());
			else
				flatF.add(fma);

		return flatF;
	}

	public void prune() {
		if (Probability.hasProbExp(this))
			return;
		f = getFlatOr().getFormulae();
		for (Formula fma1: f)
			for (Formula fma2: f)
				if (fma1 instanceof Not && ((Not) fma1).getTheNeg().equals(fma2)) { // If both AP and !AP are in the formula list, Or collapses to True.
					f = new ArrayList<Formula>();
					f.add(new PltlFormula.True());
					return;
				}

		ArrayList<Formula> newF = new ArrayList<Formula>();
		for (Formula fma: f) {
			if (fma instanceof And) {
				if (((And) fma).collapsedToTrue()) { // If both AP and !AP are in the formula list of an inner And, Or collapses to True.
					f = new ArrayList<Formula>();
					f.add(new PltlFormula.True());
					return;
				}

				if (! ((And) fma).collapsedToFalse()) // If an inner formula is collapsed to False, it is deleted from the main formula list.
					newF.add(fma);
			}
			else if (fma instanceof Or) {
				if (((Or) fma).collapsedToTrue()) { // If both AP and !AP are in the formula list of an inner Or, this Or collapses to True.
					f = new ArrayList<Formula>();
					f.add(new PltlFormula.True());
					return;
				}

				if (! ((Or) fma).collapsedToFalse()) // If an inner formula is collapsed to False, it is deleted from the main formula list.
					newF.add(fma);
			}
			else if (! (fma instanceof PltlFormula.False))
				newF.add(fma);
		}

		f = newF;
		if (f.size() == 0)
			f.add(new PltlFormula.False());
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
		if (PltlFormula.outOfBound(offset))
			return new PltlFormula.False();
		
		Or or = new Or();
		for (Formula fma: f)
			or.addFormula(fma.get(offset));
		return or;
	}
	
	public Formula getProp(int offset) {
		if (PltlFormula.outOfBound(offset))
    		return new PltlFormula.PropFalse();
		
		PropOr pOr = new PropOr();
		for (Formula fma: f)
			pOr.addFormula(fma.getProp(offset));
		return pOr;
	}

	@Override
	public String toString() {
		if (f.isEmpty() && p.isEmpty())
			return "";

		String s = "(||";
		for (int i = 0; i < f.size(); i++)
			s = s + " " + f.get(i).toString();

		for (int i = 0; i < p.size(); i++)
			s = s + " " + p.get(i).toString();

		return s + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Or) {
			ArrayList<Formula> a1 = this.getFormulae();
			ArrayList<Formula> a2 = ((Or) o).getFormulae();
			return Parser.bfArrayEqual(a1, a2);
		}
		return false;
	}

}
