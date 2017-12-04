/**
 * @author Mohammad Mehdi Pourhashem Kallehbasti
 */

package pltl;

import org.junit.Test;

public class BayesNetwork {
	@Test
	public void test1() {
		PltlFormula.bound = 3;
		String s = "(&& "
				+ "		(dep (-p- b) (-p- a))"
				+ "		(dep (-p- c) (-p- a))"
				+ ")"
//				+ "FORMULA:"
//						+ "(&& "
//						+ "(> (zot-p (&& (-p- a) (-p- b))) (zot-p (-p- a)))"
//						+ ")"
;
		Parser p = new Parser();
		boolean result = p.Parse(s);
		String ss ="";
	}
	
	//FIXME
	/*c D a b
	 * d D e
	 * f D c d
	 * a & d?
	 * when two nodes do not share any ancestral member
	 * 
	 */
}
