package pltl.trio;

import java.util.HashSet;
import pltl.bool.Atom;
import pltl.bool.Formula;

public class TrioConstant implements Atom{

	public static HashSet<TrioConstant> instances=new HashSet<TrioConstant>();
    String constantname;
    int value;

    public TrioConstant(String constantname, int value){
        this.constantname = constantname;
        this.value = value;
        instances.add(this);
    }

    public int getValue() {
		return value;
	}
    
	public String getConstantName() {
		return constantname;
	}

	@Override
    public String toString() {
        return constantname;
    }

	@Override
	public boolean equals(Object object){
		if(!(object instanceof TrioConstant))return false;
		TrioConstant other=(TrioConstant)object;
		return this.getConstantName().equals(other.getConstantName());	
	}

	@Override
	public int hashCode(){
		return this.getConstantName().hashCode();
	}

	public Formula get(int offset) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
