package pltl.trio;


import java.util.HashSet;

import pltl.bool.Atom;

public class Predicate implements Atom{

    public static HashSet<Predicate> instances=new HashSet<Predicate>();
    
    String predicatename;

    /**
     * Creates the predicate without setting the actual name
     * This has been done for state diagrams in particular
     * where the predicates of the states should have the prefix
     * of the object involved
     */
    public Predicate(){
      // since the predicate name is necessary to compute the hash value of the
      // object, this statement cannot be executed in this constructor, but only
      // when the predicate name is set (hence in method setPredicateName)
        // this.instances.add(this);
    }

    /**
     * Creates the predicate and sets the name
     * @param name
     * the name of the predicate
     */
    public Predicate(String predicatename){
        this.predicatename=predicatename;
        instances.add(this);
    }

    /**
     * Sets the name of the predicate
     * @param name
     * The name of the predicate
     */
    public void setPredicateName(String predicatename){
        this.predicatename=predicatename;
        instances.add(this);
    }

    /**
     * Returns the name of the predicate
     * @return
     * the name of the predicate
     */
    public String getPredicateName(){
        return predicatename;
    }

    @Override
    public String toString() {
    	return "(-P- " +predicatename+ ")";
    }
    
    @Override
	public boolean equals(Object o){
        if(o instanceof Predicate){
            return ((Predicate)o).predicatename.toUpperCase().equals(predicatename.toUpperCase());
        }
        return false;
    }    

    @Override
	public int hashCode(){
      try {
		return this.predicatename.toUpperCase().hashCode();
	} catch (Exception e) {
		e.printStackTrace();
	}
      return 0;
    }
}
