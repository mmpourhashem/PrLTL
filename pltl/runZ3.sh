#!/bin/bash
# !/Applications/z3/bin/
ls > pltl.output.txt
/Applications/z3/bin/z3 z3.input.smt2 pp.decimal=true > z3.output.txt
open pltl.output.txt