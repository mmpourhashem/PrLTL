package pltl.trio;

import pltl.bool.BooleanFormulae;

public class AlwP implements BooleanFormulae {

    BooleanFormulae f;

    public AlwP(BooleanFormulae f) {
        this.f = f;
    }

    @Override
    public String toString() {
        return "(alwP " + f + ")";
    }
}
