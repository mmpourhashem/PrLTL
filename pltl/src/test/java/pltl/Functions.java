package pltl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Test;

import pltl.bool.And;
import pltl.bool.Formula;
import pltl.bool.Not;
import pltl.bool.Or;
import pltl.trio.Predicate;

public class Functions {

	@Test
	public void populate() {
		ArrayList<Formula> input = new ArrayList<Formula>();
		ArrayList<Formula> result = new ArrayList<Formula>();
		ArrayList<Formula> expResult = new ArrayList<Formula>();
		Formula f1 = new Predicate("a");
		Formula f2 = new Predicate("b");
		input.add(new Not(f1));
		input.add(f2);
		result = Probability.populateFormulae(input);
		expResult.add(new And(new Not(f1), new Not(f2))); 	//00
		expResult.add(new And(new Not(f1), f2));			//01
		expResult.add(new And(f1, new Not(f2)));			//10
		expResult.add(new And(f1,f2));						//11
		assertTrue(Parser.bfArrayEqual(result, expResult));
	}

	@Test
	public void flatFormulae() {
		And input = new And(new Predicate("a"), new And(new Predicate("b"), new Predicate("c")), new And(new Or(new Predicate("e"), new Or(new Predicate("f"), new Predicate("g"))),new Predicate("b"), new Predicate("c")));
		System.out.println(input.getFlatAnd().toString());
		And result = new And(new Predicate("a"), new Predicate("b"), new Predicate("c"), new Or(new Predicate("e"), new Predicate("f"), new Predicate("g")));
		assertTrue(result.equals(input.getFlatAnd()));
	}
	
	@Test
	public void getNNF() {
		// input = (!! (&& (-P- x) (|| (-P- y) (-P- z))))
		Not input = new Not(new And(new Predicate("x"), new Or(new Predicate("y"), new Predicate("z"))));
		System.out.println(PltlFormula.getNNF(input));
		assertTrue(PltlFormula.getNNF(input).equals(new Or(new Not(new Predicate("x")), new And(new Not(new Predicate("y")), new Not(new Predicate("z"))))));
		//result = (|| (!! (-P- x)) (&& (!! (-P- y)) (!! (-P- z))))
	}
	
	@Test
	public void isDNF() {
		assertTrue(PltlFormula.isDNF(new Or(new Or(new Predicate("a")), new Predicate("b"))));
	}
	
	@Test
	public void getDNF1() {
		//input = (&& (-p- z) (|| (-p- a) (-p- b) (|| (-p- c) (-p- d))))
		And input = new And(new Predicate("z"), new Or(new Predicate("a"), new Predicate("b"), new Or(new Predicate("c"), new Predicate("d"))));
		System.out.println(PltlFormula.getDNF(input));
		//result = (|| (&& (-P- z) (-P- a)) (&& (-P- z) (-P- b)) (&& (-P- z) (-P- c)) (&& (-P- z) (-P- d)))
		assertTrue(PltlFormula.getDNF(input).equals(new Or(new And(new Predicate("z"), new Predicate("a")), new And(new Predicate("z"), new Predicate("b")), new And(new Predicate("z"), new Predicate("c")), new And(new Predicate("z"), new Predicate("d")))));
		
		//input = (&& (|| (!! (!! (-P- a))) (!! (!! (!! (-P- b))))) (!! (&& (-P- b) (-P- c))))
		And input1 = new And(new Or(new Not(new Not(new Predicate("a"))), new Not(new Not(new Not(new Predicate("b"))))), new Not (new And(new Predicate("b"), new Predicate("c"))));
		System.out.println(input1);
		System.out.println(PltlFormula.getDNF(input1));
		//result = (|| (&& (-P- a) (!! (-P- b))) (&& (-P- a) (!! (-P- c))) (!! (-P- b)) (&& (!! (-P- b)) (!! (-P- c))))
		Or result = new Or(new And(new Predicate("a"), new Not(new Predicate("b"))), new And(new Predicate("a"), new Not(new Predicate("c"))), new Not(new Predicate("b")), new And(new Not(new Predicate("b")), new Not(new Predicate("c"))));
		assertTrue(PltlFormula.getDNF(input1).equals(result));
	}
	
	@Test
	public void getDNF2() {
		//input = (&& (|| (-P- a) (-P- b)) (!! (-P- b)))
		And input = new And(new Or(new Predicate("a"), new Predicate("b")), new Not(new Predicate("b")));
		System.out.println(input);
		Or output = (Or) PltlFormula.getDNF(input);
		System.out.println(output);
		assertTrue(output.equals(new Or(new And(new Not(new Predicate("b")), new Predicate("a")))));
	}
	
	@Test
	public void getDNF3() {
		//input = (&& (|| (-P- a) (!! (-P- a))) (|| (-P- b) (!! (-P- b))))
		And input = new And(new Or(new Predicate("a"), new Not(new Predicate("a"))), new Or(new Predicate("b"), new Not(new Predicate("b"))));
		System.out.println(input);
		Formula output = PltlFormula.getDNF(input);
		System.out.println(output);
		assertTrue(output.equals(new PltlFormula.True()));
	}
	
}
