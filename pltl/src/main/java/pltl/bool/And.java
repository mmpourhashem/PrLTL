package pltl.bool;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import pltl.Parser;
import pltl.PltlFormula;
import pltl.Smt2Formula;
import pltl.TimeIndex;
import pltl.trio.Predicate;

public class And implements BooleanFormulae{
	ArrayList<BooleanFormulae> f = new ArrayList<BooleanFormulae>();

	public And(BooleanFormulae... fList) {
		//		f.addAll(Arrays.asList(fList));
		for (BooleanFormulae bf:fList){
			if (bf != null)
				f.add(bf);
		}
	}

	public And(ArrayList<BooleanFormulae> fList) {
		//		f.addAll(fList);
		for (BooleanFormulae bf:fList){
			if (bf != null)
				f.add(bf);
		}
	}

	public And(Collection<Predicate> pList) {
		for(Predicate p : pList)
			f.add(p);
	}

	public void addFormulae(BooleanFormulae fIn) {
		if (fIn != null)
			f.add(fIn);
	}

	public void removeFormulae(int index) {
		f.remove(index);
	}

	public void setFormulae(int index, BooleanFormulae newf) {
		f.set(index, newf);
	}

	public void addAllFormulae(ArrayList<BooleanFormulae> bfs) {
		//		f.addAll(bfs);
		for (BooleanFormulae bf:bfs){
			if (bf != null)
				f.add(bf);
		}
	}

	public ArrayList<BooleanFormulae> getFormulae(){
		return f;
	}

	public int size() {
		return f.size();
	}

	public String getSemantics() {
		String s = "";
		for (int time = 0; time <= PltlFormula.bound; time++)
			s += getProbSemantics(time);
		Set<BooleanFormulae> bfSet = new HashSet<BooleanFormulae>();
		for (BooleanFormulae bf: f)
			bfSet.add(bf);
		return getPropSemantics() + s;
	}

	private String getProbSemantics(int time) {
		String s = "";
		TimeIndex mainF = new TimeIndex(time, PltlFormula.add(this));
		
		if (size() == 2) {
			TimeIndex ti0 = PltlFormula.getTimeIndex(time, f.get(0));
			TimeIndex ti1 = PltlFormula.getTimeIndex(time, f.get(1));
			boolean areDep = false;
			
			if (PltlFormula.isParentOf(PltlFormula.getTimeIndex(time, f.get(0)), PltlFormula.getTimeIndex(time, f.get(1)))) {
				areDep = true;
			} else if (PltlFormula.isParentOf(PltlFormula.getTimeIndex(time, f.get(1)), PltlFormula.getTimeIndex(time, f.get(0)))) {
				ti0 = PltlFormula.getTimeIndex(time, f.get(1));
				ti1 = PltlFormula.getTimeIndex(time, f.get(0));
				areDep = true;
			}
			
			if (areDep) //when there is an edge from ti0 to ti1 in the Bayesian Network:
				return Smt2Formula.getOp("=", mainF.toString(), Smt2Formula.getOp("*" , Smt2Formula.getzotcp(ti1, ti0), ti0.toString())) + "\n";
			else //when ti0 and ti1 are not directly connected in the Bayesian Network:
				return Smt2Formula.getOp("=", mainF.toString(), Smt2Formula.getOp("*" , PltlFormula.getTimeIndex(time, f.get(0)).toString(), PltlFormula.getTimeIndex(time, f.get(1)).toString())) + "\n";
		}

		return s;
	}

	public String getPropSemantics() {
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

	//	public ArrayList<Integer> getProbSemantics(Set<BooleanFormulae> bfSet) {
	//		String s = "";
	//		ArrayList<E>
	//		ArrayList<BooleanFormulae> bfa = new ArrayList<BooleanFormulae>();
	//		for (BooleanFormulae bf: bfSet)
	//			bfa.add(bf);
	//		if (bfa.size() == 2) {
	//			
	//		}
	//			
	//		return s;
	//	}

	//	public String getProbSemantics() {
	//		String s = "";
	//		int mainF = PltlFormula.add(this);
	//		ArrayList<Integer> indexes = new ArrayList<Integer>();
	//		ArrayList<BooleanFormulae> tempF = new ArrayList<BooleanFormulae>();// [???] we are not producing probability analysis over probability formulae. The probability of probabilities is not supported in PLTL.
	//		for (BooleanFormulae bf: f){
	//			indexes.add(PltlFormula.add(bf));
	//			tempF.add(bf);
	//		}
	//
	//		if (tempF.size() == 1)
	//			return "";
	//		if (tempF.size() == 2)
	//			return getBinaryProbSemantics(indexes.get(0), indexes.get(1));
	//		for (int i = 0; i < indexes.size(); i++){
	//			And newAnd = new And();
	//			for (int j = 0; j < indexes.size(); j++){
	//				if (j != i){
	//					newAnd.addFormulae(PltlFormula.get(indexes.get(j)));
	//				}
	//			}
	//			int newAndIndex = PltlFormula.add(newAnd); 
	//			s += newAnd.getPropSemantics();
	//			s += getBinaryProbSemantics(indexes.get(i), newAndIndex);
	//		}
	//
	//		return s;
	//	}

	//	private String getBinaryProbSemantics(int f1, int f2){
	//		int mainF = PltlFormula.add(this);
	//
	//		int f1f2 = mainF;
	//		String s = "";
	//
	//		//f1 = f1f2 + f1!f2
	//		int nf2 = PltlFormula.add(new Not(PltlFormula.get(f2)));//to avoid !(!(fma))
	//		And f1nf2f = new And (PltlFormula.get(f1), PltlFormula.get(nf2));
	//		int f1nf2 = PltlFormula.add(f1nf2f);
	//
	//		//f2 = f2f1 + f2!f1
	//		int nf1 = PltlFormula.add(new Not(PltlFormula.get(f1)));
	//		And f2nf1f = new And (PltlFormula.get(f2), PltlFormula.get(nf1));
	//		int f2nf1 = PltlFormula.add(f2nf1f);
	//
	//		//f1f2 + f1!f2 + !f1f2 + !f1!f2 = 1.0
	//		And nf1nf2f = new And (PltlFormula.get(nf1), PltlFormula.get(nf2));
	//		int nf1nf2 = PltlFormula.add(nf1nf2f);
	//		for (int time = 0; time <= PltlFormula.bound; time++){
	//			s += Smt2Formula.getOp("=", Smt2Formula.getzotp(time, f1), Smt2Formula.getOp("+" , Smt2Formula.getzotp(time, f1f2), Smt2Formula.getzotp(time, f1nf2))) + "\n"; //f1 = f1f2 + f1!f2
	//			s += Smt2Formula.getOp("=", Smt2Formula.getzotp(time, f2), Smt2Formula.getOp("+" , Smt2Formula.getzotp(time, f1f2), Smt2Formula.getzotp(time, f2nf1))) + "\n"; //f2 = f2f1 + f2!f1
	//			s += Smt2Formula.getOp("=", "1.0", Smt2Formula.getSmt2Plus(Smt2Formula.getzotp(time, f1f2), Smt2Formula.getzotp(time, f1nf2), Smt2Formula.getzotp(time, f2nf1), Smt2Formula.getzotp(time, nf1nf2))) + "\n"; //f1f2 + f1!f2 + !f1f2 + !f1!f2 = 1.0
	//		}
	//
	//		for (int time = 0; time <= PltlFormula.bound; time++){
	//			s += Smt2Formula.getOp("=>", Smt2Formula.getOp(">", Smt2Formula.getzotp(time, f2), "0.0"), Smt2Formula.getOp("=", Smt2Formula.getOp("+", Smt2Formula.getzotcp(time, f1, time, f2), Smt2Formula.getzotcp(time, nf1, time, f2)) , "1.0")) + "\n" ;
	//			//			s += Smt2Formula.getOp("=>", Smt2Formula.getOp("and", Smt2Formula.getOp(">", Smt2Formula.getzotp(time, f2), "0.0"), Smt2Formula.getOp("<", Smt2Formula.getzotp(time, f2), "1.0")), Smt2Formula.getOp("=", Smt2Formula.getOp("+", Smt2Formula.getzotcp(time, f1, f2), Smt2Formula.getzotcp(time, f1, nf2)) , Smt2Formula.getzotp(time, f1))) + "\n" ;
	//			s += Smt2Formula.getOp("=>", Smt2Formula.getOp(">", Smt2Formula.getzotp(time, f1), "0.0"), Smt2Formula.getOp("=", Smt2Formula.getOp("+", Smt2Formula.getzotcp(time, f2, time, f1), Smt2Formula.getzotcp(time, nf2, time, f1)) , "1.0")) + "\n" ;
	//			//			s += Smt2Formula.getOp("=>", Smt2Formula.getOp("and", Smt2Formula.getOp(">", Smt2Formula.getzotp(time, f1), "0.0"), Smt2Formula.getOp("<", Smt2Formula.getzotp(time, f1), "1.0")), Smt2Formula.getOp("=", Smt2Formula.getOp("+", Smt2Formula.getzotcp(time, f2, f1), Smt2Formula.getzotcp(time, f2, nf1)) , Smt2Formula.getzotp(time, f2))) + "\n" ;
	//		}
	//
	//		s += "; " + this.toString() + "=" + (new And(PltlFormula.get(f1), PltlFormula.get(f2))).toString() + "\n";
	//		boolean independent = true;
	//		for (int time = 0; time <= PltlFormula.bound; time++){
	//			if (independent)
	//				s += Smt2Formula.getOp("=", Smt2Formula.getzotp(time, mainF), Smt2Formula.getOp("*", Smt2Formula.getzotp(time, f1), Smt2Formula.getzotp(time, f2))); 
	//			else
	//				s += Smt2Formula.getOp("and", 
	//						Smt2Formula.getOp("or", 
	//								Smt2Formula.getOp("and", 
	//										Smt2Formula.getOp("=", Smt2Formula.getzotp(time, f2), "0.0") , 
	//										Smt2Formula.getOp("=", Smt2Formula.getzotp(time, mainF), "0.0")), 
	//								Smt2Formula.getOp("and", Smt2Formula.getOp(">", Smt2Formula.getzotp(time, f2), "0.0"), Smt2Formula.getOp("=", Smt2Formula.getzotp(time, mainF),
	//										Smt2Formula.getOp("*", Smt2Formula.getzotcp(time, f1, time, f2), Smt2Formula.getzotp(time, f2))))), 
	//						Smt2Formula.getOp("or", 
	//								Smt2Formula.getOp("and", 
	//										Smt2Formula.getOp("=", Smt2Formula.getzotp(time, f1), "0.0") , 
	//										Smt2Formula.getOp("=", Smt2Formula.getzotp(time, mainF), "0.0")), 
	//								Smt2Formula.getOp("and", Smt2Formula.getOp(">", Smt2Formula.getzotp(time, f1), "0.0"), Smt2Formula.getOp("=", Smt2Formula.getzotp(time, mainF),
	//										Smt2Formula.getOp("*", Smt2Formula.getzotcp(time, f2, time, f1), Smt2Formula.getzotp(time, f1))))));
	//		}
	//		return s;
	//	}

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
	public boolean equals(Object o) {
		if (o instanceof And){
			ArrayList<BooleanFormulae> a1 = this.getFormulae();
			ArrayList<BooleanFormulae> a2 = ((And) o).getFormulae();
			return Parser.bfArrayEqual(a1, a2);
		}

		return false;
	}
}
