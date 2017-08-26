package pltl.trio;


import pltl.bool.Atom;
import pltl.bool.Formula;

public class LiteralInt implements Atom{
    int value;

    public LiteralInt(int value){
        this.value = value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

	public Formula get(int offset) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
