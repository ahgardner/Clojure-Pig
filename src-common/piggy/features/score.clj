; score feature: displays the current score

(ns piggy.features.score
    (:require [play-clj.core :refer :all]
              [play-clj.ui :refer :all]
              [piggy.helper :as h]))

(defn create
  "Create/return the readable score entity"
  [count]
  (let [score-label (assoc (label (str "score:" count) (color :white)) :x 5 :y h/dashboard-y :score count)]
    (label! score-label :set-font-scale 2.0)
    score-label))

(defn increment
  "Increment the score"
  [score-entity]
  (let [new-score (inc (:score score-entity))]
    (create new-score)))
