/**
 * @author Mohammad Mehdi Pourhashem Kallehbasti
 */

package pltl.trio;


import java.util.HashSet;

import pltl.DepTempFormula;
import pltl.PltlFormula;
import pltl.Prob;
import pltl.Prop;
import pltl.bool.Atom;
import pltl.bool.Formula;
import pltl.bool.Not;

public class Predicate implements Atom, DepTempFormula{

	public static HashSet<Predicate> instances=new HashSet<Predicate>();

	private String predicatename;
	public Predicate() {
	}

	public Predicate(String predicatename) {
		this.predicatename=predicatename;
		instances.add(this);
	}

	public void setPredicateName(String predicatename) {
		this.predicatename=predicatename;
		instances.add(this);
	}

	public String getPredicateName() {
		return predicatename;
	}

	public String getSemantics() {
		//ProbSemantics:
			String s = ";" + this.toString() + "\n";
			int index = PltlFormula.add(this);
			for (int time = 0; time <= PltlFormula.bound; time++)
				s += new Prob(time, index).getSemantics(time);

			return s;
	}
	
	public Prob getProbForDep(int offset, boolean neg) {
		if (neg)
			return new Prob(offset, PltlFormula.add(new Not(this)));
		
    	return new Prob(offset, PltlFormula.add(this));
    }
	
	public Formula get(int offset) {
		if (PltlFormula.outOfBound(offset))
			return new PltlFormula.False();
		
		return new Dist(this, offset);
	}
	
	public Formula getProp(int offset) {
		if (PltlFormula.outOfBound(offset))
    		return new PltlFormula.PropFalse();
		
		return new Prop(offset, PltlFormula.add(this));
	}

	@Override
	public String toString() {
		return "(-P- " + predicatename + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Predicate)
			return (predicatename.toUpperCase().equals(((Predicate) o).getPredicateName().toUpperCase()));

		return false;
	}    

	@Override
	public int hashCode() {
		try {
			return this.predicatename.toUpperCase().hashCode();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}
}
