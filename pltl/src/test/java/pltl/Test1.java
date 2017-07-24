package pltl;

public class Test1 {

	public static void main(String[] args) {
		String s = "";// check tautologies............
		//test if (dep !a b) is considered from (dep a b)***************************************************

//TODO
//		s = "(&& (alw(dep (-p- a) (-p- b)))"
//				+ ")"
//				+ "FORMULA:\n"
//				+ "(&&"
//				+ " (= 0.2 (zot-p (-p- b))) (= 0.5 (zot-cp (-p- a) (-p- b))) "
//				+ "(!! (= 0.1 (zot-p (&& (-p- a) (-p- b)))))"
//				+ ")"
//				;
		
//		s = "(&& "
//				+ "(= (zot-p (-p- head)) 0.5)"
//				+ "(= (zot-p (next (-p- head))) 0.5)"
//				+ "(= (zot-p (next (next (-p- head)))) 0.5)"
//				+ "(= (zot-cp (-p- head) (next (-p- head))) (zot-p (-p- head)))"//independent
//				+ "(= (zot-cp (next (-p- head)) (next (next (-p- head)))) (zot-p (next (-p- head))))"//independent
//				+ "(= (zot-cp (-p- head) (next (next (-p- head)))) (zot-p (-p- head)))"//independent
////				+ "(!! (= 0.125 (zot-p (&& (-p- head) (next (-p- head)) (next (next (-p- head)))))))"
//				+ ")";
//		s = "(&& (!! (&& (-p- b) (-p- c))) (!! (&& (!!(-p- b)) (!! (-p- c)))) (= (zot-p (-p- b)) 0.9) (= (zot-p (-p- c)) 0.1)" //missing relationship between d and (b,c)
//				+ "(= (zot-cp (next (-p- d)) (-p- b)) 0.1)"
//				+ "(= (zot-cp (!! (next (-p- d))) (-p- b)) 0.9)"
//				+ "(= (zot-cp (next (-p- d)) (-p- c)) 1.0)"
//				+ "(= (zot-cp (!! (next (-p- d))) (-p- c)) 0.0)"
////				+ "(= (zot-p (-p- c)) 0.1)  "
////				+ "(= (zot-cp (next (-p- d)) (-p- b)) 0.1)" //must imply CP(Xd, b) = 0.9
////				+ "(= (zot-cp (next (-p- d)) (-p- b)) 0.1)" //must imply CP(Xd, b) = 0.9
//				+ ")";
//		s = "(&& "
//				+ "(-> (!! (-p- a)) (= (zot-p (-p- b)) 0.8)) (-> (-p- a) (= (zot-p (-p- b)) 0.9))"
//				+ ")";
//		s = "(&& (-p- a) (-p- b))";
//		s = "(&& (<-> (-p- d) (&& (-p- b) (!! (-p- c) )) ) (= (zot-p (&& (-p- b) (-p- c) )) 0.1))";
		
		//		s = "(&& (|| (-p- a) (!! (-p- a))) (!! (= (zot-p (-p- a)) 0.5) ) (> (zot-p (|| (-p- a) (-p- a))) 0.0))");
		//		s = "(-> (-p- a) (= (zot-p (-p- c) 0.9))");
		//		s = "(= 1.0 (zot-p (-> (-p- a) (|| (-p- b) (-p- c)))))");
		//		s = "(|| (&& (-p- a) (-p- c)) (&& (-p- b) (-p- c))) ");//(= (zot-cp (-p- c) (-p- a)) 0.9) (= (zot-cp (-p- c) (-p- b)) 0.8))");//??????????
		//		s = "(|| (&& (-p- a) (-p- c)) (-p- c)) ");//(= (zot-cp (-p- c) (-p- a)) 0.9) (= (zot-cp (-p- c) (-p- b)) 0.8))");//??????????
		//		s = "(&& (-> (-p- a) (= (zot-p (-p- c)) 0.9)) (-> (!! (-p- a)) (= (zot-p (-p- c)) 0.8)))");//??????????
		//		s = "(&& (> (zot-p (&& (-p- a) (-p- b))) 0.0) (||(!! (-p- a)) (-p- b))))");
		//		s = "(> (zot-p (&& (-p- a) (-p- b))) 0.0)");
		//		s = "(&& (-p- a) (|| (!! (-p- a)) (-p- d)))");
		//		s = "(&& (-p- a) (|| (!! (-p- b)) (-p- c)))");
		//		s = "(&& (-p- a) (-p- b) (-P- c) )");
		//		s = "(&& (-p- a) (-p- b) (= (zot-p (-p- a) 1.0)))");
		//		s = "(|| (-p- a) (-p- b) (-P- c)");
		//		s = "(&& (-p- a) (|| (!! (-p- a)) (-p- b))");
		//		s = "(&& (!! (= (zot-p (&& (-p- b) (-p- a)) 0.3)) (= (zot-p (-p- a)) 0.5) (= (zot-cp (-p- b) (-p- a)) 0.6))"); //*
		//		s = "(>= (zot-p (-p- a)) (zot-cp (-p- b) (-p- a)))");
		//		s = "(|| (-p- a) (-p- b))");
		//		s = "(&& (= (zot-cp (-p- a) (-p- b)) 1.0) (= (zot-p (-p- a)) 0.5 )");
		//		s = "(&& (-p- a) (|| (-p- b) (-p- c)))"); //[] getNormalizeFormula
		//		s = "(&& (-p- a) (-p- b) (-p- c))"); //[] getNormalizeFormula


		//		Done
//		s = "(&& (-p- a) (-> (-p- a) (> (zot-p (next (-p- a))) 0.9)) )";// []
//s = "(&& (|| (&& (-p- b) (!! (-p- c))) (&& (!! (-p- b)) (-p- c))) (<-> (-p- d) (&& (-p- b) (!! (-p- c) )) ) (= (zot-p (&& (-p- b) (-p- c) )) 0.1))"; // [] UNSAT
		//		s = "(&& (> (zot-p (-p- a)) (zot-p (-p- b))) (-> (-p- a) (-p- b)))");// [] UNSAT
		//		s = "(&& (< (zot-p (-p- a)) 0.5) (= (zot-p (|| (-p- a) (-p- b))) 0.6) (= (zot-p (-> (-p- a) (-p- b))) 0.5))"); //P(b) must be 0.1, and P(a) must be >= 0.5, result = unsat
		//		s = "(&& (-p- a) (|| (-p- b) (-p- c)))"); //[] getNormalizeFormula
		//		s = "(&& (&& (-p- c) (-p- d)) (|| (-p- a) (-p- b)))"); // getNormalizeFormula
		//		s = "(&& (|| (-p- a) (-p- b)) (&& (|| (-p- a) (-p- b)) (|| (-p- a) (-p- b))) (&& (-p- a) (-p- b)))");// [] getNormalizeFormula
		//		s = "(&& (|| (-p- a) (-p- b)) (&& (|| (-p- a) (-p- b)) (|| (-p- a) (-p- b))) )");// [] getNormalizeFormula
		//		s = "(&& (|| (-p- a) (-p- a)) (&& (|| (-p- a) (|| (-p- a) (-p- a) (|| (-p- a) (-p- a))) (-p- a) )) )");// [] getNormalizeFormula
		//		s = "(&& (-p- a) (|| (-p- a) (-p- a)))");// []  getNormalizeFormula
		//		s = "(&& (|| (|| (-p- a) (-p- b)) (|| (-p- a) (-p- b))))");// [] getNormalizeFormula
		//		s = "(&& (||  (-p- b) (-p- a) (-p- b)))");// [] getNormalizeFormula
		//		s = "(|| (|| (-p- c) (-p- d)) (&& (-p- a) (-p- b)))"); // [] getNormalizeFormula
		//		s = "(|| (-p- a) (-p- a)))");
		//		s = "(&& (-p- a) (-p- b)))");
		//		s = "(&& (-p- a) (-p- b) (-p- c)))");
		//		s = "(&& (= (zot-p (|| (-p- a) (-p- b))) 0.6) (= (zot-p (-> (-p- a) (-p- b))) 0.5))"); //P(b) must be 0.1
		Parser p = new Parser();
		p.Parse(s);
	}

}
