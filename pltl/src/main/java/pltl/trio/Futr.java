package pltl.trio;

import pltl.bool.BooleanFormulae;

public class Futr implements BooleanFormulae {

    BooleanFormulae f;
    int t;

    public Futr(BooleanFormulae f, int t) {
        this.f = f;
        this.t = t;
    }

    @Override
    public String toString() {
        return "(futr " + f + " " + t + ")";
    }
}
