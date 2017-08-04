package arith;

import java.util.ArrayList;

import pltl.CProb;
import pltl.PltlFormula;
import pltl.bool.Formula;

public class ArithFormula implements Formula{
	private Op op;
	private ArrayList<Formula> formulae = new ArrayList<Formula>();

	public ArithFormula(Op op, Formula... formulae) {
		ArrayList<Formula> fmae = new ArrayList<Formula>();
		for (Formula f: formulae)
			fmae.add(f);
		setOpFe(true, op, fmae);
	}
	
	public ArithFormula(Op op, ArrayList<Formula> formulae) {
		setOpFe(true, op, formulae);
	}
	
	public ArithFormula(boolean process, Op op, Formula... formulae) {
		ArrayList<Formula> fmae = new ArrayList<Formula>();
		for (Formula f: formulae)
			fmae.add(f);
		setOpFe(process, op, fmae);
	}
	
	private void setOpFe(boolean process, Op op, ArrayList<Formula> formulae) {
		this.op = op;
		for (Formula f: formulae) {
			this.formulae.add(f);
			if (process && f instanceof CProb)
				PltlFormula.add((CProb) f);
		}
	}
	
	@Override
	public String toString() {
		String s = "(" + op.toString();
		for (Formula f: formulae)
			s += " " + f.toString();

		return s + ")";
	}
	
}
