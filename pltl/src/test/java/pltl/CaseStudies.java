/**
 * @author Mohammad Mehdi Pourhashem Kallehbasti
 */

package pltl;

import static org.junit.Assert.*;

import org.junit.Test;

public class CaseStudies {

//	@Test
//	public void caseStudy1() {
//		PltlFormula.bound = 0;
//		String s = "(alw (&& "
//				+ "		(dep (-p- w) (yesterday (-p- w)))"
//				+ "		(dep (-p- w) (yesterday (yesterday (-p- w))))"
//				+ "))"
//				+ "FORMULA:"
//				+ "(&& "
//				+ "(alw (&&"
//				+ "		(= (zot-cp (-p- w) (&& (!! (yesterday (yesterday (-p- w)))) (!! (yesterday (-p- w))))) 0.5)"
//				+ "		(= (zot-cp (-p- w) (&& (!! (yesterday (yesterday (-p- w)))) (yesterday (-p- w)))) 0.8)"
//				+ "		(= (zot-cp (-p- w) (&& (yesterday (yesterday (-p- w))) (!! (yesterday (-p- w))))) 0.5)"
//				+ "		(= (zot-cp (-p- w) (&& (yesterday (yesterday (-p- w))) (yesterday (-p- w)))) 0)"
//				+ "))"
//				+ ")";
//		mustBeSAT(s);
//	}
	
//	@Test
//	public void montyHall() {
//		PltlFormula.bound = 0;
//		String s = "(&& "
//				+ "		(dep (-p- c3) (-p- c2))"
//				+ "		(dep (-p- c3) (-p- c1))"
//				+ "		(dep (-p- c2) (-p- c1))"
//				+ "		(dep (-p- h1) (-p- c1))"
//				+ "		(dep (-p- h1) (-p- x1))"
//				
//				+ "		(dep (-p- h2) (-p- c2))"
//				+ "		(dep (-p- h2) (-p- x2))"
//				+ "		(dep (-p- h2) (-p- h1))"
//				
//				+ "		(dep (-p- h3) (-p- c3))"
//				+ "		(dep (-p- h3) (-p- x3))"
//				+ "		(dep (-p- h3) (-p- h1))"
//				+ "		(dep (-p- h3) (-p- h2))"
//				+ ")"
//				+ "FORMULA:"
//				+ "(&& "
//				+ "		(= (zot-cp (-p- c3) (&& (!! (-p- c1)) (-p- c2))) 0)"
//				+ "		(= (zot-cp (-p- c3) (&& (-p- c1) (-p- c2))) 0)"
//				+ "		(= (zot-cp (-p- c3) (&& (-p- c1) (!! (-p- c2)))) 0)"
//				+ "		(= (zot-cp (-p- c3) (&& (-p- c1) (-p- c2))) 0)"
//				+ "		(= (zot-cp (-p- c3) (&& (!! (-p- c1)) (!! (-p- c2)))) 1)"
//				+ "		(= (zot-cp (-p- c2) (-p- c1)) 0)"
//				+ "		(= (zot-p (-p- c1)) (zot-p (-p- c2)))"
//				+ "		(= (zot-p (-p- c1)) (zot-p (-p- c3)))"
//				+ "		(= (zot-p (-p- c2)) (zot-p (-p- c3)))"
//				+ ""
//				+ "		(= (zot-cp (-p- h1) (&& (!! (-p- c1)) (!! (-p- x1)))) 1)"
//				+ "		(= (zot-cp (-p- h1) (&& (!! (-p- c1)) (-p- x1))) 0)"
//				+ "		(= (zot-cp (-p- h1) (&& (-p- c1) (!! (-p- x1)))) 0)"
//				+ "		(= (zot-cp (-p- h1) (&& (-p- c1) (-p- x1))) 0)"
//				+ ""
//				+ "		(= (zot-cp (-p- h2) (&& (!! (-p- c2)) (!! (-p- x2)))) 1)"
//				+ "		(= (zot-cp (-p- h2) (&& (!! (-p- c2)) (-p- x2))) 0)"
//				+ "		(= (zot-cp (-p- h2) (&& (-p- c2) (!! (-p- x2)))) 0)"
//				+ "		(= (zot-cp (-p- h2) (&& (-p- c2) (-p- x2))) 0)"
//				+ ""
//				+ "		(= (zot-cp (-p- h3) (&& (!! (-p- c3)) (!! (-p- x3)))) 1)"
//				+ "		(= (zot-cp (-p- h3) (&& (!! (-p- c3)) (-p- x3))) 0)"
//				+ "		(= (zot-cp (-p- h3) (&& (-p- c3) (!! (-p- x3)))) 0)"
//				+ "		(= (zot-cp (-p- h3) (&& (-p- c3) (-p- x3))) 0)"
//				+ ""
//				+ "		(= (zot-cp (-p- h3) (&& (!! (-p- c3)) (!! (-p- x3)) (!! (-p- h1)) (!! (-p- h2)))) 1)" //0000
//				+ "		(= (zot-cp (-p- h3) (&& (!! (-p- c3)) (!! (-p- x3)) (!! (-p- h1)) (-p- h2))) 0)" //0001
//				+ "		(= (zot-cp (-p- h3) (&& (!! (-p- c3)) (!! (-p- x3)) (-p- h1) (!! (-p- h2)))) 0)" //0010
//				+ "		(= (zot-cp (-p- h3) (&& (!! (-p- c3)) (!! (-p- x3)) (-p- h1) (-p- h2))) 0)" //0011
//				+ "		(= (zot-cp (-p- h3) (&& (!! (-p- c3)) (-p- x3) (!! (-p- h1)) (!! (-p- h2)))) 0)" //0100
//				+ "		(= (zot-cp (-p- h3) (&& (!! (-p- c3)) (-p- x3) (!! (-p- h1)) (-p- h2))) 0)" //0101
//				+ "		(= (zot-cp (-p- h3) (&& (!! (-p- c3)) (-p- x3) (-p- h1) (!! (-p- h2)))) 0)" //0110
//				+ "		(= (zot-cp (-p- h3) (&& (!! (-p- c3)) (-p- x3) (-p- h1) (-p- h2))) 0)" //0111
//				+ "		(= (zot-cp (-p- h3) (&& (-p- c3) (!! (-p- x3)) (!! (-p- h1)) (!! (-p- h2)))) 0)" //1000
//				+ "		(= (zot-cp (-p- h3) (&& (-p- c3) (!! (-p- x3)) (!! (-p- h1)) (-p- h2))) 0)" //1001
//				+ "		(= (zot-cp (-p- h3) (&& (-p- c3) (!! (-p- x3)) (-p- h1) (!! (-p- h2)))) 0)" //1010
//				+ "		(= (zot-cp (-p- h3) (&& (-p- c3) (!! (-p- x3)) (-p- h1) (-p- h2))) 0)" //1011
//				+ "		(= (zot-cp (-p- h3) (&& (-p- c3) (-p- x3) (!! (-p- h1)) (!! (-p- h2)))) 0)" //1100
//				+ "		(= (zot-cp (-p- h3) (&& (-p- c3) (-p- x3) (!! (-p- h1)) (-p- h2))) 0)" //1101
//				+ "		(= (zot-cp (-p- h3) (&& (-p- c3) (-p- x3) (-p- h1) (!! (-p- h2)))) 0)" //1110
//				+ "		(= (zot-cp (-p- h3) (&& (-p- c3) (-p- x3) (-p- h1) (-p- h2))) 0)" //1111
//				+ ""
////				+ "		(= (zot-cp (-p- h2) (&& (!! (-p- c2)) (!! (-p- x2)) (!! (-p- h1)))) 1)" //000 //It must be relaxed.
//				+ "		(= (zot-cp (-p- h2) (&& (!! (-p- c2)) (!! (-p- x2)) (-p- h1))) 0)" //001
//				+ "		(= (zot-cp (-p- h2) (&& (!! (-p- c2)) (-p- x2) (!! (-p- h1)))) 0)" //010
//				+ "		(= (zot-cp (-p- h2) (&& (!! (-p- c2)) (-p- x2) (-p- h1))) 0)" //011
//				+ "		(= (zot-cp (-p- h2) (&& (-p- c2) (!! (-p- x2)) (!! (-p- h1)))) 0)" //100
//				+ "		(= (zot-cp (-p- h2) (&& (-p- c2) (!! (-p- x2)) (-p- h1))) 0)" //101
//				+ "		(= (zot-cp (-p- h2) (&& (-p- c2) (-p- x2) (!! (-p- h1)))) 0)" //110
//				+ "		(= (zot-cp (-p- h2) (&& (-p- c2) (-p- x2) (-p- h1))) 0)" //111
//				
//				
//				+ ")";
//		mustBeSAT(s);
//	}
//	@Test
//	public void montyHall() {
//		PltlFormula.bound = 0;
//		String s = "(&& "
//				+ "		(dep (-p- c0) (-p- c1))"
//				+ "		(dep (-p- x0) (-p- x1))"
//				+ "		(dep (-p- h0) (-p- h1))"
//				+ "		(dep (-p- h0) (-p- c0))"
//				+ "		(dep (-p- h0) (-p- c1))"
//				+ "		(dep (-p- h0) (-p- x0))"
//				+ "		(dep (-p- h0) (-p- x1))"
//				+ "		(dep (-p- h1) (-p- c0))"
//				+ "		(dep (-p- h1) (-p- c1))"
//				+ "		(dep (-p- h1) (-p- x0))"
//				+ "		(dep (-p- h1) (-p- x1))"
//				+ ")"
//				+ "FORMULA:"
//				+ "(&& "
//				+ "(!! (-p- h1)) (!! (-p- h0))"
//				+ "(= (zot-cp (-p- c0) (!! (-p- c1))) 1)" //Mutual exclusiveness for Car
//				+ "(= (zot-cp (-p- h0) (!! (-p- h1))) 1)" //Mutual exclusiveness for Host
//				+ "(= (zot-cp (-p- x0) (!! (-p- x1))) 1)" //Mutual exclusiveness for Choice
////				// CP for h1:
////				+ "(= (zot-cp (-p- h1) (&& (!! (-p- x1)) (!! (-p- x0)))) 0)" //No 00 as Host code
////				+ "(= (zot-cp (-p- h1) (&& (!! (-p- c1)) (!! (-p- c0)))) 0)" //No 00 as Car code
////				+ "(= (zot-cp (-p- h1) (&& (!! (-p- c1)) (-p- c0) (!! (-p- x1)) (-p- x0))) 1)"
////				+ "(= (zot-cp (-p- h1) (&& (!! (-p- c1)) (-p- c0) (-p- x1) (!! (-p- x0)))) 1)"
////				+ "(= (zot-cp (-p- h1) (&& (!! (-p- c1)) (-p- c0) (-p- x1) (-p- x0))) 1)"
////				+ "(= (zot-cp (-p- h1) (&& (-p- c1) (!! (-p- c0)) (!! (-p- x1)) (-p- x0))) 1)"
////				+ "(= (zot-cp (-p- h1) (&& (-p- c1) (!! (-p- c0)) (-p- x1) (!! (-p- x0)))) 0.5)"
////				+ "(= (zot-cp (-p- h1) (&& (-p- c1) (!! (-p- c0)) (-p- x1) (-p- x0))) 0)"
////				+ "(= (zot-cp (-p- h1) (&& (-p- c1) (-p- c0) (!! (-p- x1)) (-p- x0))) 1)"
////				+ "(= (zot-cp (-p- h1) (&& (-p- c1) (-p- c0) (-p- x1) (!! (-p- x0)))) 0)"
////				+ "(= (zot-cp (-p- h1) (&& (-p- c1) (-p- c0) (-p- x1) (-p- x0))) 0.5)"
////				// CP for h0:
////				+ "(= (zot-cp (-p- h1) (&& (!! (-p- x1)) (!! (-p- x0)))) 0)" //No 00 as Host code
////				+ "(= (zot-cp (-p- h1) (&& (!! (-p- c1)) (!! (-p- c0)))) 0)" //No 00 as Host code
////				+ "(= (zot-cp (-p- h1) (!! (-p- h1))) 1)"
////				+ "(= (zot-cp (-p- h1) (&& (!! (-p- c1)) (-p- c0) (!! (-p- x1)) (-p- x0) (-p- h1))) 0.5)"
////				+ "(= (zot-cp (-p- h1) (&& (!! (-p- c1)) (-p- c0) (-p- x1) (!! (-p- x0)) (-p- h1))) 1)"
////				+ "(= (zot-cp (-p- h1) (&& (!! (-p- c1)) (-p- c0) (-p- x1) (-p- x0) (-p- h1))) 0)"
////				+ "(= (zot-cp (-p- h1) (&& (-p- c1) (!! (-p- c0)) (!! (-p- x1)) (-p- x0) (-p- h1))) 1)"
////				+ "(= (zot-cp (-p- h1) (&& (-p- c1) (!! (-p- c0)) (-p- x1) (!! (-p- x0)) (-p- h1))) 1)"
////				+ "(= (zot-cp (-p- h1) (&& (-p- c1) (!! (-p- c0)) (-p- x1) (-p- x0) (-p- h1))) 1)"
////				+ "(= (zot-cp (-p- h1) (&& (-p- c1) (-p- c0) (!! (-p- x1)) (-p- x0) (-p- h1))) 0)"
////				+ "(= (zot-cp (-p- h1) (&& (-p- c1) (-p- c0) (-p- x1) (!! (-p- x0)) (-p- h1))) 1)"
////				+ "(= (zot-cp (-p- h1) (&& (-p- c1) (-p- c0) (-p- x1) (-p- x0) (-p- h1))) 0.5)"
//				
//				+ ")";
//		mustBeSAT(s);
//	}
	
	@Test
	public void montyHall() {
		PltlFormula.bound = 0;
		String s = "FORMULA:"
				+ "(&& "
				+ "(!! (-p- h1)) (!! (-p- h0))"
				+ "(>= (zot-p (!! (-p- h1))) 0)"
//				// CP for h1:
//				+ "(= (zot-cp (-p- h1) (&& (!! (-p- x1)) (!! (-p- x0)))) 0)" //No 00 as Host code
//				+ "(= (zot-cp (-p- h1) (&& (!! (-p- c1)) (!! (-p- c0)))) 0)" //No 00 as Car code
//				+ "(= (zot-cp (-p- h1) (&& (!! (-p- c1)) (-p- c0) (!! (-p- x1)) (-p- x0))) 1)"
//				+ "(= (zot-cp (-p- h1) (&& (!! (-p- c1)) (-p- c0) (-p- x1) (!! (-p- x0)))) 1)"
//				+ "(= (zot-cp (-p- h1) (&& (!! (-p- c1)) (-p- c0) (-p- x1) (-p- x0))) 1)"
//				+ "(= (zot-cp (-p- h1) (&& (-p- c1) (!! (-p- c0)) (!! (-p- x1)) (-p- x0))) 1)"
//				+ "(= (zot-cp (-p- h1) (&& (-p- c1) (!! (-p- c0)) (-p- x1) (!! (-p- x0)))) 0.5)"
//				+ "(= (zot-cp (-p- h1) (&& (-p- c1) (!! (-p- c0)) (-p- x1) (-p- x0))) 0)"
//				+ "(= (zot-cp (-p- h1) (&& (-p- c1) (-p- c0) (!! (-p- x1)) (-p- x0))) 1)"
//				+ "(= (zot-cp (-p- h1) (&& (-p- c1) (-p- c0) (-p- x1) (!! (-p- x0)))) 0)"
//				+ "(= (zot-cp (-p- h1) (&& (-p- c1) (-p- c0) (-p- x1) (-p- x0))) 0.5)"
//				// CP for h0:
//				+ "(= (zot-cp (-p- h1) (&& (!! (-p- x1)) (!! (-p- x0)))) 0)" //No 00 as Host code
//				+ "(= (zot-cp (-p- h1) (&& (!! (-p- c1)) (!! (-p- c0)))) 0)" //No 00 as Host code
//				+ "(= (zot-cp (-p- h1) (!! (-p- h1))) 1)"
//				+ "(= (zot-cp (-p- h1) (&& (!! (-p- c1)) (-p- c0) (!! (-p- x1)) (-p- x0) (-p- h1))) 0.5)"
//				+ "(= (zot-cp (-p- h1) (&& (!! (-p- c1)) (-p- c0) (-p- x1) (!! (-p- x0)) (-p- h1))) 1)"
//				+ "(= (zot-cp (-p- h1) (&& (!! (-p- c1)) (-p- c0) (-p- x1) (-p- x0) (-p- h1))) 0)"
//				+ "(= (zot-cp (-p- h1) (&& (-p- c1) (!! (-p- c0)) (!! (-p- x1)) (-p- x0) (-p- h1))) 1)"
//				+ "(= (zot-cp (-p- h1) (&& (-p- c1) (!! (-p- c0)) (-p- x1) (!! (-p- x0)) (-p- h1))) 1)"
//				+ "(= (zot-cp (-p- h1) (&& (-p- c1) (!! (-p- c0)) (-p- x1) (-p- x0) (-p- h1))) 1)"
//				+ "(= (zot-cp (-p- h1) (&& (-p- c1) (-p- c0) (!! (-p- x1)) (-p- x0) (-p- h1))) 0)"
//				+ "(= (zot-cp (-p- h1) (&& (-p- c1) (-p- c0) (-p- x1) (!! (-p- x0)) (-p- h1))) 1)"
//				+ "(= (zot-cp (-p- h1) (&& (-p- c1) (-p- c0) (-p- x1) (-p- x0) (-p- h1))) 0.5)"
				
				+ ")";
		mustBeSAT(s);
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
