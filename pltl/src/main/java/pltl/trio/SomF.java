package pltl.trio;

import pltl.bool.Formula;

public class SomF implements Formula {

    Formula f;

    public SomF(Formula f) {
        this.f = f;
    }

    @Override
    public String toString() {
        return "(somf " + f + ")";
    }
    
    //TODO: implements equals for all operators.
}
