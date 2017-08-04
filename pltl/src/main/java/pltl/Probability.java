package pltl;

import static org.junit.Assert.assertFalse;

import java.util.ArrayList;

import pltl.bool.And;
import pltl.bool.Formula;
import pltl.bool.Not;
import pltl.bool.Or;
import pltl.trio.Alw;
import pltl.trio.Futr;
import pltl.trio.Next;
import pltl.trio.Predicate;

public class Probability {

	public static String getProbFormulae() {
		String s = "";
		int mainFSize = 0;
		int mainCSize = 0;
		int lastFProcessed = 0;
		int lastCProcessed = 0;
		while (mainFSize < PltlFormula.instances.size() || mainCSize < PltlFormula.cProbsTBP.size()) {
			mainFSize = PltlFormula.instances.size();
			mainCSize = PltlFormula.cProbsTBP.size();
			s += getMainSemantics(lastFProcessed, PltlFormula.instances.size() - 1, lastCProcessed, PltlFormula.cProbsTBP.size() - 1);
			lastFProcessed = mainFSize;
			lastCProcessed = mainCSize;
			//		s += ";<TheNeg inferred>\n" + getTheNegInferredSemantics() + ";</TheNeg inferred>\n";
			//		s += ";<Inferred analysis>\n" + getMainSemantics(mainSize, PltlFormula.instances.size() - 1) + ";<Inferred analysis>\n";
			//		s += ";<Formulae map>\n" + getExhaustivePredicateFormuale() + ";</Formulae map>\n";
			s += getMainSemantics(lastFProcessed, PltlFormula.instances.size() - 1, lastCProcessed, PltlFormula.cProbsTBP.size() - 1);
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
	//		for (int i = 1; i < ps.size(); i++) {
	//			int newfsSize = newfs.size();
	//			for (int j = 0; j < newfsSize; j++) {
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

	//	private static String getFusionSemantics(Formula f) {
	//		String s = "";
	//		int index = PltlFormula.add(f);
	//		if (index != -1)
	//			s += getMainSemantics(index);
	//
	//		return s;
	//	}
	//
	//	private static Formula getFusion(Formula f1, Formula f2) {
	//		Formula f = null;
	//		if (f2 instanceof And) {//In order to avoid (&& (-p- c) (&& (-p- b) (-p- a))), and have (&& (-p- c) (-p- b) (-p- a)) instead.
	//			ArrayList<Formula> newAndFs = ((And) f2).getFormulae();
	//			newAndFs.add(f1);
	//			f = new And(newAndFs);
	//		}
	//		else{
	//			f = new And(f1, f2);
	//		}
	//
	//		return f;
	//	}

	public static String getMainSemantics(int fStartI, int fEndI, int cStartI, int cEndI) {
		String s = "";
		for (int i = fStartI; i <= fEndI; i++) {
			Formula f = PltlFormula.instances.get(i);
			if (PltlFormula.add(f) == PltlFormula.mainF && f instanceof And) {
				And newAnd = new And();
				for (Formula bf:((And) f).getFormulae())
					for (int time = 0; time <= PltlFormula.bound; time++) {
						s += Smt2Formula.getzot(time, PltlFormula.add(bf)) + "\n";
						if (! (bf instanceof ProbExp)) {
							s += Smt2Formula.getOp("=" , "1.0", new Prob(time, PltlFormula.add(bf)).toString());
							newAnd.addFormula(bf);
						}
					}
				s += newAnd.getSemantics();
				continue;
			}
			if (f instanceof And) {
				boolean hasProbExp = false;
				for (Formula fma: ((And) f).getFormulae())
					if (fma instanceof ProbExp)
						hasProbExp = true;
				assertFalse(hasProbExp);
				s += ((And) f).getSemantics();
			}
			else if (f instanceof Or) {
				boolean hasProbExp = false;
				for (Formula fma: ((Or) f).getFormulae())
					if (fma instanceof ProbExp)
						hasProbExp = true;
				assertFalse(hasProbExp);
				s += ((Or) f).getSemantics();
			}
			else if (f instanceof ProbExp)
				s += ((ProbExp) f).getSemantics();
			else if (f instanceof Not)
				s += ((Not) f).getSemantics();
			else if (f instanceof Next)
				s += ((Next) f).getSemantics();
			else if (f instanceof Predicate)
				s += ((Predicate) f).getSemantics();
			else
				s += "Not Recognized!";
		}

		for (int i = cStartI; i <= cEndI; i++) {
			CProb c = PltlFormula.cProbsTBP.get(i);
			s += c.getSemantics();
			//FIXME Process conditional probability
			//				TAKE CARE OF RANGE CONSTRAINT WHILE PROCESSING.
			// while processing a|b = r add also !a|b = 1-r
		}

		return s;
	}

	//	public static String getMainSemantics(int index) {
	//		return getMainSemantics(index, index);
	//	}

	public static String getTheNegInferredSemantics() {
		String s = "";
		//If fma is a formula then simplified (!! fma) is added to the formula instances.
		int n = PltlFormula.instances.size();
		for (int i=0; i < n; i++) {
			Formula f = PltlFormula.instances.get(i);
			s += new Not(f).getTheNegSemantics();
		}

		return s;
	}

	public static void processDeps(int left, int right, Formula f) {
		if (f instanceof And)
			for (Formula bf: ((And) f).getFormulae())
				processDeps(left, right, bf);
		else if (f instanceof Alw)
			processDeps(0, PltlFormula.bound, ((Alw) f).getFormula());
		else if (f instanceof Next)
			processDeps(left + 1, right + 1, ((Next) f).getFormula());
		else if (f instanceof Futr)
			processDeps(left + ((Futr) f).getInt(), right + ((Futr) f).getInt(), ((Futr) f).getFormula());
		else if (f instanceof Dep) {
			Dep d = (Dep) f;
			for (int time = left; time <= right; time++) {
				int depFI = PltlFormula.add(d.getDepF());
				Prob depTI = PltlFormula.getProb(time, depFI);
				for (int i = 0; i < d.getParentsF().size(); i++) {
					int parentFI = PltlFormula.add(d.getParentsF().get(i));
					depTI.addParent(PltlFormula.getProb(time, parentFI));
				}
				PltlFormula.addBayes(depTI);
			}
		}

		//TODO yesterday, past, lasts, lasted, alwf, futr, past, ...		

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

	public static ArrayList<Formula> populate(ArrayList<Formula> formulae) {
		Formula f = formulae.get(0);
		Formula notF = null;

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
			ArrayList<Formula> populatedCdr = populate(formulae);
			ArrayList<Formula> populatedMain = new ArrayList<Formula>();
			for (int i = 0; i < populatedCdr.size(); i++) {
				populatedMain.add(new And(f, populatedCdr.get(i)).getFlatAnd());
				populatedMain.add(new And(notF, populatedCdr.get(i)).getFlatAnd());
			}

			return populatedMain;
		}
	}
}
