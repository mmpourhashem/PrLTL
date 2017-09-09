package pltl.trio;

import pltl.PltlFormula;
import pltl.bool.And;
import pltl.bool.Formula;
import pltl.bool.Or;
import pltl.bool.PropAnd;
import pltl.bool.PropOr;

public class Release implements Formula {

	private Formula f1;
	private Formula f2;

	public Release(Formula f1, Formula f2) {
		this.f1 = f1;
		this.f2 = f2;
	}

	public Formula getFormula1() {
		return f1;
	}

	public Formula getFormula2() {
		return f2;
	}

	//	public Formula get(int offset) {
	//		if (PltlFormula.outOfBound(offset))
	//			return new PltlFormula.False();
	//		
	//		Or or = new Or();
	//		or.addFormula(new And(f1.get(offset), f2.get(offset)));
	//		for (int time = offset + 1; time <= PltlFormula.bound; time++) {
	//			And and = new And();
	//			for (int b = offset; b <= time; b++)
	//				and.addFormula(f2.get(b));
	//			and.addFormula(f1.get(time));
	//			or.addFormula(and);
	//		}
	//
	//		return or;
	//	}
	//	
	//	public Formula getProp(int offset) {
	//		if (PltlFormula.outOfBound(offset))
	//    		return new PltlFormula.PropFalse();
	//		PropOr pOr = new PropOr();
	//		pOr.addFormula(new PropAnd(f1.getProp(offset), f2.getProp(offset)));
	//		for (int time = offset + 1; time <= PltlFormula.bound; time++) {
	//			PropAnd pAnd = new PropAnd();
	//			for (int b = offset; b <= time; b++)
	//				pAnd.addFormula(f2.getProp(b));
	//			pAnd.addFormula(f1.getProp(time));
	//			pOr.addFormula(pAnd);
	//		}
	//		
	//		return pOr;
	//	}

	public Formula get(int offset) {
		if (PltlFormula.outOfBound(offset))
			return new PltlFormula.False();

		if (offset == PltlFormula.bound)
			return f2.get(offset);

		return new And(f2.get(offset), new Or(f1.get(offset), this.get(offset + 1))); 
	}

	public Formula getProp(int offset) {
		if (PltlFormula.outOfBound(offset))
			return new PltlFormula.PropFalse();

		if (offset == PltlFormula.bound)
			return f2.getProp(offset);

		return new PropAnd(f2.getProp(offset), new PropOr(f1.getProp(offset), this.getProp(offset + 1))); 
	}

	@Override
	public String toString() {
		return "(release " + f1 + " " + f2 + ")";
	}

	@Override
	public boolean equals(Object o){
		if (o instanceof Release)
			return f1.equals(((Release) o).getFormula1()) && f2.equals(((Release) o).getFormula2());

		return false;
	}

}