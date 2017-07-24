package pltl.trio;


import java.util.ArrayList;
import java.util.HashSet;

import pltl.PltlFormula;
import pltl.Probability;
import pltl.TimeIndex;
import pltl.bool.Atom;
import pltl.bool.BooleanFormulae;

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
    	for (int time = 0; time <= PltlFormula.bound; time++){
    		s += getProbSemantics(time);
    	}
    	return s;
    }
    private String getProbSemantics(int time) {
    	String s = "";
    	TimeIndex ti = new TimeIndex(time, PltlFormula.add(this));
    	ArrayList<TimeIndex> parents = ti.getParents();
    	for (BooleanFormulae f:Probability.populate(parents) )
		return s;
	}

	@Override
    public String toString() {
    	return "(-P- " +predicatename+ ")";
    }
    
    @Override
	public boolean equals(Object o) {
        if(o instanceof Predicate) {
            return ((Predicate)o).predicatename.toUpperCase().equals(predicatename.toUpperCase());
        }
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
