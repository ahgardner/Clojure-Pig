; lion feature: lurks out of sight, may leap out on pig

(ns piggy.features.lion
    (:require [play-clj.core :refer :all]
              [play-clj.g2d :refer :all]
              [piggy.helper :as h]))

(defn create []
  (-> (texture "lion.png")
      (assoc :x -200 :y h/bottom :width 200 :height 200)))

(defn ready?
  "The hidden lion will hear you above speed 10, and may jump you
  The faster you go, the more likely he is to notice you.
  However, as you get fatter, he is more hesitant to attack."
  [speed pig-size]
  (if (<= speed 10)
    false
    (let [speed-factor (if (< speed 50) (- 50 speed) 0)
          final-factor (int (* speed-factor pig-size))
          random-number (rand-int final-factor)]
      (= random-number 0))))
        
(defn pounce
  "Move the lion to the pig's location"
  [lion pig]
  (assoc lion :x  (:x pig)))


