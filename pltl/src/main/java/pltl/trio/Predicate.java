package pltl.trio;


import java.util.HashSet;

import pltl.PltlFormula;
import pltl.Prob;
import pltl.bool.Atom;
import pltl.bool.Formula;

public class Predicate implements Atom{

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
	
	public Formula get(int offset) {
		return new Dist(this, offset);
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
