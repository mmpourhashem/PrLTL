package pltl;

import pltl.PltlFormula.probOp;
import pltl.bool.And;
import pltl.bool.BooleanFormulae;

public class ProbAtom implements BooleanFormulae{

	probOp probOP;
	private BooleanFormulae f1, f11, f12, f2, f21, f22;
	float r1, r2;

	public ProbAtom(probOp probOP, BooleanFormulae f1, BooleanFormulae f11, BooleanFormulae f12, BooleanFormulae f2, BooleanFormulae f21, BooleanFormulae f22, float r1, float r2) {
		this.probOP = probOP;
		this.f1 = f1;
		this.f11 = f11;
		this.f12 = f12;
		this.f2 = f2;
		this.f21 = f21;
		this.f22 = f22;
		this.r1 = r1;
		this.r2 = r2;
	}

	public BooleanFormulae getF1(){
		return f1;
	}
	
	public BooleanFormulae getF11(){
		return f11;
	}
	
	public BooleanFormulae getF12(){
		return f12;
	}
	
	public BooleanFormulae getF2(){
		return f2;
	}
	
	public BooleanFormulae getF21(){
		return f21;
	}
	
	public BooleanFormulae getF22(){
		return f22;
	}
	
	public float getR1(){
		return r1;
	}
	public float getR2(){
		return r2;
	}
	
	public String getSemantics(){
		String s= ";" + toString() + "\n";
		int mainF = PltlFormula.add(this);
		if (f1 != null)
			PltlFormula.add(f1);
		if (f11 != null){
			PltlFormula.add(f11);
			//to produce useful information when CP(fma1, fma2) is present, but not (&& fma1 fma2).
			PltlFormula.add(new And(f11, f12));
		}
		if (f12 != null)
			PltlFormula.add(f12);
		if (f2 != null)
			PltlFormula.add(f2);
		if (f21 != null){
			PltlFormula.add(f21);
			//to produce useful information when CP(fma1, fma2) is present, but not (&& fma1 fma2).
			PltlFormula.add(new And(f21, f22));
		}
		if (f22 != null)
			PltlFormula.add(f22);
		
		for (int i = 0; i <= PltlFormula.bound; i++){
			s += "(= (zot " + i + " " + mainF + ") " + toSmt2(i) + ")\n";
		}

		return s;
	}

	@Override
	public String toString() {
		String s1 = "", s2 = "";

		if (f1 != null)
			s1 = "(zot-p " + f1.toString() + ")";
		else if (f11 != null && f12 != null)
			s1 = "(zot-cp " + f11.toString() + " " + f12.toString() + ")";
		else
			s1 = Float.toString(r1);
		
		if (f2 != null)
			s2 = "(zot-p " + f2.toString() + ")";
		else if (f21 != null && f22 != null)
			s2 = "(zot-cp " + f21.toString() + " " + f22.toString() + ")";
		else
			s2 = Float.toString(r2);
		
		return "(" + PltlFormula.probOpToString(probOP) + " " + s1 + " " + s2 + ")";
	}
	
	public String toSmt2(int time) {
		String s1 = "", s2 = "";
			
		if (f1 != null)
			s1 = Smt2Formula.getzotp(time, PltlFormula.add(f1));
		else if (f11 != null && f12 != null)
			s1 = Smt2Formula.getzotcp(time, PltlFormula.add(f11), time, PltlFormula.add(f12));
		else
			s1 = Float.toString(r1);
		
		if (f2 != null)
			s2 = Smt2Formula.getzotp(time, PltlFormula.add(f2));
		else if (f21 != null && f22 != null)
			s2 = Smt2Formula.getzotcp(time, PltlFormula.add(f21), time, PltlFormula.add(f22));
		else
			s2 = Float.toString(r2);
		
		return Smt2Formula.getOp(PltlFormula.probOpToString(probOP), s1, s2);
	}

}
