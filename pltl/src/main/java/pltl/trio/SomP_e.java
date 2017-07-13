package pltl.trio;

import pltl.bool.BooleanFormulae;

public class SomP_e implements BooleanFormulae {

    BooleanFormulae f;

    public SomP_e(BooleanFormulae f) {
        this.f = f;
    }

    @Override
    public String toString() {
        return "(somp_e " + f + ")";
    }
}
