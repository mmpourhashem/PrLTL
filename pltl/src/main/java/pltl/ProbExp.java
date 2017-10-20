package pltl;

import arith.ArithFormula;
import arith.Constant;
import arith.Op;
import pltl.bool.And;
import pltl.bool.Formula;
import pltl.bool.Not;
import pltl.trio.Dist;
import pltl.trio.Futr;
import pltl.trio.Next;
import pltl.trio.Past;
import pltl.trio.Predicate;
import pltl.trio.Yesterday;

public class ProbExp implements Formula{

	private Op op;
	private Formula f1, f11, f12, f2, f21, f22;
	private float r1, r2;
	private String mainString = null;
	
	public ProbExp(Op op, Formula f1, Formula f11, Formula f12, Formula f2, Formula f21, Formula f22, float r1, float r2) {
		if (f1 != null)
			PltlFormula.setFormulaString(f1, f1.toString());
		//Conditional probability is not VIF.
//		if (f11 != null)
//			PltlFormula.setFormulaString(f11, f11.toString());
//		if (f12 != null)
//			PltlFormula.setFormulaString(f12, f12.toString());
		if (f2 != null)
			PltlFormula.setFormulaString(f2, f2.toString());
//		if (f21 != null)
//			PltlFormula.setFormulaString(f21, f21.toString());
//		if (f22 != null)
//			PltlFormula.setFormulaString(f22, f22.toString());

		//Checking for well-formedness
		if (! ((f11 == null || isCProbFWellFormed(f11)) && (f12 == null || isCProbFWellFormed(f12)) && (f21 == null || isCProbFWellFormed(f21)) && (f22 == null || isCProbFWellFormed(f22))))
			throw new IllegalArgumentException("Formula is not well-formed to appear in conditional probability.");
		
		ProbExpSetter(op, f1, f11, f12, f2, f21, f22, r1, r2);
	}
	
	public ProbExp(Op op, Formula f1, Formula f11, Formula f12, Formula f2, Formula f21, Formula f22, float r1, float r2, String mainString) {
		this.mainString = mainString;
		ProbExpSetter(op, f1, f11, f12, f2, f21, f22, r1, r2);
	}

	public void ProbExpSetter(Op op, Formula f1, Formula f11, Formula f12, Formula f2, Formula f21, Formula f22, float r1, float r2) {
		this.op = op;
		this.f1 = f1;
		this.f11 = f11;
		this.f12 = f12;
		this.f2 = f2;
		this.f21 = f21;
		this.f22 = f22;
		this.r1 = r1;
		this.r2 = r2;
	}

	public Op getOp() {
		return op;
	}
	public Formula getF1() {
		return f1;
	}

	public Formula getF11() {
		return f11;
	}

	public Formula getF12() {
		return f12;
	}

	public Formula getF2() {
		return f2;
	}

	public Formula getF21() {
		return f21;
	}

	public Formula getF22() {
		return f22;
	}

	public float getR1() {
		return r1;
	}
	public float getR2() {
		return r2;
	}

	public String getSemantics() {
		String s= ";" + toString() + "\n";
		int mainF = PltlFormula.add(this);
		if (f1 != null)
			PltlFormula.add(f1);
		if (f11 != null)
			PltlFormula.add(f11);
		if (f12 != null)
			PltlFormula.add(f12);
		if (f2 != null)
			PltlFormula.add(f2);
		if (f21 != null)
			PltlFormula.add(f21);
		if (f22 != null)
			PltlFormula.add(f22);

		for (int i = 0; i <= PltlFormula.bound; i++)
			s += "(= (zot " + i + " " + mainF + ") " + toSmt2(i) + ")\n";

		return s;
	}
	
	/**
	 * @param f
	 * @return
	 * Returns true, if f is allowed to appear in a conditional probability, (f|f).
	 * A formula is well-formed for CProb iff it is in the form of (-p- p), (!! (-p- p)), shifted mentioned forms, negated shifted mentioned form, or conjunction of them.
	 */
	private boolean isCProbFWellFormed(Formula f) {
		if (f instanceof Predicate)
			return true;
		if (f instanceof Not)
			return isCProbFWellFormed(((Not) f).getFormula());
		if (f instanceof Next)
			return isCProbFWellFormed(((Next) f).getFormula());
		if (f instanceof Yesterday)
			return isCProbFWellFormed(((Yesterday) f).getFormula());
		if (f instanceof Futr)
			return isCProbFWellFormed(((Futr) f).getFormula());
		if (f instanceof Past)
			return isCProbFWellFormed(((Past) f).getFormula());
		if (f instanceof Dist)
			return isCProbFWellFormed(((Dist) f).getFormula());
		if (f instanceof And) {
			for (Formula fma: ((And) f).getFormulae())
				if (! isCProbFWellFormed(fma))
					return false;
			
			return true;
		}

		return false;
	}

	public String toSmt2(int time) {
		Formula fma1,fma2;

		if (f1 != null)
			fma1= new Prob(time, PltlFormula.add(f1));
		else if (f11 != null && f12 != null)
//			fma1 = new CProb(new Prob(time, PltlFormula.add(f11)), new Prob(time, PltlFormula.add(f12)));
			fma1 = new CProb(((DepTempFormula) f11).getProbForDep(time, false), ((DepTempFormula) f12).getProbForDep(time, false));
		else
			fma1 = new Constant(r1);

		if (f2 != null)
			fma2 = new Prob(time, PltlFormula.add(f2));
		else if (f21 != null && f22 != null)
//			fma2 = new CProb(new Prob(time, PltlFormula.add(f21)), new Prob(time, PltlFormula.add(f22)));
			fma2 = new CProb(((DepTempFormula) f21).getProbForDep(time, false), ((DepTempFormula) f22).getProbForDep(time, false));
		else
			fma2 = new Constant(r2);

		return new ArithFormula(op, fma1, fma2).toString();
	}
	
	public Formula get(int offset) {
//		return new Dist(this, offset);
		return new Prob(offset, PltlFormula.add(this));
	}
	
	public Formula getProp(int offset) {
		return new Prop(offset, PltlFormula.add(this));
	}
	
	@Override
	public String toString() {
		if (mainString != null)
			return mainString;
		String s1 = "", s2 = "";
		
		if (f1 != null)
			s1 = "(zot-p " + f1.toString() + ")";
		else if (f11 != null && f12 != null)
			s1 = "(zot-cp " + f11.toString() + " " + f12.toString() + ")";
		else
			s1 = Float.toString(r1);
		
		if (f2 != null)
			s2 = "(zot-p " + f2.toString() + ")";
		else if (f21 != null && f22 != null)
			s2 = "(zot-cp " + f21.toString() + " " + f22.toString() + ")";
		else
			s2 = Float.toString(r2);
		
		return "(" + op.toString() + " " + s1 + " " + s2 + ")";
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof ProbExp)
			return (toString().equals(((ProbExp) o).toString()));
		
		return false;
	}

}
