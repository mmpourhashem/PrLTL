package pltl.trio;

import pltl.bool.*;

public class Multiply implements Formula{

    Formula a;
    Formula b;

    public Multiply(Formula a, Formula b) {
        this.a=a;
        this.b=b;
    }

    @Override
    public String toString() {
        return "([*] "+a+" "+b+")";
    }
}
