package pltl;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import pltl.bool.And;
import pltl.bool.BooleanFormulae;
import pltl.bool.Not;
import pltl.trio.Predicate;

public class Functions {

	@Test
	public void populate1() {
		ArrayList<BooleanFormulae> input = new ArrayList<BooleanFormulae>();
		ArrayList<BooleanFormulae> result = new ArrayList<BooleanFormulae>();
		ArrayList<BooleanFormulae> expResult = new ArrayList<BooleanFormulae>();
		BooleanFormulae f1 = new Predicate("a");
		BooleanFormulae f2 = new Predicate("b");
		input.add(new Not(f1));
		input.add(f2);
		result = Probability.populate(input);
		expResult.add(new And(new Not(f1), new Not(f2))); 	//00
		expResult.add(new And(new Not(f1), f2));			//01
		expResult.add(new And(f1, new Not(f2)));			//10
		expResult.add(new And(f1,f2));						//11
		assertTrue(Parser.bfArrayEqual(result, expResult));
	}

}
