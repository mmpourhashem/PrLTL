package pltl;

import static org.junit.Assert.assertFalse;

import java.util.ArrayList;

import com.sun.org.glassfish.external.probe.provider.annotations.Probe;

import arith.ArithFormula;
import arith.Op;
import pltl.bool.*;
import pltl.trio.*;

public class Probability {

	public static String getProbFormulae() {
		String s = "";
		int mainFSize = 0;
		int mainCSize = 0;
		int nextFToProcess = 0;
		int nextCToProcess = 0;
		while (mainFSize < PltlFormula.instances.size() || mainCSize < PltlFormula.cProbsTBP.size()) {
			mainFSize = PltlFormula.instances.size();
			mainCSize = PltlFormula.cProbsTBP.size();
			s += getMainSemantics(nextFToProcess, PltlFormula.instances.size() - 1, nextCToProcess, PltlFormula.cProbsTBP.size() - 1);
			nextFToProcess = mainFSize;
			nextCToProcess = mainCSize;
		}

		return s;
	}

	public static String getMainSemantics(int fStartI, int fEndI, int cStartI, int cEndI) {
		String s = "";
		for (int formulaIndex = fStartI; formulaIndex <= fEndI; formulaIndex++) {
			Formula f = PltlFormula.instances.get(formulaIndex);

			//If the formula is an inner formula of a ProbExp, it means that it is not processed nor converted to DNF. <!-- "processed" : this.process, "converted to DNF: PltlFormula.getDNF"-->
			if (PltlFormula.getFormulaString(formulaIndex).length() > 0) {
				Formula newF = PltlFormula.getDNF(process(f));
				int newFIndex = PltlFormula.add(newF);
				if (newFIndex != formulaIndex) {
					s += getEqualitySemantics(formulaIndex, newFIndex);
					continue;
				}
			}

			if (f instanceof And) {
				for (Formula fma: ((And) f).getFormulae())
					assertFalse(hasProbExp(fma)); // PorbExps (like (P((-p- a)) = 0.3) must be at the first level. 
				s += ((And) f).getSemantics();
			}
			else if (f instanceof Or) {
				for (Formula fma: ((Or) f).getFormulae())
					assertFalse(hasProbExp(fma)); // PorbExps (like (P((-p- a)) = 0.3) must be at the first level. 
				s += ((Or) f).getSemantics();
			}
			else if (f instanceof ProbExp) {// ProbExps' simplifications are postponed in order to Parser.java.<Simplification rationale>.
				ProbExp probExp = new ProbExp(((ProbExp) f).getOp(), process(((ProbExp) f).getF1()), process(((ProbExp) f).getF11()), process(((ProbExp) f).getF12()), process(((ProbExp) f).getF2()), process(((ProbExp) f).getF21()), process(((ProbExp) f).getF22()), ((ProbExp) f).getR1(), ((ProbExp) f).getR2(), ((ProbExp) f).toString());
				s += probExp.getSemantics();
			}
			else if (f instanceof Not)
				s += ((Not) f).getSemantics();
			else if (f instanceof Predicate)
				s += ((Predicate) f).getSemantics();
			else if (f instanceof PltlFormula.True)
				s += ((PltlFormula.True) f).getSemantics();
			else if (f instanceof PltlFormula.False)
				s += ((PltlFormula.False) f).getSemantics();
			else if (f instanceof Dist)
				s += ((Dist) f).getSemantics();
			else
				s += f.toString() + " <Not Recognized!>";

			if (! hasProbExp(f))
				s += Smt2Formula.getRangeConstraints(formulaIndex) + "\n";
		}
		//		s += "\n";
		for (int formulaIndex = cStartI; formulaIndex <= cEndI; formulaIndex++) {
			CProb c = PltlFormula.cProbsTBP.get(formulaIndex);
			s += c.getSemantics() + "\n";
		}

		return s;
	}

	private static String getEqualitySemantics(int i1, int i2) {
		String s = ";" + PltlFormula.get(i1).toString() + " = " + PltlFormula.get(i2).toString() + "\n";
		for (int time = 0; time <= PltlFormula.bound; time++) {
			s += "(= " + Smt2Formula.getzot(time, i1) + " " + Smt2Formula.getzot(time, i2) + " ) " + new ArithFormula(Op.EQ, new Prob(time, i1), new Prob(time, i2)) + " ";
		}
		
		return s;
	}

	public static void processDeps(int left, int right, Formula f) {
		if (f instanceof And)
			for (Formula bf: ((And) f).getFormulae())
				processDeps(left, right, bf);
		else if (f instanceof Alw)
			processDeps(0, PltlFormula.bound, ((Alw) f).getFormula());
		else if (f instanceof AlwF)
			processDeps(left, PltlFormula.bound, ((AlwF) f).getFormula());
		else if (f instanceof AlwP)
			processDeps(0, right, ((AlwP) f).getFormula());
		else if (f instanceof Next)
			processDeps(left + 1, right + 1, ((Next) f).getFormula());
		else if (f instanceof Yesterday)
			processDeps(left - 1, right - 1, ((Yesterday) f).getFormula());
		else if (f instanceof Futr)
			processDeps(left + ((Futr) f).getInt(), right + ((Futr) f).getInt(), ((Futr) f).getFormula());
		else if (f instanceof Past)
			processDeps(left - ((Past) f).getInt(), right - ((Past) f).getInt(), ((Past) f).getFormula());
		else if (f instanceof Dist)
			processDeps(left + ((Dist) f).getOffset(), right + ((Dist) f).getOffset(), f);
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
	}

	/**
	 * @param formulae
	 * An atomic formula followed by a subset of its parents.
	 * @return
	 * Breaks down to analyzable formulae.
	 * For example, if (-p- a) depends on (-p- b) and (-p- c) and formulae = {(-p- a) (-p- b)} it returns
	 * (&& (-p- a) (-p- b) (-p- c)) (&& (-p- a) (-p- b) (!! (-p- c)))
	 * It essentially there to infer the following:
	 * P((&& (-p- a) (-p- b))) = P((&& (-p- a) (-p- b) (-p- c))) + P((&& (-p- a) (-p- b) (!! (-p- c))))
	 */
	public static ArrayList<Formula> populateFormulae(ArrayList<Formula> formulae) {
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
			ArrayList<Formula> populatedCdr = populateFormulae(formulae);
			ArrayList<Formula> populatedMain = new ArrayList<Formula>();
			for (int i = 0; i < populatedCdr.size(); i++) {
				populatedMain.add(new And(f, populatedCdr.get(i)).getFlatAnd());
				populatedMain.add(new And(notF, populatedCdr.get(i)).getFlatAnd());
			}

			return populatedMain;
		}
	}

	public static ArrayList<Prob> populateProbs(ArrayList<Prob> probs) {
		ArrayList<Prob> result = new ArrayList<Prob>();
		int baseTime = probs.get(0).getTime();
		ArrayList<Formula> formulae = new ArrayList<Formula>();
		for (Prob p: probs)
			formulae.add(p.getFormula(p.getTime()));
		formulae = populateFormulae(formulae);
		for (Formula f: formulae)
			result.add(new Prob(baseTime, PltlFormula.add(f)));

		return result;
	}

	public static Prob processMainF(Formula f) {
		if (f instanceof And) {
			And newAnd = new And();
			for (Formula fma: ((And) f).getFormulae())
				if (! hasProbExp(fma))
					newAnd.addFormula(process(fma));
				else
					PltlFormula.add(fma);
			if (newAnd.size() > 0) {
				Formula dnf = PltlFormula.getDNF(newAnd);
				return new Prob(0, PltlFormula.add(dnf));
			}
			else
				return null;
		}
		else if (f instanceof Or) {
			Or newOr = new Or();
			for (Formula bf:((Or) f).getFormulae()) {
				assertFalse(hasProbExp(bf));
				newOr.addFormula(PltlFormula.getDNF(bf));
			}

			Formula dnf = PltlFormula.getDNF(newOr);
			return new Prob(0, PltlFormula.add(dnf));
		}
		else
			return processMainF(new And(f));
	}

	/**
	 * @param f
	 * @return
	 * Returns simplified formula. E.g. (|| (next (-p- a)) (next (next (-p- a)))) is returned for (withinf (-p- a) 2).
	 */
	private static Formula process(Formula f) {
		if (f instanceof Next)
			return new Dist(process(((Next) f).getFormula()), 1);
		
		if (f instanceof Yesterday)
			return new Dist(process(((Yesterday) f).getFormula()), -1);
		
		if (f instanceof Futr)
			return new Dist(process(((Futr) f).getFormula()), ((Futr) f).getInt());

		if (f instanceof Past)
			return new Dist(process(((Past) f).getFormula()), -((Past) f).getInt());

		if (f instanceof WithinF) {
			assertFalse(((WithinF) f).getInt() < 1);
			Or or = new Or();
			for (int window = 1; window <= ((WithinF) f).getInt(); window++)
				or.addFormula(new Dist(process(((WithinF) f).getFormula()), window));
			return or;
		}

		if (f instanceof WithinP) {
			assertFalse(((WithinP) f).getInt() < 1);
			Or or = new Or();
			for (int window = 1; window <= ((WithinP) f).getInt(); window++)
				or.addFormula(new Dist(process(((WithinP) f).getFormula()), -window));
			return or;
		}

		if (f instanceof Lasts) {
			assertFalse(((Lasts) f).getInt() < 1);
			And and = new And();
			for (int window = 1; window <= ((Lasts) f).getInt(); window++)
				and.addFormula(new Dist(process(((Lasts) f).getFormula()), window));
			return and;
		}

		if (f instanceof Lasted) {
			assertFalse(((Lasted) f).getInt() < 1);
			And and = new And();
			for (int window = 1; window <= ((Lasted) f).getInt(); window++)
				and.addFormula(new Dist(process(((Lasted) f).getFormula()), -window));
			return and;
		}

		if (f instanceof Not)
			return new Not(process(((Not) f).getFormula()));

		if (f instanceof And) {
			And and = new And();
			for (Formula fma:((And) f).getFormulae())
				and.addFormula(process(fma));
		}

		if (f instanceof Or) {
			Or or = new Or();
			for (Formula fma:((Or) f).getFormulae())
				or.addFormula(process(fma));
		}

		if (f instanceof Until)
			return new Until(process(((Until) f).getFormula1()), process(((Until) f).getFormula2()));

		if (f instanceof Release)
			return new Release(process(((Release) f).getFormula1()), process(((Release) f).getFormula2()));

		if (f instanceof Since)
			return new Since(process(((Since) f).getFormula1()), process(((Since) f).getFormula2()));

		if (f instanceof Trigger)
			return new Trigger(process(((Trigger) f).getFormula1()), process(((Trigger) f).getFormula2()));


		return f;
	}

	public static boolean hasProbExp(Formula fma) {
		if (fma instanceof ProbExp)
			return true;
		if (fma instanceof Not)
			return hasProbExp(((Not) fma).getFormula());
		if (fma instanceof Next)
			return hasProbExp(((Next) fma).getFormula());
		if (fma instanceof Yesterday)
			return hasProbExp(((Yesterday) fma).getFormula());
		if (fma instanceof Futr)
			return hasProbExp(((Futr) fma).getFormula());
		if (fma instanceof Past)
			return hasProbExp(((Past) fma).getFormula());
		if (fma instanceof Dist)
			return hasProbExp(((Dist) fma).getFormula());
		if (fma instanceof Alw)
			return hasProbExp(((Alw) fma).getFormula());
		if (fma instanceof AlwF)
			return hasProbExp(((AlwF) fma).getFormula());
		if (fma instanceof AlwP)
			return hasProbExp(((AlwP) fma).getFormula());
		if (fma instanceof Som)
			return hasProbExp(((Som) fma).getFormula());
		if (fma instanceof SomF)
			return hasProbExp(((SomF) fma).getFormula());
		if (fma instanceof SomP)
			return hasProbExp(((SomP) fma).getFormula());
		if (fma instanceof WithinF)
			return hasProbExp(((WithinF) fma).getFormula());
		if (fma instanceof WithinP)
			return hasProbExp(((WithinP) fma).getFormula());
		if (fma instanceof Lasts)
			return hasProbExp(((Lasts) fma).getFormula());
		if (fma instanceof Lasted)
			return hasProbExp(((Lasted) fma).getFormula());

		return false;
	}
}
