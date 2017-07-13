package pltl.trio;

import pltl.bool.BooleanFormulae;

public class Past implements BooleanFormulae {

    BooleanFormulae f;
    int t;

    public Past(BooleanFormulae f, int t) {
        this.f = f;
        this.t = t;
    }

    @Override
    public String toString() {
        return "(past " + f + " " + t + ")";
    }
}
