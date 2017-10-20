(set-option :smtlib2-compliant true)
;K=0
;Formula table:
;0	(&& (-P- a) (-P- c))
;1	(|| (&& (-P- a) (!! (-P- b)) (-P- c)) (&& (-P- a) (-P- b) (-P- c)))
;2	(-P- c)
;3	(-P- b)
;4	(-P- a)
;5	(!! (= (zot-p (&& (-P- a) (-P- c))) (zot-p (|| (&& (-P- a) (!! (-P- b)) (-P- c)) (&& (-P- a) (-P- b) (-P- c))))))
;6	(= (zot-p (&& (-P- a) (-P- c))) (zot-p (|| (&& (-P- a) (!! (-P- b)) (-P- c)) (&& (-P- a) (-P- b) (-P- c)))))
;7	(&& (-P- a) (!! (-P- b)) (-P- c))
;8	(&& (-P- a) (-P- b) (-P- c))
;9	(!! (-P- b))
;10	(zot 0 -2)
;11	(&& (-P- c) (-P- b))
;12	(&& (-P- c) (!! (-P- b)))
;13	(&& (-P- b) (-P- a))
;14	(&& (-P- b) (!! (-P- a)))
;15	(!! (-P- a))
;16	(!! (-P- c))
;Conditional probability table:
;0	(zot-cp 0 9 0 4)
;1	(zot-cp 0 2 0 9)
;2	(zot-cp 0 3 0 4)
;3	(zot-cp 0 2 0 3)
;4	(zot-cp 0 3 0 15)
;5	(zot-cp 0 16 0 9)
;6	(zot-cp 0 16 0 3)
;7	(zot-cp 0 9 0 15)

(declare-fun zot (Int Int) Bool)
(declare-fun zot-p (Int Int) Real)
(declare-fun zot-cp (Int Int Int Int) Real)
(define-fun range ((probability Real)) Bool
	(and (>= probability 0.0) (<= probability 1.0)))

(assert (and 

(zot 0 -1) (= (zot-p 0 -1) 1.0) (not (zot 0 -2)) (= (zot-p 0 -2) 0.0) 
;<ProbExp at T0>
;(!! (= (zot-p (&& (-P- a) (-P- c))) (zot-p (|| (&& (-P- a) (!! (-P- b)) (-P- c)) (&& (-P- a) (-P- b) (-P- c))))))
(not (zot 0 6))

;</ProbExp at T0>

;(&& (-P- a) (-P- c))
(= (zot 0 0) (and (zot 0 4) (zot 0 2)))
(= (zot-p 0 0) (* (zot-p 0 2) (zot-p 0 4)))
(range (zot-p 0 0))

;(|| (&& (-P- a) (!! (-P- b)) (-P- c)) (&& (-P- a) (-P- b) (-P- c)))
(= (zot 0 1) (or (zot 0 7) (zot 0 8)))
(= (zot-p 0 1) (+ (* (zot-cp 0 2 0 9) (* (zot-cp 0 9 0 4) (zot-p 0 4))) (- (* (zot-cp 0 2 0 3) (* (zot-cp 0 3 0 4) (zot-p 0 4))) (zot-p 0 10))))(range (zot-p 0 1))

;(-P- c)
(= (zot-p 0 2) (+ (zot-p 0 11) (zot-p 0 12)))
(range (zot-p 0 2))

;(-P- b)
(= (zot-p 0 3) (+ (zot-p 0 13) (zot-p 0 14)))
(range (zot-p 0 3))

;(-P- a)
(range (zot-p 0 4))

;(!! (= (zot-p (&& (-P- a) (-P- c))) (zot-p (|| (&& (-P- a) (!! (-P- b)) (-P- c)) (&& (-P- a) (-P- b) (-P- c))))))
(= (zot 0 5) (not (zot 0 6)))
;(= (zot-p (&& (-P- a) (-P- c))) (zot-p (|| (&& (-P- a) (!! (-P- b)) (-P- c)) (&& (-P- a) (-P- b) (-P- c)))))
(= (zot 0 6) (= (zot-p 0 0) (zot-p 0 1)))
;(&& (-P- a) (!! (-P- b)) (-P- c))
(= (zot 0 7) (and (zot 0 4) (zot 0 9) (zot 0 2)))
(= (zot-p 0 7) (* (zot-cp 0 2 0 9) (* (zot-cp 0 9 0 4) (zot-p 0 4))))
(range (zot-p 0 7))

;(&& (-P- a) (-P- b) (-P- c))
(= (zot 0 8) (and (zot 0 4) (zot 0 3) (zot 0 2)))
(= (zot-p 0 8) (* (zot-cp 0 2 0 3) (* (zot-cp 0 3 0 4) (zot-p 0 4))))
(range (zot-p 0 8))

;(!! (-P- b))
(= (zot 0 9) (not (zot 0 3)))
(= 1.0 (+ (zot-p 0 9) (zot-p 0 3)))
(range (zot-p 0 9))

; (zot 0 -2)
(= (zot-p 0 10) 0.0)(range (zot-p 0 10))

;(&& (-P- c) (-P- b))
(= (zot 0 11) (and (zot 0 2) (zot 0 3)))
(= (zot-p 0 11) (* (zot-cp 0 2 0 3) (zot-p 0 3)))
(range (zot-p 0 11))

;(&& (-P- c) (!! (-P- b)))
(= (zot 0 12) (and (zot 0 2) (zot 0 9)))
(= (zot-p 0 12) (* (zot-cp 0 2 0 9) (zot-p 0 9)))
(range (zot-p 0 12))

;(&& (-P- b) (-P- a))
(= (zot 0 13) (and (zot 0 3) (zot 0 4)))
(= (zot-p 0 13) (* (zot-cp 0 3 0 4) (zot-p 0 4)))
(range (zot-p 0 13))

;(&& (-P- b) (!! (-P- a)))
(= (zot 0 14) (and (zot 0 3) (zot 0 15)))
(= (zot-p 0 14) (* (zot-cp 0 3 0 15) (zot-p 0 15)))
(range (zot-p 0 14))

;(zot-cp 0 9 0 4)
(= 1.0 (+ (zot-cp 0 9 0 4) (zot-cp 0 3 0 4)))
(range (zot-cp 0 9 0 4))
(= 1.0 (+ (zot-cp 0 9 0 4) (zot-cp 0 3 0 4)))

;(zot-cp 0 2 0 9)
(range (zot-cp 0 2 0 9))
(= 1.0 (+ (zot-cp 0 2 0 9) (zot-cp 0 16 0 9)))

;(zot-cp 0 3 0 4)
(= 1.0 (+ (zot-cp 0 3 0 4) (zot-cp 0 9 0 4)))
(range (zot-cp 0 3 0 4))
(= 1.0 (+ (zot-cp 0 3 0 4) (zot-cp 0 9 0 4)))

;(zot-cp 0 2 0 3)
(range (zot-cp 0 2 0 3))
(= 1.0 (+ (zot-cp 0 2 0 3) (zot-cp 0 16 0 3)))

;(!! (-P- a))
(= (zot 0 15) (not (zot 0 4)))
(= 1.0 (+ (zot-p 0 15) (zot-p 0 4)))
(range (zot-p 0 15))

;(!! (-P- c))
(= (zot 0 16) (not (zot 0 2)))
(= 1.0 (+ (zot-p 0 16) (zot-p 0 2)))
(range (zot-p 0 16))

;(zot-cp 0 3 0 15)
(range (zot-cp 0 3 0 15))
(= 1.0 (+ (zot-cp 0 3 0 15) (zot-cp 0 9 0 15)))

;(zot-cp 0 16 0 9)
(= 1.0 (+ (zot-cp 0 16 0 9) (zot-cp 0 2 0 9)))
(range (zot-cp 0 16 0 9))
(= 1.0 (+ (zot-cp 0 16 0 9) (zot-cp 0 2 0 9)))

;(zot-cp 0 16 0 3)
(= 1.0 (+ (zot-cp 0 16 0 3) (zot-cp 0 2 0 3)))
(range (zot-cp 0 16 0 3))
(= 1.0 (+ (zot-cp 0 16 0 3) (zot-cp 0 2 0 3)))

;(zot-cp 0 9 0 15)
(= 1.0 (+ (zot-cp 0 9 0 15) (zot-cp 0 3 0 15)))
(range (zot-cp 0 9 0 15))
(= 1.0 (+ (zot-cp 0 9 0 15) (zot-cp 0 3 0 15)))

))
(check-sat-using qfufnra)
(get-model)
