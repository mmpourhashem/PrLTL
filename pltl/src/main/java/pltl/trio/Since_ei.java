package pltl.trio;

import pltl.bool.BooleanFormulae;

public class Since_ei implements BooleanFormulae {

    BooleanFormulae f1;
    BooleanFormulae f2;

    public Since_ei(BooleanFormulae f1, BooleanFormulae f2) {
        this.f1 = f1;
        this.f2 = f2;
    }

    @Override
    public String toString() {
        return "(since_ei " + f1 + " " + f2 + ")";
    }
}