package pltl.bool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import pltl.Parser;
import pltl.PltlFormula;
import pltl.Smt2Formula;
import pltl.trio.Predicate;

public class Or implements BooleanFormulae{
	ArrayList<BooleanFormulae> f = new ArrayList<BooleanFormulae>();

	public Or() {
	}

	public Or(ArrayList<BooleanFormulae> fList) {
		f.addAll(fList);
	}

	public Or(BooleanFormulae... fList) {
		f.addAll(Arrays.asList(fList));
	}

	public Or(Collection<Predicate> pList) {
		for(Predicate p : pList)
			f.add(p);
	}

	public void addFormulae(BooleanFormulae fIn) {
		f.add(fIn);
	}

	public void removeFormulae(int index) {
		f.remove(index);
	}

	public void setFormulae(int index, BooleanFormulae newf) {
		f.set(index, newf);
	}

	public void addAllFormulae(ArrayList<BooleanFormulae> bfs) {
		f.addAll(bfs);
	}

	public ArrayList<BooleanFormulae> getFormulae(){
		return f;
	}

	public int size() {
		return f.size();
	}

	public String getSemantics(){
		return getPropSemantics() + getProbSemantics();
	}

//	private String getBVProbSemantics() {
//		String s = "";
//		And newAnd = new And();
//		for (BooleanFormulae bf: getFormulae())
//			newAnd.addFormulae(new Not(bf));
//		for (int time = 0; time <= PltlFormula.bound; time++){
//			s += Smt2Formula.getSmt2Eq(PltlFormula.getZotpx(time, this), Smt2Formula.getSmt2Minus("1.0", PltlFormula.getZotpx(time, newAnd)));
//		}
//		return s;
//	}

	public String getPropSemantics(){
		String s = ";" + this.toString() + "\n";
		int mainF = PltlFormula.add(this);
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		for (BooleanFormulae bf: f)
			indexes.add(PltlFormula.add(bf));
		for (int i = 0; i <= PltlFormula.bound; i++){
			s += "(= (zot " + i + " " + mainF + ") (or";
			for (int j: indexes){
				s += " (zot " + i + " " + j + ")"; 
			}
			s += "))\n";
		}

		return s;
	}

	public String getProbSemantics() {
		String s = "";
//		int mainF = PltlFormula.add(this);
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		ArrayList<BooleanFormulae> tempF = new ArrayList<BooleanFormulae>();// [???] we are not producing probability analysis over probability formulae. The probability of probabilities is not supported in PLTL. E.g. if we have a => P(b) = r1, then we ignore the probability formula for (!a || P(b) = r1). 
		for (BooleanFormulae bf: f){
			indexes.add(PltlFormula.add(bf));
			tempF.add(bf);
		}
		if (tempF.size() == 1)
			return "";
		if (tempF.size() == 2)
			return getBinaryProbSemantics(indexes.get(0), indexes.get(1));
		for (int i = 0; i < indexes.size(); i++){
			Or newOr = new Or();
			for (int j = 0; j < indexes.size(); j++){
				if (j != i){
					newOr.addFormulae(PltlFormula.get(indexes.get(j)));
				}
			}
			int newOrIndex = PltlFormula.add(newOr); 
			s += newOr.getPropSemantics();
			s += getBinaryProbSemantics(indexes.get(i), newOrIndex);
		}

		return s;
	}

	private String getBinaryProbSemantics(int f1, int f2){
		int mainF = PltlFormula.add(this);
		And f1f2f = new And (PltlFormula.get(f1), PltlFormula.get(f2));
		int f1f2 = PltlFormula.add(f1f2f);
		String s = "; " + this.toString() + "=" + (new Or(PltlFormula.get(f1), PltlFormula.get(f2))).toString() + "\n";
		s += f1f2f.getSemantics();

		//f1 = f1f2 + f1!f2
//		Not nf2f = new Not(PltlFormula.get(f2));
		int nf2f = PltlFormula.add(new Not(PltlFormula.get(f2)));//to avoid !(!(fma))
//		s += nf2f.getSemantics();
		And f1nf2f = new And (PltlFormula.get(f1), PltlFormula.get(nf2f));
		int f1nf2 = PltlFormula.add(f1nf2f);
//		s += f1nf2f.getSemantics();
		
		//f2 = f2f1 + f2!f1
//		Not nf1f = new Not(PltlFormula.get(f1));
		int nf1f = PltlFormula.add(new Not(PltlFormula.get(f1)));
//		s += nf1f.getSemantics();
		And f2nf1f = new And (PltlFormula.get(f2), PltlFormula.get(nf1f));
		int f2nf1 = PltlFormula.add(f2nf1f);
//		s += f2nf1f.getSemantics();
		
		//f1f2 + f1!f2 + !f1f2 + !f1!f2 = 1.0
		And nf1nf2f = new And (PltlFormula.get(nf1f), PltlFormula.get(nf2f));
		int nf1nf2 = PltlFormula.add(nf1nf2f);
		
		for (int time = 0; time <= PltlFormula.bound; time++){
			s += "(= (zot-p " + time + " " + mainF + ") (- (+ " + "(zot-p " + time + " " + f1 + ") (zot-p " + time + " " + f2 + ")) (zot-p " + time + " " + f1f2 + ")))\n";
			s += Smt2Formula.getOp("=" , Smt2Formula.getzotp(time, f1), Smt2Formula.getSmt2Plus(Smt2Formula.getzotp(time, f1f2), Smt2Formula.getzotp(time, f1nf2))) + "\n"; //f1 = f1f2 + f1!f2
			s += Smt2Formula.getOp("=" , Smt2Formula.getzotp(time, f2), Smt2Formula.getSmt2Plus(Smt2Formula.getzotp(time, f1f2), Smt2Formula.getzotp(time, f2nf1))) + "\n"; //f2 = f2f1 + f2!f1
			s += Smt2Formula.getOp("=" , "1.0", Smt2Formula.getSmt2Plus(Smt2Formula.getzotp(time, f1f2), Smt2Formula.getzotp(time, f1nf2), Smt2Formula.getzotp(time, f2nf1), Smt2Formula.getzotp(time, nf1nf2))) + "\n"; //f1f2 + f1!f2 + !f1f2 + !f1!f2 = 1.0
		}

		return s;
	}

	@Override
	public String toString() {
		if (f.isEmpty()) {
			return "";
		}
		if (f.size() == 1) {
			return f.get(0).toString();
		}
		String s = "(||";
		for (int i = 0; i < f.size(); i++) {
			s = s + " " + f.get(i).toString();
		}
		s = s + ")";
		return s;
	}

	@Override
	public boolean equals(Object o){
		if (o instanceof Or){
			ArrayList<BooleanFormulae> a1 = this.getFormulae();
			ArrayList<BooleanFormulae> a2 = ((Or) o).getFormulae();
			return Parser.bfArrayEqual(a1, a2);
		}
		return false;

	}
}
