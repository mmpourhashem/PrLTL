package pltl.bool;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import pltl.Parser;
import pltl.PltlFormula;
import pltl.Smt2Formula;
import pltl.trio.Predicate;

public class And implements BooleanFormulae{
	ArrayList<BooleanFormulae> f = new ArrayList<BooleanFormulae>();

	public And(BooleanFormulae... fList) {
		f.addAll(Arrays.asList(fList));
	}

	public And(ArrayList<BooleanFormulae> fList) {
		f.addAll(fList);
	}

	public And(Collection<Predicate> pList) {
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

	public String getPropSemantics(){
		String s = ";" + this.toString() + "\n";
		int mainF = PltlFormula.add(this);
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		for (BooleanFormulae bf: f)
			indexes.add(PltlFormula.add(bf));
		for (int i = 0; i <= PltlFormula.bound; i++){
			s += "(= (zot " + i + " " + mainF + ") (and";
			for (int j: indexes){
				s += " (zot " + i + " " + j + ")";
			}
			s += "))\n";
		}

		return s;
	}

	public String getProbSemantics() {
		String s = "";
		int mainF = PltlFormula.add(this);
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		ArrayList<BooleanFormulae> tempF = new ArrayList<BooleanFormulae>();// [???] we are not producing probability analysis over probability formulae. The probability of probabilities is not supported in PLTL.
		for (BooleanFormulae bf: f){
			indexes.add(PltlFormula.add(bf));
			tempF.add(bf);
		}

		if (tempF.size() == 1)
			return "";
		if (tempF.size() == 2)
			return getBinaryProbSemantics(indexes.get(0), indexes.get(1));
		for (int i = 0; i < indexes.size(); i++){
			And newAnd = new And();
			for (int j = 0; j < indexes.size(); j++){
				if (j != i){
					newAnd.addFormulae(PltlFormula.get(indexes.get(j)));
				}
			}
			int newAndIndex = PltlFormula.add(newAnd); 
			s += newAnd.getPropSemantics();
			s += getBinaryProbSemantics(indexes.get(i), newAndIndex);
		}

		return s;
	}

	private String getBinaryProbSemantics(int f1, int f2){
		int mainF = PltlFormula.add(this);

		int f1f2 = mainF;
		String s = "";

		//f1 = f1f2 + f1!f2
		int nf2 = PltlFormula.add(new Not(PltlFormula.get(f2)));//to avoid !(!(fma))
		And f1nf2f = new And (PltlFormula.get(f1), PltlFormula.get(nf2));
		int f1nf2 = PltlFormula.add(f1nf2f);

		//f2 = f2f1 + f2!f1
		int nf1 = PltlFormula.add(new Not(PltlFormula.get(f1)));
		And f2nf1f = new And (PltlFormula.get(f2), PltlFormula.get(nf1));
		int f2nf1 = PltlFormula.add(f2nf1f);

		//f1f2 + f1!f2 + !f1f2 + !f1!f2 = 1.0
		And nf1nf2f = new And (PltlFormula.get(nf1), PltlFormula.get(nf2));
		int nf1nf2 = PltlFormula.add(nf1nf2f);
		for (int time = 0; time <= PltlFormula.bound; time++){
			s += Smt2Formula.getOp("=", Smt2Formula.getzotp(time, f1), Smt2Formula.getOp("+" , Smt2Formula.getzotp(time, f1f2), Smt2Formula.getzotp(time, f1nf2))) + "\n"; //f1 = f1f2 + f1!f2
			s += Smt2Formula.getOp("=", Smt2Formula.getzotp(time, f2), Smt2Formula.getOp("+" , Smt2Formula.getzotp(time, f1f2), Smt2Formula.getzotp(time, f2nf1))) + "\n"; //f2 = f2f1 + f2!f1
			s += Smt2Formula.getOp("=", "1.0", Smt2Formula.getSmt2Plus(Smt2Formula.getzotp(time, f1f2), Smt2Formula.getzotp(time, f1nf2), Smt2Formula.getzotp(time, f2nf1), Smt2Formula.getzotp(time, nf1nf2))) + "\n"; //f1f2 + f1!f2 + !f1f2 + !f1!f2 = 1.0
		}

		for (int time = 0; time <= PltlFormula.bound; time++){
			s += Smt2Formula.getOp("=>", Smt2Formula.getOp(">", Smt2Formula.getzotp(time, f2), "0.0"), Smt2Formula.getOp("=", Smt2Formula.getOp("+", Smt2Formula.getzotcp(time, f1, f2), Smt2Formula.getzotcp(time, nf1, f2)) , "1.0")) + "\n" ;
			//			s += Smt2Formula.getOp("=>", Smt2Formula.getOp("and", Smt2Formula.getOp(">", Smt2Formula.getzotp(time, f2), "0.0"), Smt2Formula.getOp("<", Smt2Formula.getzotp(time, f2), "1.0")), Smt2Formula.getOp("=", Smt2Formula.getOp("+", Smt2Formula.getzotcp(time, f1, f2), Smt2Formula.getzotcp(time, f1, nf2)) , Smt2Formula.getzotp(time, f1))) + "\n" ;
			s += Smt2Formula.getOp("=>", Smt2Formula.getOp(">", Smt2Formula.getzotp(time, f1), "0.0"), Smt2Formula.getOp("=", Smt2Formula.getOp("+", Smt2Formula.getzotcp(time, f2, f1), Smt2Formula.getzotcp(time, nf2, f1)) , "1.0")) + "\n" ;
			//			s += Smt2Formula.getOp("=>", Smt2Formula.getOp("and", Smt2Formula.getOp(">", Smt2Formula.getzotp(time, f1), "0.0"), Smt2Formula.getOp("<", Smt2Formula.getzotp(time, f1), "1.0")), Smt2Formula.getOp("=", Smt2Formula.getOp("+", Smt2Formula.getzotcp(time, f2, f1), Smt2Formula.getzotcp(time, f2, nf1)) , Smt2Formula.getzotp(time, f2))) + "\n" ;
		}

		s += "; " + this.toString() + "=" + (new And(PltlFormula.get(f1), PltlFormula.get(f2))).toString() + "\n";
		boolean independent = true;
		for (int time = 0; time <= PltlFormula.bound; time++){
			if (independent)
				s += "(= (zot-p " + time + " " + mainF + ") (* (zot-p " + time + " " + f1 + ") (zot-p " + time + " " + f2 + ")))"; 
			else
				s += "(and"
						+ " (or (and (= (zot-p " + time + " " + f2 + ") 0.0) (= (zot-p " + time + " " + mainF + ") 0.0)) (and (> (zot-p " + time + " " + f2 +") 0.0) (= (zot-p " + time + " " + mainF + ") (* (zot-cp " + time + " " + f1 + " " + f2 + ") (zot-p " + time + " " + f2 + ")))))"
						+ " (or (and (= (zot-p " + time + " " + f1 + ") 0.0) (= (zot-p " + time + " " + mainF + ") 0.0)) (and (> (zot-p " + time + " " + f1 +") 0.0) (= (zot-p " + time + " " + mainF + ") (* (zot-cp " + time + " " + f2 + " " + f1 + ") (zot-p " + time + " " + f1 + "))))))\n";
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
		String s = "(&&";
		for (int i = 0; i < f.size(); i++) {
			s = s + " " + f.get(i).toString();
		}
		return s + ")";
	}

	@Override
	public boolean equals(Object o){
		if (o instanceof And){
			ArrayList<BooleanFormulae> a1 = this.getFormulae();
			ArrayList<BooleanFormulae> a2 = ((And) o).getFormulae();
			return Parser.bfArrayEqual(a1, a2);
		}
		return false;
	}
}
