package pltl.trio;

import pltl.bool.BooleanFormulae;

public class Since implements BooleanFormulae {

    BooleanFormulae f1;
    BooleanFormulae f2;

    public Since(BooleanFormulae f1, BooleanFormulae f2) {
        this.f1 = f1;
        this.f2 = f2;
    }

    @Override
    public String toString() {
        return "(since " + f1 + " " + f2 + ")";
    }
}