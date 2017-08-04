package pltl.bool;

public class Iff implements Formula{

	Formula f1;
	Formula f2;

	public Iff(Formula f1, Formula f2){
		this.f1=f1;
		this.f2=f2;
	}

	@Override
	public String toString(){
		return "(<-> " + f1 + " " + f2 + ")";
	}

	@Override
	public boolean equals(Object o){
		if (o instanceof Iff){
			Iff iffO = (Iff) o;
			return ((f1.equals(iffO.f1) && f2.equals(iffO.f2)) || (f2.equals(iffO.f1) && f1.equals(iffO.f2)));
		}
		return false;
	}

//	public String getSemantics(){
//		return getPropSemantics() + getProbSemantics();
//	}
//
//	private String getProbSemantics() {
//		String s = ";" + this.toString() + "\n";
//		int mainF = PltlFormula.add(this);
//		for (int time = 0; time <= PltlFormula.bound; time++)
//			s += Smt2Formula.getSmt2Eq(Smt2Formula.getzot(time, mainF), Smt2Formula.getSmt2Eq(Smt2Formula.getzot(time, PltlFormula.add(f1)), Smt2Formula.getzot(time, PltlFormula.add(f2))));
//
//		return s + "\n";
//	}
//
//	private String getPropSemantics() {
//		String s = "";
//		for (int time = 0; time <= PltlFormula.bound; time++)
//			s += Smt2Formula.getSmt2Eq(Smt2Formula.getzot(time, mainF), Smt2Formula.getSmt2Eq(Smt2Formula.getzot(time, PltlFormula.add(f1)), Smt2Formula.getzot(time, PltlFormula.add(f2))));
//		return 0;
//	}
}
