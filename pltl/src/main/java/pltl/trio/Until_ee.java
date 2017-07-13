package pltl.trio;

import pltl.bool.BooleanFormulae;

public class Until_ee implements BooleanFormulae {

    BooleanFormulae f1;
    BooleanFormulae f2;

    public Until_ee(BooleanFormulae f1, BooleanFormulae f2) {
        this.f1 = f1;
        this.f2 = f2;
    }

    @Override
    public String toString() {
        return "(until_ee " + f1 + " " + f2 + ")";
    }
}
