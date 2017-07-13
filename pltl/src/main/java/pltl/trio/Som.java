package pltl.trio;

import pltl.bool.BooleanFormulae;

public class Som implements BooleanFormulae {

    BooleanFormulae f;

    public Som(BooleanFormulae f) {
        this.f = f;
    }

    @Override
    public String toString() {
        return "(som " + f + ")";
    }
}
