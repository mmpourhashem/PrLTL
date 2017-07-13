package pltl.trio;

import pltl.bool.BooleanFormulae;

public class SomF implements BooleanFormulae {

    BooleanFormulae f;

    public SomF(BooleanFormulae f) {
        this.f = f;
    }

    @Override
    public String toString() {
        return "(somf " + f + ")";
    }
    
    //TODO: implements equals for all operators.
}
