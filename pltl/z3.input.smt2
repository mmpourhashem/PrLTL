(set-option :smtlib2-compliant true)
;Formula table:
;0	(-P- a)
;1	(-P- b)
;2	(= (zot-p (-P- a)) 1.0)
;3	(= (zot-p (-P- b)) 1.0)
;4	(zot 0 -1)
;Conditional probability table:

(declare-fun zot (Int Int) Bool)
(declare-fun zot-p (Int Int) Real)
(declare-fun zot-cp (Int Int Int Int) Real)
(define-fun range ((probability Real)) Bool
	(and (>= probability 0.0) (<= probability 1.0)))

(assert (and 
(= (zot-p 0 4) 1.0) (zot 0 4)
(zot 0 -1) (= (zot-p 0 -1) 1.0) (not (zot 0 -2)) (= (zot-p 0 -2) 0.0) (zot 1 -1) (= (zot-p 1 -1) 1.0) (not (zot 1 -2)) (= (zot-p 1 -2) 0.0) (zot 2 -1) (= (zot-p 2 -1) 1.0) (not (zot 2 -2)) (= (zot-p 2 -2) 0.0) 

;(-P- a)
(range (zot-p 0 0))
(range (zot-p 1 0))
(range (zot-p 2 0))

;(-P- b)
(range (zot-p 0 1))
(range (zot-p 1 1))
(range (zot-p 2 1))

;(= (zot-p (-P- a)) 1.0)
(= (zot 0 2) (= (zot-p 0 0) 1.0))
(= (zot 1 2) (= (zot-p 1 0) 1.0))
(= (zot 2 2) (= (zot-p 2 0) 1.0))
;(= (zot-p (-P- b)) 1.0)
(= (zot 0 3) (= (zot-p 0 1) 1.0))
(= (zot 1 3) (= (zot-p 1 1) 1.0))
(= (zot 2 3) (= (zot-p 2 1) 1.0))
; (zot 0 -1)
(= (zot-p 0 4) 1.0)(= (zot-p 1 4) 1.0)(= (zot-p 2 4) 1.0)(range (zot-p 0 4))
(range (zot-p 1 4))
(range (zot-p 2 4))

))
(check-sat-using qfufnra)
(get-model)
