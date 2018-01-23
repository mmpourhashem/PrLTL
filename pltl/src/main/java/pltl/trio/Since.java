/**
 * @author Mohammad Mehdi Pourhashem Kallehbasti
 */

package pltl.trio;

import pltl.PltlFormula;
import pltl.bool.And;
import pltl.bool.Formula;
import pltl.bool.Or;
import pltl.bool.PropAnd;
import pltl.bool.PropOr;

public class Since implements Formula {

	private Formula f1;
	private Formula f2;

	public Since(Formula f1, Formula f2) {
		this.f1 = f1;
		this.f2 = f2;
	}

	public Formula getFormula2() {
		return f2;
	}

	public Formula getFormula1() {
		return f1;
	}

	@Override
	public String toString() {
		return "(since " + f1 + " " + f2 + ")";
	}

	@Override
	public boolean equals(Object o){
		if (o instanceof Since)
			return f1.equals(((Since) o).getFormula1()) && f2.equals(((Since) o).getFormula2());

		return false;
	}

	public Formula get(int offset) {
		if (PltlFormula.outOfBound(offset))
			return new PltlFormula.False();

		Or or = new Or();
		or.addFormula(f2.get(offset));
		for (int time = offset - 1; time >= 0; time--) {
			And and = new And();
			for (int a = offset; a > time; a--)
				and.addFormula(f1.get(a));
			and.addFormula(f2.get(time));
			or.addFormula(and);
		}

		return or;
	}

	public Formula getProp(int offset) {
		if (PltlFormula.outOfBound(offset))
			return new PltlFormula.PropFalse();

		PropOr pOr = new PropOr();
		pOr.addFormula(f2.getProp(offset));
		for (int time = offset - 1; time >= 0; time--) {
			PropAnd pAnd = new PropAnd();
			for (int a = offset; a > time; a--)
				pAnd.addFormula(f1.getProp(a));
			pAnd.addFormula(f2.getProp(time));
			pOr.addFormula(pAnd);
		}

		return pOr;
	}

}