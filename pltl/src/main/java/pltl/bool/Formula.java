package pltl.bool;

public interface Formula {

    @Override
    public String toString();
    
    /**
     * @param offset
     * @return
     * Formula at offset
     */
    public Formula get(int offset);

    /**
     * @param offset
     * @return
     * SMT2 propositional formula at offset
     */
    public Formula getProp(int offset);
    
    public boolean equals(Object o);
}
