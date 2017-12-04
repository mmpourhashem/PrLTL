/**
 * @author Mohammad Mehdi Pourhashem Kallehbasti
 */

package pltl;

import static org.junit.Assert.*;

import org.junit.Test;

public class TemporalLayer {

	@Test
	public void test1() {
		PltlFormula.bound = 3;
		String s = "FORMULA:"
				+ "(&&"
				+ "(-p- a) "
				+ "(!! (until (-p- a) (-p- b)))"
				+ "(release (-p- a) (-p- b))"
				+ ")";
		mustBeUNSAT(s);
	}
	
	@Test
	public void alw() {
		PltlFormula.bound = 3;
		String s = "FORMULA:"
				+ "(&& (alw (&& (= (zot-p (-p- a)) 1.0) (= (zot-p (&& (-p- a) (-p- b))) 0.98765)))"
				+ "(!! (= (zot-p (-p- b)) 0.98765))"
				+ ")";

		mustBeUNSAT(s);
	}
	
	@Test
	public void until1() {
		PltlFormula.bound = 3;
		String s = "FORMULA:"
				+ "(&& (until (-p- a) (-p- b))"
				+ "(!! (= (zot-p (until (-p- a) (-p- b))) 1))"
				+ ")";

		mustBeUNSAT(s);
	}
	
	@Test
	public void release1() {
		PltlFormula.bound = 3;
		String s = "FORMULA:"
				+ "(&& (release (-p- a) (-p- b))"
				+ "(!! (= (zot-p (release (-p- a) (-p- b))) 1))"
				+ ")";

		mustBeUNSAT(s);
	}
	
	@Test
	public void untilRelease1() {
		PltlFormula.bound = 3;
		String s = "FORMULA:"
				+ "(&& (until (-p- a) (-p- b))"
				+ "(!! (= (zot-p (release (!! (-p- a)) (!! (-p- b)))) 0))"
				+ ")";

		mustBeUNSAT(s);
	}
	
	@Test
	public void until2() {
		PltlFormula.bound = 3;
		String s = "FORMULA:"
				+ "(&& (until (-p- a) (-p- b))"
				+ "(!! (-p- b))"
				+ "(!! (= (zot-p (next (until (-p- a) (-p- b)))) 1))"
				+ ")";

		mustBeUNSAT(s);
	}
	
	@Test
	public void sinceTrigger1() {
		PltlFormula.bound = 3;
		String s = "FORMULA:"
				+ "(&& (since (-p- a) (-p- b))"
				+ "(!! (= (zot-p (trigger (!! (-p- a)) (!! (-p- b)))) 0))"
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
