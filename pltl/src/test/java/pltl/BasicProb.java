package pltl;

import static org.junit.Assert.*;

import org.junit.Test;

public class BasicProb {

	@Test
	public void test1() {
		PltlFormula.bound = 3;
		String s = "FORMULA:"
				+ "(&& "
				+ "(> (zot-p (&& (-p- a) (-p- b))) (zot-p (-p- a)))"
				+ ")";
		mustBeUNSAT(s);
	}

	@Test
	public void test2() {
		PltlFormula.bound = 3;
		String s = "(dep (-p- a) (-p- b))"
				+ "FORMULA:"
				+ "(&&"
				+ "(= (zot-p (-p- b)) 0.5)"
				+ "(= (zot-cp (-p- a) (-p- b)) 0.3)"
				+ "(!! (= 0.7 (zot-cp (!! (-p- a)) (-p- b))))"
				+ ")";
		mustBeUNSAT(s);
	}

	@Test
	public void test3() {
		PltlFormula.bound = 3;
		String s = "FORMULA:"
				+ "(&& "
				+ "(-p- a)"
				+ "(= (zot-p (&& (-p- a) (-p- b))) 0.9)"
				+ "(= (zot-p (&& (-p- a) (-p- c))) 0.1)"
				+ "(!! (= 0.82 (zot-p (|| (&& (-p- b) (!! (-p- c))) (&& (!! (-p- b)) (-p- c))))))"
				+ ")";
		mustBeUNSAT(s);
	}

	@Test
	public void test31() {
		PltlFormula.bound = 3;
		String s = "FORMULA:"
				+ "(!! (|| "
				+ "(-p- b)"
				+ "(-p- a) "
				+ "(!! (-p- a))"
				+ "))";
		mustBeUNSAT(s);
	}

	@Test
	public void test32() {
		PltlFormula.bound = 3;
		String s = "FORMULA:"
				+ "(&& "
				+ "(-p- a) "
				+ "(!! (-p- a))"
				+ ")";
		mustBeUNSAT(s);
	}

	@Test
	public void test4() {
		PltlFormula.bound = 3;
		String s = "FORMULA:"
				+ "(&&"
				+ "(<-> (-p- a) (-p- b))"
				+ "(!! (= (zot-p (-p- a)) (zot-p (-p- b))))"
				+ ")";
		mustBeUNSAT(s);
	}

	@Test
	public void test5() {
		PltlFormula.bound = 3;
		String s = "FORMULA:"
				+ "(&& "
				+ "(= (zot-p (-p- head)) 0.5)"
				+ "(= (zot-p (next (-p- head))) 0.5)"
				+ "(!! (= 0.25 (zot-p (&& (-p- head) (next (-p- head))))))"
				+ ")";

		mustBeUNSAT(s);
	}

	@Test
	public void bayes1() {
		PltlFormula.bound = 3;
		String s = "(&&"
				+ "(dep (-p- c) (-p- a))"
				+ "(dep (-p- c) (-p- b))"
				+ ")"
				+ "FORMULA:"
				+ "(&&"
				+ "(= (zot-p (-p- a)) 0.5)"
				+ "(= (zot-p (-p- b)) 0.6)"
				+ "(= (zot-cp (-p- c) (&& (-p- a) (-p- b))) 0.9)"
				+ "(= (zot-cp (-p- c) (&& (-p- a) (!! (-p- b)))) 0.8)"
				+ "(!! (= (zot-p (&& (-p- a) (!! (-p- c)))) 0.07))"
				+ ")";

		mustBeUNSAT(s);
	}
	
	//FIXME
	// if f1 and f2 are connected (regardless of arrow direction in the bayes network), P((&& f1 f2)) shouldn't be expanded to P(f1).P(f2).
	@Test
	public void bayes2() {
		PltlFormula.bound = 0;
		String s = "(&& "
				+ "		(dep (-p- c) (-p- b))"
				+ "		(dep (-p- b) (-p- a))"
				+ ")"
				+ "FORMULA:"
				+ "(&& "
				+ "		(!! (= (zot-p (&& (-p- a) (-p- c))) (zot-p (|| (&& (-p- a) (!! (-p- b)) (-p- c)) (&& (-p- a) (-p- b) (-p- c)))) ))"
				
				
				+ ")";
		mustBeSAT(s);
	}

	@Test
	public void yesterday() {
		PltlFormula.bound = 3;
		String s = "FORMULA:"
				+ "(&&"
				+ "(|| (yesterday (next (yesterday (|| (!! (-p- a)) (-p- a)))))"
				+ "(-p- b))"
				+ "(!! (= (zot-p (-p- b)) 1.0))"
				+ ")";

		mustBeUNSAT(s);
	}

	@Test
	public void falseF() {
		PltlFormula.bound = 3;
		String s = "FORMULA:"
				+ "(&&"
				+ "(&& (!! (-p- a)) (-p- a))"
				+ ")";

		mustBeUNSAT(s);
	}

	@Test// <YesterdayDep>
	public void yesterdayDep() {
		PltlFormula.bound = 3;
		String s = "(alw (dep (-p- a) (yesterday (-p- a))))"
				+ "FORMULA:"
				+ "(&&"
				+ "(alw (= (zot-cp (-p- a) (!! (yesterday (-p- a)))) 0.9))"
				+ "(!! (= (zot-p (-p- a)) 0.9))"
				+ ")";

		mustBeUNSAT(s);
	}

	@Test
	public void yesterdayDepChain() {
		PltlFormula.bound = 3;
		String s = "(alw (dep (-p- a) (yesterday (-p- a))))"
				+ "FORMULA:"
				+ "(&&"
				+ "(alw (= (zot-cp (-p- a) (yesterday (-p- a))) 0.4))"
				+ "(alw (= (zot-cp (-p- a) (!! (yesterday (-p- a)))) 0.9))"
				+ ")";

		mustBeSAT(s);
		s = "(alw (dep (-p- a) (yesterday (-p- a))))"
				+ "FORMULA:"
				+ "(&&"
				+ "		(alw (= (zot-cp (-p- a) (yesterday (-p- a))) 0.4))"
				+ "		(alw (= (zot-cp (-p- a) (!! (yesterday (-p- a)))) 0.9))"
				+ "		(|| "
				+ "			(!! (= (zot-p (-p- a)) 0.9))"
				+ "			(!! (= (zot-p (next (-p- a))) 0.45))"
				+ "			(!! (= (zot-p (futr (-p- a) 2)) 0.675))"
				+ "		)"
				+ ")";

		mustBeUNSAT(s);
	}

	@Test
	public void yYDep() {
		PltlFormula.bound = 3;
		String s = "(alw (&& "
				+ "		(dep (-p- a) (yesterday (yesterday (-p- a))))"
				+ "))"
				+ "FORMULA:"
				+ "(&&"
				+ "	(alw (&& "
				+ "		(= (zot-cp (-p- a) (!! (yesterday (yesterday (-p- a))))) 0.5)"
				+ "		(= (zot-cp (-p- a) (yesterday (yesterday (-p- a)))) 0.1)"
				+ "	))"
				+ "(> (zot-p (-p- a)) 0)"
				+ ")";

		mustBeSAT(s);

		s = "(alw (&& "
				+ "		(dep (-p- a) (yesterday (yesterday (-p- a))))"
				+ "))"
				+ "FORMULA:"
				+ "(&&"
				+ "	(alw (&& "
				+ "		(= (zot-cp (-p- a) (!! (yesterday (yesterday (-p- a))))) 0.5)"
				+ "		(= (zot-cp (-p- a) (yesterday (yesterday (-p- a)))) 0.1)"
				+ "	))"
				+ "(!! (&&"
				+ "		(= (zot-p (-p- a)) 0.5)"
				+ "		(= (zot-p (futr (-p- a) 1)) 0.5)"
				+ "		(= (zot-p (futr (-p- a) 2)) 0.3)"
				+ "		(= (zot-p (futr (-p- a) 3)) 0.3)"
				+ "))"
				+ ")";

		mustBeUNSAT(s);
	}

	@Test
	public void yYDep12341341234134() {
		PltlFormula.bound = 3;
		String s = "(alw (&& "
				//				+ "		(dep (-p- a) (yesterday (-p- a)))"
				+ "		(dep (-p- a) (yesterday (yesterday (-p- a))))"
				+ ") )"
				+ "FORMULA:"
				+ "(&&"
				+ "	(alw (&& "
				+ "		(= (zot-cp (-p- a) (!! (yesterday (yesterday (-p- a))))) 0.5)"
				+ "		(= (zot-cp (-p- a) (yesterday (yesterday (-p- a)))) 0.1)"
				+ "	))"
				//				+ "(!! (= (zot-p (-p- a)) 0.5))"
				+ "(> (zot-p (-p- a)) 0)"
				+ ")";
		mustBeSAT(s);
	}

	@Test
	public void withinF() {
		PltlFormula.bound = 4;
		String s = "FORMULA:"
				+ "(&&"
				+ "(withinf (-p- a) 2)"
				+ "(!! (= (zot-p (|| (next (-p- a)) (futr (yesterday (yesterday (-p- a))) 4))) (zot-p (withinf (-p- a) 2))))"
				+ ")";
		mustBeUNSAT(s);
		PltlFormula.bound = 3;
		mustBeSAT(s);
	}

	@Test
	public void nextYesterday() {
		PltlFormula.bound = 2;
		String s = "FORMULA:"
				+ "(&&"
				+ "(> (zot-p (next (yesterday (-p- a)))) 0)"
				+ "(= (zot-p (next (yesterday (-p- a)))) (zot-p (yesterday (next (-p- a)))))"
				+ ")";
		mustBeUNSAT(s);
	}

	@Test
	public void simplification() {
		PltlFormula.bound = 3;
		String s = "FORMULA:"
				+ "(&&"
				+ "(-p- a) (!! (futr (-p- a) 0))"
				+ ")";

		mustBeUNSAT(s);
	}

	private void mustBeUNSAT(String s) {
		Parser p = new Parser();
		boolean result = p.Parse(s);
		assertTrue(result);
	}

	private void mustBeSAT(String s) {
		Parser p = new Parser();
		boolean result = p.Parse(s);
		assertFalse(result);
	}

}
