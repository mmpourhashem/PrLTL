package pltl.trio;

import pltl.bool.BooleanFormulae;

public class SomP implements BooleanFormulae {

    BooleanFormulae f;

    public SomP(BooleanFormulae f) {
        this.f = f;
    }

    @Override
    public String toString() {
        return "(somp " + f + ")";
    }
}
