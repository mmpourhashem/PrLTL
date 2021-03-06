package pltl;

import pltl.bool.Formula;

public class Smt2Formula{

	public static String getOp(String op, String s1, String s2) {
		return "(" + op + " " + s1 + " " + s2 + ")";

	}
	
	public static String getOp(String op, Formula... formulae) { // Whenever an operator is used, it also adds undefined (conditional) probabilities to be defined.  
		PltlFormula.add(formulae);
		String s = "(" + op;
		for (Formula f:formulae)
			s += " " + f.toString();

		return s + ")";
	}
	
	public static String getOp(String op, String... formulae) { // Whenever an operator is used, it also adds undefined (conditional) probabilities to be defined.  
		String s = "(" + op;
		for (String f:formulae)
			s += " " + f;

		return s + ")";
	}
	
	public static String getRangeConstraints(Formula f) {
//		return new ArithFormula(false, Op.GTE, f, new Constant((float) 0.0)).toString() + " " + new ArithFormula(false, Op.LTE, f, new Constant((float) 1.0)).toString() + "\n";
		return "(range " + f + ")\n";
	}
	
	public static String getzot(int time, int f) {
		return "(zot " + time + " " + f + ")";
	}

	public static String getRangeConstraints(int formulaIndex) {
		String s = "";
		for (int time = 0; time <= PltlFormula.bound; time++)
			s += getRangeConstraints(new Prob(time, formulaIndex));
		
		return s ;
	}
	
}
