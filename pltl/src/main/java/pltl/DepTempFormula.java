/**
 * @author Mohammad Mehdi Pourhashem Kallehbasti
 */

package pltl;

/*
 * Formulae that target proper Prob inside a Dep using Next, Yesterday, Futr, Past, Dist operators, or their conjunctions. 
 */
public interface DepTempFormula {

    @Override
    public String toString();
    
    /**
     * @param offset
     * @return
     * Formula at offset
     */
    public Prob getProbForDep(int offset, boolean neg);

    public boolean equals(Object o);
}
