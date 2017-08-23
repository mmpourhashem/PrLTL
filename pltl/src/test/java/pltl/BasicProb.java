package pltl;

import static org.junit.Assert.*;

import org.junit.Test;

public class BasicProb {

	@Test
	public void test1() {
		String s = "FORMULA:"
				+ "(&& "
				+ "(> (zot-p (&& (-p- a) (-p- b))) (zot-p (-p- a)))"
				+ ")";
		mustBeUNSAT(s);
	}
	
	@Test
	public void test2() {
		String s = "FORMULA:"
				+ "(&&"
				+ "(= (zot-p (-p- b)) 0.5)"
				+ "(= (zot-cp (-p- a) (-p- b)) 0.3)"
				+ "(!! (= 0.7 (zot-cp (!! (-p- a)) (-p- b))))"
				+ ")";
		mustBeUNSAT(s);
	}
	
	@Test
	public void test3() {
		String s = "FORMULA:"
				+ "(&& "
				+ "(-p- a) "
				+ "(= (zot-p (&& (-p- a) (-p- b))) 0.9) "
				+ "(= (zot-p (&& (-p- a) (-p- c))) 0.1) "
				+ "(|| (&& (-p- b) (!! (-p- c))) (&& (!! (-p- b)) (-p- c)))"
				+ "(<-> (-p- d) (&& (-p- b) (!! (-p- c))))"
				+ "(= (zot-p (&& (-p- b) (-p- c) )) 0.1) "
				+ ")";
		mustBeUNSAT(s);
	}
	
	@Test
	public void test31() {
		String s = "FORMULA:"
				+ "(!! (|| (-p- b)"
				+ "(-p- a) "
				+ "(!! (-p- a))"
				+ "))";
		mustBeUNSAT(s);
	}
	
	@Test
	public void test32() {
		String s = "FORMULA:"
				+ "(&& "
				+ "(-p- a) "
				+ "(!! (-p- a))"
				+ ")";
		mustBeUNSAT(s);
	}
	
	@Test
	public void test4() {
		String s = "FORMULA:"
				+ "(&&"
				+ "(<-> (-p- a) (-p- b))"
				+ "(!! (= (zot-p (-p- a)) (zot-p (-p- b))))"
				+ ")";
		mustBeUNSAT(s);
	}
	
	@Test
	public void test5() {
		String s = "FORMULA:"
				+ "(&& "
				+ "(= (zot-p (-p- head)) 0.5)"
				+ "(= (zot-p (next (-p- head))) 0.5)"
				+ "(!! (= 0.25 (zot-p (&& (-p- head) (next (-p- head))))))"
				+ ")";
		
		mustBeUNSAT(s);
	}
	
	@Test
	public void factorGraph() {
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

//TODO Take care of dist dists while preprocessing formulae for LTL transformations (lasts (-p- a) 2) => (&& (futr (-p- a) 1) (futr (-p- a) 2))
	@Test
	public void yesterday() {
		String s = "FORMULA:"
				+ "(&&"
				+ "(|| (yesterday (next (yesterday (|| (!! (-p- a)) (-p- a)))))"
				+ "(-p- b))"
				+ "(!! (= (zot-p (-p- b)) 1.0))"
				+ ")";
		
		mustBeUNSAT(s);
	}
	
	@Test
	public void yesterdayDep() {/////////////////////////////////////////////////fix
		String s = "(alw (dep (-p- a) (yesterday (-p- a))))"
				+ "FORMULA:"
				+ "(&&"//there must be an alw here
				+ "(alw (= (zot-cp (-p- a) (yesterday (-p- a))) 0.4))"
				+ "(alw (= (zot-cp (-p- a) (!! (yesterday (-p- a)))) 0.9))"
//				+ "(!! (= (zot-p (-p- a)) 0.9))"
				+ ")";
		
		mustBeUNSAT(s);
	}
	
	@Test
	public void withinF() {
		String s = "FORMULA:"
				+ "(&&"//there must be an alw here
				+ "(withinf (-p- a) 2)"
//				+ "(> (zot-p (withinf (-p- a) 2)) 0.5)"
				+ "(> (zot-p (next (-p- a)) 0.5))"
////				+ "(!! (= (zot-p (-p- a)) 0.9))"
				+ ")";

		mustBeUNSAT(s);
	}

	private void mustBeUNSAT(String s) {
		Parser p = new Parser();
		boolean result = p.Parse(s);
		//must be UNSAT
		assertTrue(result);
	}
	
}
