package pltl;

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
	
	public static String getOp(String op, String s1, String s2){
		return "(" + op + " " + s1 + " " + s2 + ")";
		
	}
	public static String getSmt2Plus(String... inputs){
		String s = "(+";
		for (String s1:inputs)
			s += " " + s1;
		return s+ ")";
	}
	
	public static String getzot(int time, int f){
		return "(zot " + time + " " + f + ")";
	}
	
	public static String getzotp(int time, int f){
		return "(zot-p " + time + " " + f + ")";
	}
	
	public static String getzotcp(int time, int f1, int f2){
		return "(zot-cp " + time + " " + f1 + " " + f2 + ")";
	}
	
}
