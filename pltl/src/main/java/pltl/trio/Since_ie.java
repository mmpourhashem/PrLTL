package pltl.trio;

import pltl.bool.BooleanFormulae;

public class Since_ie implements BooleanFormulae {

    BooleanFormulae f1;
    BooleanFormulae f2;

    public Since_ie(BooleanFormulae f1, BooleanFormulae f2) {
        this.f1 = f1;
        this.f2 = f2;
    }

    @Override
    public String toString() {
        return "(since_ie " + f1 + " " + f2 + ")";
    }
}