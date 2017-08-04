package pltl;

import pltl.bool.Formula;

public class Smt2Formula{

	//	public static String getSmt2Eq(String s1, String s2){
	//		return getOp("=", s1, s2);
	//	}
	//	
	//	public static String getSmt2Minus(String s1, String s2){
	//		return getOp("-", s1, s2);
	//	}
	//	
	//	public static String getSmt2Plus(String s1, String s2){
	//		return getOp("+", s1, s2);
	//	}
	//	
	//	public static String getSmt2Implies(String s1, String s2){
	//		return getOp("=>", s1, s2);
	//	}

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
	
//	public static String getSmt2Plus(String... inputs) {
//		String s = "(+";
//		for (String s1:inputs)
//			s += " " + s1;
//		return s+ ")";
//	}
	
//	public static String getSmt2Plus(ArrayList<String> inputs) {
//		String s = "(+";
//		for (String s1:inputs)
//			s += " " + s1;
//		return s + ")";
//	}

	public static String getzot(int time, int f) {
		return "(zot " + time + " " + f + ")";
	}

//	public static String getzotp(int time, int f) {
////		return "(zot-p " + time + " " + f + ")";
////		return "(zot-p " + PltlFormula.getCode(time, PltlFormula.get(f)) + ")";
//		return new Prob(time, f).toString();
//	}

//	public static String getzotcp(int time1, int f1, int time2, int f2) {
////		return "(zot-cp " + PltlFormula.getCode(time1, PltlFormula.get(f1)) + " " + PltlFormula.getCode(time2, PltlFormula.get(f2)) + ")";
//		return "(zot-cp " + PltlFormula.getTimeIndex(time1, f1).toSeqString() + " " + PltlFormula.getTimeIndex(time1, f2).toSeqString() + ")";
////		return "(zot-cp " + time + " " + f1 + " " + f2 + ")";
//	}

//	public static String getzotcp(Prob ti1, Prob ti2) {
//		return "(zot-cp " + ti1.toSeqString() + " " + ti2.toSeqString() + ")";
//	}


}
