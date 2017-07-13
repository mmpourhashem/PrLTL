package pltl.trio;

import pltl.bool.BooleanFormulae;

public class AlwF implements BooleanFormulae {

    BooleanFormulae f;

    public AlwF(BooleanFormulae f) {
        this.f = f;
    }

    @Override
    public String toString() {
        return "(alwf " + f + ")";
    }
}
