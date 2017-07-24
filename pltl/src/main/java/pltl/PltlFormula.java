package pltl;


import java.util.ArrayList;

import pltl.bool.And;
import pltl.bool.BooleanFormulae;
import pltl.bool.Not;
import pltl.bool.Or;
import pltl.trio.Next;
import pltl.trio.Predicate;
import pltl.trio.Yesterday;

public class PltlFormula{

	public static ArrayList<BooleanFormulae> instances = new ArrayList<BooleanFormulae>();
	public static ArrayList<BooleanFormulae> deps = new ArrayList<BooleanFormulae>();
//	public static ArrayList<ArrayList<BooleanFormulae>> depCluster = new ArrayList<ArrayList<BooleanFormulae>>();
//	public static ArrayList<ArrayList<Integer>> deps = new ArrayList<ArrayList<Integer>>();
	public static int bound = 0;
	public static int mainF = 0;
	public static ArrayList<TimeIndex> bayesNet = new ArrayList<TimeIndex>();

	public static int add(BooleanFormulae fma){
		int i = getIndex(fma);
		if (i == -1) {
			if (fma instanceof Not && ((Not) fma).getFormula() instanceof Not)
				return add(((Not) ((Not) fma).getFormula()).getFormula());
			instances.add(fma);
//			if (fma instanceof ProbAtom){
//				if (((ProbAtom) fma).getF11() != null)
//					addToDepCluster(((ProbAtom) fma).getF11(), ((ProbAtom) fma).getF12());
//				if (((ProbAtom) fma).getF21() != null)
//					addToDepCluster(((ProbAtom) fma).getF21(), ((ProbAtom) fma).getF22());
//			}
			return instances.size() - 1;
		}
		else
			return getIndex(fma);
	}
	
//	private static BooleanFormulae pruneDeps(And and){
//		for (BooleanFormulae f: and.getFormulae())
//	}
//	TODO order of conjunction
	
	public static TimeIndex getTimeIndex(int time, BooleanFormulae f) {
		if (f instanceof Next)
			return getTimeIndex(time + 1, ((Next) f).getFormula());
		else if (f instanceof Yesterday)
			return getTimeIndex(time - 1, ((Next) f).getFormula());
		
		// TODO Futr, Past, ...
		
		return new TimeIndex(time, add(f));
	}
	
	public static TimeIndex getTimeIndex(int time, int index) {
		return getTimeIndex(time, PltlFormula.get(index));
	}
	
	public static void addDep(BooleanFormulae f){
		deps.add(f);
	}

//	private static void addToDepCluster(BooleanFormulae f1, BooleanFormulae f2) {
//		ArrayList<BooleanFormulae> bfs = new ArrayList<BooleanFormulae>();
//		bfs.addAll(getAtoms(f1));
//		bfs.addAll(getAtoms(f2));
//		for (int i = 0; i < bfs.size() - 1; i++)
//			addPairToDepCluster(bfs.get(i), bfs.get(i + 1));
//	}
//
//	private static void addPairToDepCluster(BooleanFormulae f1, BooleanFormulae f2) {
//		if (getDepClusterIndex(f1) == -1 && getDepClusterIndex(f2) == -1){
//			ArrayList<BooleanFormulae> temp = new ArrayList<BooleanFormulae>();
//			temp.add(f1);
//			temp.add(f2);
//			depCluster.add(temp);
//		}
//		else if (getDepClusterIndex(f1) > -1 && getDepClusterIndex(f2) == -1){
//			depCluster.get(getDepClusterIndex(f1)).add(f2);
//		}
//		else if (getDepClusterIndex(f1) == -1 && getDepClusterIndex(f2) > -1){
//			depCluster.get(getDepClusterIndex(f2)).add(f1);
//		}
//		else{
//			int ai = getDepClusterIndex(f1);
//			int bi = getDepClusterIndex(f2);
//			if (ai != bi){
//				for (BooleanFormulae bf: depCluster.get(bi))
//					depCluster.get(ai).add(bf);
//				depCluster.remove(bi);
//			}
//		}
//	}

//	private static int getDepClusterIndex(BooleanFormulae f){
//		for (int i = 0; i < depCluster.size(); i++){
//			for (int j = 0; j < depCluster.get(i).size(); j++){ //It returns f's negation index if it exists. 
//				if (f.equals(depCluster.get(i).get(j)) || (f instanceof Not && ((Not) f).getFormula().equals(depCluster.get(i).get(j))) || new Not(f).equals(depCluster.get(i).get(j)))
//					return i;
//			}
//		}
//
//		return -1;
//	}

	private static ArrayList<BooleanFormulae> getAtoms(BooleanFormulae f){
		ArrayList<BooleanFormulae> bfs = new ArrayList<BooleanFormulae>();
		if (f instanceof And)
			for (BooleanFormulae bf:((And) f).getFormulae())
				bfs.addAll(getAtoms(bf));

		else if (f instanceof Or)
			for (BooleanFormulae bf:((Or) f).getFormulae())
				bfs.addAll(getAtoms(bf));

		else if (f instanceof Not)
			bfs.addAll(getAtoms(((Not) f).getFormula()));

		else if (f instanceof Next){
			bfs.addAll(getAtoms(((Next) f).getFormula()));
			bfs.add((Next) f);
		}
		
		else if (f instanceof Predicate)
			bfs.add((Predicate) f);
		//TODO until, since, ...

		return bfs;
	}

	public static int getIndex(BooleanFormulae fma){
		for (int i = 0; i < instances.size(); i++)
			if (instances.get(i).equals(fma))
				return i;
		return -1;
	}

	public static BooleanFormulae get(int index){
		return instances.get(index);
	}

	/*
//	//Converts a formula to its bitvector representative with X (don't care) bits.
//	public static String getZotpx(int time, BooleanFormulae f) {
//		String s = "(zot-px " + time + " #b";
//		StringBuilder bv1 = new StringBuilder("");
//		StringBuilder bv2 = new StringBuilder("");
//		for (int ins = instances.size() -1; ins >= 0 ; ins--){
//			bv1.append("1");
//			bv2.append("1");
//		}
//		int n = instances.size();
//		int fIndex = getIndex(f);
//		
//		if (fIndex != -1) {
//			bv1.setCharAt(n - fIndex - 1, '1');
//			bv2.setCharAt(n - fIndex - 1, '0');
//		}
//		else if (f instanceof And){
//			And and = (And) f;
//			for (BooleanFormulae andFF:and.getFormulae()){
//				fIndex = getIndex(andFF);
//				char andFFChar = '1';
//				if (fIndex == -1){
//					if (andFF instanceof Not){
//						fIndex = getIndex(((Not) andFF).f);
//						andFFChar = '0';
//					}
//					else
//						System.out.println("The formula is not found in the PltlFormulae.instaces.");
//				}
//				bv1.setCharAt(n - fIndex - 1, andFFChar);
//				bv2.setCharAt(n - fIndex - 1, '0');
//			}
//		}
//
//		else if (f instanceof Or){
//		}
//
//		else if (f instanceof Not){
//
//		}
//
//
//		return s + bv1 + " #b" + bv2 + ")";
//	}
	 */

	public enum probOp{
		LT, LTE, EQ, GT, GTE
	}

	public static probOp getProbOp(String s){
		if (s.equals("<"))
			return probOp.LT;
		if (s.equals("<="))
			return probOp.LTE;
		if (s.equals("="))
			return probOp.EQ;
		if (s.equals(">"))
			return probOp.GT;
		if (s.equals(">="))
			return probOp.GTE;
		return null;
	}

	public static String probOpToString(probOp pop){
		switch (pop) {
		case LT:
			return "<";
		case LTE:
			return "<=";
		case EQ:
			return "=";
		case GT:
			return ">";
		case GTE:
			return ">=";
		}
		return null;
	}

	public static ArrayList<Predicate> getPredicates(){
		ArrayList<Predicate> p = new ArrayList<Predicate>();
		for (BooleanFormulae f:instances)
			if (f instanceof Predicate)
				p.add((Predicate) f);
		return p;
	}

	public static void addBayes(TimeIndex depTI) {
		for (TimeIndex ti: bayesNet)
			if (ti.equals(depTI)) {
				ti.addParent(depTI.getParents());
				return;
			}
		bayesNet.add(depTI);
	}
	
	public static boolean isParentOf(TimeIndex parent, TimeIndex depF) {
		for (TimeIndex ti:bayesNet)
			if (ti.equals(depF) && depF.hasParent(parent))
				return true;
		
		return false;
	}

	////Returns ture, if it is expressible by zot-px.
	//	public static boolean isSimpleType(BooleanFormulae f){
	//		return ()
	//	}

	//	private class FInstance{
	//		BooleanFormulae f;
	//		boolean probDone;
	//		boolean theNegDone;
	//		public FInstance(BooleanFormulaeZ){
	//			this.f = f;
	//			this.probDone = fNumber;
	//			this.value = value;
	//		}
	//	}


}
