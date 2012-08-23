(ns PokerHands.core
	(:use [clojure.string :only [split]])
  (:gen-class))

(def rankmap {\2 2 \3 3 \4 4 \5 5 \6 6 \7 7 \8 8 \9 9 \J 11 \T 10 \Q 12 \K 13 \A 14})
(def suitmap {\H :H \C :C \S :S \D :D})

(defrecord Card [rank suit])

(defn rank [str-rank] (get rankmap str-rank))

(defn c [card] 
	(->Card (rank (first card)) (get suitmap (second card))))

(defn hand [string]
	(->> (split string #" ") (map c)))

(defn high [hand]
	(last (sort-by :rank hand)))

(defn nkind [number hand]
	(->> hand 
		(group-by :rank ,,,)
		(filter #(= (count (second %)) number) ,,,)
		keys
		sort
		reverse))

(defn pairs [hand] (nkind 2 hand))
(defn three-kinds [hand] (nkind 3 hand))
(defn four-kinds [hand] (nkind 4 hand))
(defn full-house [hand] 
	(let [three (three-kinds hand)]
		(if (and (not-empty three) (not-empty (pairs hand)))
			three
			[])))
(defn flush1 [hand] (if (= (count (group-by :suit hand)) 1)
						(->> hand
							(map :rank)
							sort
							reverse)
						[])) 