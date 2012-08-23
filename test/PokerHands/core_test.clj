(ns PokerHands.core-test
  (:use clojure.test
        PokerHands.core))

(def straight-flush-hand "2H 3H 4H 5H 6H")

(deftest cards-test
	(testing "rank makes a number from the string rank"
		(is (= 2 (rank \2))))
	(testing "c makes a card"
		(is (= (->Card 2 :H) (c "2H")))
		(is (= (->Card 11 :C) (c "JC")))
		(is (= (->Card 10 :D) (c "TD")))
		(is (= (->Card 12 :S) (c "QS"))))
	(testing "hand test"
		(is (= (hand straight-flush-hand)
				[(->Card 2 :H) (->Card 3 :H) (->Card 4 :H) (->Card 5 :H) (->Card 6 :H)])))
	(testing "high cards"
		(is (= (->Card 6 :H) (high (hand "2H 3H 4H 5H 6H"))))
		(is (= (->Card 8 :H) (high (hand "8H 3H 4H 5H 6H")))))
	(testing "ranks of pair"
		(is (= [2] (pairs (hand "2H 2S 4D 5D 6D")) ))
		(is (= [14] (pairs (hand "AH AD 4D 6D 5S"))))
		(is (= [14 13] (pairs (hand "KH KD AD AD 5S")))))
	(testing "3 of a kind"
		(is (= [2] (three-kinds (hand "2H 2S 2C 4D 6D")))))
	(testing "4 of a kind"
		(is (= [] (four-kinds (hand straight-flush-hand)))))
	(testing "full house"
		(is (= [2] (full-house (hand "2H 2S 2C 3S 3C"))))
		(is (= [] (full-house (hand "2H 2S 2C 4D 6D")))))
	(testing "flush"
		(is (= [10 8 6 4 2] (flush1 (hand "2H 4H 6H 8H TH")))))
	)

	