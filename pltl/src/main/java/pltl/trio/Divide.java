package pltl.trio;

import pltl.bool.*;

public class Divide implements BooleanFormulae{

    BooleanFormulae a;
    BooleanFormulae b;

    public Divide(BooleanFormulae a, BooleanFormulae b) {
        this.a=a;
        this.b=b;
    }

    @Override
    public String toString() {
        return "([/] "+a+" "+b+")";
    }
}
