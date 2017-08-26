package pltl.bool;

public class Iff implements Formula{

	private Formula f1;
	private Formula f2;

	public Iff(Formula f1, Formula f2){
		this.f1 = f1;
		this.f2 = f2;
	}
	
	public Formula getFormula1() {
		return f1;
	}
	
	public Formula getFormula2() {
		return f2;
	}
	
	public Formula get(int offset) {
		return new Iff(f1.get(offset), f2.get(offset));
	}

	@Override
	public String toString(){
		return "(<-> " + f1 + " " + f2 + ")";
	}

	@Override
	public boolean equals(Object o){
		if (o instanceof Iff){
			Iff iffO = (Iff) o;
			return ((f1.equals(iffO.getFormula1()) && f2.equals(iffO.getFormula2())) || (f2.equals(iffO.getFormula1()) && f1.equals(iffO.getFormula2())));
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
