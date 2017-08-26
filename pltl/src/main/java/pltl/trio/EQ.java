package pltl.trio;

import pltl.bool.Formula;

public class EQ implements Formula{

    Formula a;
    Formula b;
    
    public EQ(Formula a, Formula b){
        this.a=a;
        this.b=b;
    }
    
    public Formula getLeftSide(){
        return a;
    }
    
    public Formula getRightSide(){
        return b;
    }
    
    @Override
    public String toString() {
        return "([=] " + a + " " + b + ")";
    }

	public Formula get(int offset) {
		return null;
	}
}