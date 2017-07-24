package pltl;

import java.util.ArrayList;

import pltl.bool.And;
import pltl.bool.BooleanFormulae;
import pltl.bool.Not;
import pltl.bool.Or;
import pltl.trio.Alw;
import pltl.trio.Next;

public class Probability {

	public static String getProbFormulae(){
		String s = "";
		int mainSize = 0;
		int lastProcessed = 0;
		while (mainSize < PltlFormula.instances.size()) {
			mainSize = PltlFormula.instances.size();
			s += getMainSemantics(lastProcessed, PltlFormula.instances.size() - 1);
			lastProcessed = mainSize;
			//		s += ";<TheNeg inferred>\n" + getTheNegInferredSemantics() + ";</TheNeg inferred>\n";
			//		s += ";<Inferred analysis>\n" + getMainSemantics(mainSize, PltlFormula.instances.size() - 1) + ";<Inferred analysis>\n";
			//		s += ";<Formulae map>\n" + getExhaustivePredicateFormuale() + ";</Formulae map>\n";
			s += getMainSemantics(lastProcessed, PltlFormula.instances.size() - 1);
		}
		return s;
	}

	//	private static String getExhaustivePredicateFormuale() {//SMT solvers cannot handle this.
	//		String s = "";
	//		ArrayList<Predicate> ps = PltlFormula.getPredicates();
	//		ArrayList<BooleanFormulae> newfs = new ArrayList<BooleanFormulae>();
	//		if (ps.size() < 2)
	//			return "";
	//		newfs.add(ps.get(0));
	//		for (int i = 1; i < ps.size(); i++){
	//			int newfsSize = newfs.size();
	//			for (int j = 0; j < newfsSize; j++){
	//				BooleanFormulae f = getFusion(ps.get(i), newfs.get(j));
	//				newfs.add(f);
	//				s += getFusionSemantics(f);
	//
	//				f = getFusion(ps.get(i), new Not(newfs.get(j)));
	//				newfs.add(f);
	//				s += getFusionSemantics(f);
	//				
	//				f = getFusion(new Not(ps.get(i)), newfs.get(j));
	//				newfs.add(f);
	//				s += getFusionSemantics(f);
	//				
	//				f = getFusion(new Not(ps.get(i)), new Not(newfs.get(j)));
	//				newfs.add(f);
	//				s += getFusionSemantics(f);
	//			}
	//		}
	//
	//		return s;
	//	}

	private static String getFusionSemantics(BooleanFormulae f) {
		String s = "";
		int index = PltlFormula.add(f);
		if (index != -1)
			s += getMainSemantics(index);

		return s;
	}

	private static BooleanFormulae getFusion(BooleanFormulae f1, BooleanFormulae f2){
		BooleanFormulae f = null;
		if (f2 instanceof And){//In order to avoid (&& (-p- c) (&& (-p- b) (-p- a))), and have (&& (-p- c) (-p- b) (-p- a)) instead.
			ArrayList<BooleanFormulae> newAndFs = ((And) f2).getFormulae();
			newAndFs.add(f1);
			f = new And(newAndFs);
		}
		else{
			f = new And(f1, f2);
		}

		return f;
	}

	public static String getMainSemantics(int startI, int endI){
		String s = "";
		for (int i = startI; i <= endI; i++){
			BooleanFormulae f = PltlFormula.instances.get(i);
			if (PltlFormula.add(f) == PltlFormula.mainF && f instanceof And){
				//TODO must be removed.
				for (BooleanFormulae bf:((And) f).getFormulae())
					for (int time = 0; time <= PltlFormula.bound; time++)
						s += Smt2Formula.getOp("=" , "1.0", Smt2Formula.getzotp(time, PltlFormula.add(bf))) + Smt2Formula.getzot(time, PltlFormula.add(bf)) + "\n"; 
			}
			else if (f instanceof And)
				s += ((And) f).getSemantics();
			else if (f instanceof Or)
				s += ((Or) f).getSemantics();
			else if (f instanceof ProbAtom)
				s += ((ProbAtom) f).getSemantics();
			else if (f instanceof Not)
				s += ((Not) f).getSemantics();
			else if (f instanceof Next)
				s += ((Next) f).getSemantics();
		}

		return s;
	}

	public static String getMainSemantics(int index){
		return getMainSemantics(index, index);
	}

	public static String getTheNegInferredSemantics(){
		String s = "";
		//If fma is a formula then simplified (!! fma) is added to the formula instances.
		int n = PltlFormula.instances.size();
		for (int i=0; i < n; i++){
			BooleanFormulae f = PltlFormula.instances.get(i);
			s += new Not(f).getTheNegSemantics();
		}

		return s;
	}

	public static void processDeps(int left, int right, BooleanFormulae f) {
		if (f instanceof And)
			for (BooleanFormulae bf: ((And) f).getFormulae())
				processDeps(left, right, bf);
		else if (f instanceof Alw)
			processDeps(0, PltlFormula.bound, ((Alw) f).getFormula());
		else if (f instanceof Next)
			processDeps(left + 1, right + 1, ((Next) f).getFormula());
		else if (f instanceof Dep) {
			Dep d = (Dep) f;
			for (int time = left; time <= right; time++) {
				int depFI = PltlFormula.add(d.getDepF());
				TimeIndex depTI = PltlFormula.getTimeIndex(time, depFI);
				for (int i = 0; i < d.getParentsF().size(); i++){
					int parentFI = PltlFormula.add(d.getParentsF().get(i));
					depTI.addParent(PltlFormula.getTimeIndex(time, parentFI));
				}
				PltlFormula.addBayes(depTI);
			}
		}

		//TODO yesterday, lasts, lasted, alwf, futr, past, ...		

	}

//	public static ArrayList<BooleanFormulae> populateTI(ArrayList<TimeIndex> formulae) {
//		int f = formulae.get(0).getIndex();
//		int notF;
//		if (PltlFormula.get(formulae.get(0).getIndex()) instanceof Not)
//			notF = PltlFormula.add(((Not)PltlFormula.get(formulae.get(0).getIndex())).getFormula());
//		else
//			notF = PltlFormula.add(new Not(PltlFormula.get(formulae.get(0).getIndex())));
//		if (formulae.size() == 1) {
//			formulae.add(new TimeIndex(formulae.get(0).getTime(), notF));
//
//			return formulae;
//		}
//		else {
//			formulae.remove(0);
//			ArrayList<BooleanFormulae> populatedCdr = populate(formulae);
//			ArrayList<BooleanFormulae> populatedMain = new ArrayList<BooleanFormulae>();
//			for (int i = 0; i < populatedCdr.size(); i++) {
//				populatedMain.add(new And(f, populatedCdr.get(i)));
//				populatedMain.add(new And(notF, populatedCdr.get(i)));
//			}
//			
//			return populatedMain;
//		}
//	}
	
	public static ArrayList<BooleanFormulae> populate(ArrayList<BooleanFormulae> formulae) {
		BooleanFormulae f = formulae.get(0);
		BooleanFormulae notF = null;
		if (formulae.get(0) instanceof Not)
			notF = ((Not)formulae.get(0)).getFormula();
		else
			notF = new Not(f);
		if (formulae.size() == 1) {
			formulae.add(notF);

			return formulae;
		}
		else {
			formulae.remove(0);
			ArrayList<BooleanFormulae> populatedCdr = populate(formulae);
			ArrayList<BooleanFormulae> populatedMain = new ArrayList<BooleanFormulae>();
			for (int i = 0; i < populatedCdr.size(); i++) {
				populatedMain.add(new And(f, populatedCdr.get(i)));
				populatedMain.add(new And(notF, populatedCdr.get(i)));
			}
			
			return populatedMain;
		}
	}
}
