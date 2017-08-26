package pltl.bool;

import java.util.ArrayList;

import arith.ArithFormula;
import arith.Constant;
import arith.Op;
import pltl.PltlFormula;
import pltl.Prob;
import pltl.Probability;
import pltl.trio.Dist;
import pltl.trio.Predicate;
import pltl.trio.Release;
import pltl.trio.Until;

public class Not implements Formula {

	private Formula f;

	public Not(Formula f) {
		this.f = f;
		//TODO del
		if (f==null)
			this.f=null;
	}

	public Formula getFormula(){
		return f;
	}

	public String getSemantics(){
		//		return getPropSemantics(f) + getProbSemantics(f);

		//TODO Do the same for all other operators (not to produce probability semantics for ProbExps.
		String s = getPropSemantics(f);
		if (! Probability.hasProbExp(f))
			s += getProbSemantics(f);
		return s;
	}

	public String getTheNegSemantics(){
		if (getTheNeg() != null){
			if (getTheNeg().equals(this))
				return getSemantics();
			else if (PltlFormula.add(getTheNeg()) == -1)
				return getPropSemantics(getTheNeg()) + getProbSemantics(getTheNeg());
		}
		return "";
	}

	private String getProbSemantics(Formula fma) {
		String s = "";
		int mainF = PltlFormula.add(this);
		int innerF = PltlFormula.add(fma);
		for (int time = 0; time <= PltlFormula.bound; time++)
			s += new ArithFormula(Op.EQ, new Constant((float) 1.0), new ArithFormula(Op.PLUS, new Prob(time, mainF), new Prob(time, innerF))).toString();

		return s;
	}

	public String getPropSemantics(Formula fma){
		String s = ";" + toString() + "\n";
		int mainF = PltlFormula.add(this);
		for (int time = 0; time <= PltlFormula.bound; time++){
			s += "(= (zot " + time + " " + mainF + ") (not (zot " + time + " " + PltlFormula.add(fma) + ")))\n";
		}

		return s;
	}

	public Formula getTheNeg(){
		if (f instanceof Not)//(!! (!! (zot-p i))) = (zot-p i)// We do not need this, since the semantics are already produced.
			return f;

		if (f instanceof And){
			ArrayList<Formula> bfa = ((And) f).getFormulae();
			Or or = new Or();
			for (Formula bf:bfa)
				or.addFormula(new Not(bf));
			return or;
		}

		if (f instanceof Or){
			ArrayList<Formula> bfo = ((Or) f).getFormulae();
			And and = new And();
			for (Formula bf:bfo)
				and.addFormula(new Not(bf));
			return and;
		}

		if (f instanceof Implies)
			return new And (((Implies) f).getFormula1(), new Not(((Implies) f).getFormula2()));

		if (f instanceof Until)
			return new Release(new Not(((Until) f).getFormula1()), new Not(((Until) f).getFormula2()));



		//TODO 	
		/*'->' | '<->' | 'until' | 'since' | 'release' | 'trigger'
			'next' | 'yesterday' | 'alw' | 'som' | 'alwf' | 'somf' | 'alwp' | 'somp'
			'until' | 'since' | 'release' | 'trigger'
			'futr' | 'past' | 'withinf' | 'withinp' | 'lasts' | 'lasted'
		 */

		if (f instanceof Predicate || f instanceof Dist)
			return f;//new Not((Predicate) f);


		return null;
	}

	public Formula get(int offset) {
		return new Not(f.get(offset));
	}

	@Override
	public String toString() {
		return "(!! " + f + ")";
	}

	@Override
	public boolean equals(Object o){
		if (o instanceof Not)
			return f.equals(((Not) o).getFormula());

		return false;
	}

}
