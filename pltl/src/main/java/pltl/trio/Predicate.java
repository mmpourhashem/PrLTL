package pltl.trio;


import java.util.HashSet;

import pltl.PltlFormula;
import pltl.Prob;
import pltl.bool.Atom;

public class Predicate implements Atom{

	public static HashSet<Predicate> instances=new HashSet<Predicate>();

	String predicatename;
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

	@Override
	public String toString() {
		return "(-P- " + predicatename + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Predicate)
			return ((Predicate) o).predicatename.toUpperCase().equals(predicatename.toUpperCase());

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
