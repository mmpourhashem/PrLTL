package pltl.trio;


import pltl.bool.Atom;

public class LiteralInt implements Atom{
    int value;

    public LiteralInt(int value){
        this.value = value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
    
}
