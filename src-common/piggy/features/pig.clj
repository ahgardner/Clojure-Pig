; pig feature: the main character, eats as much corn as possible

(ns piggy.features.pig
    (:require [play-clj.core :refer :all]
              [play-clj.g2d :refer :all]
              [play-clj.math :refer :all]
              [piggy.helper :as h]))

(def pig-base-width 136)
(def pig-base-height 90)

(defn create [x size]
  (let [width (int (* pig-base-width size))
        height (int (* pig-base-height size))]
    (-> (texture "piggy.jpg")
        (assoc :x x :y h/bottom :width width :height height
               :direction :left :size size))))

(defn body-box
  "The pig is about the middle half (horizontally) 
   and lower three quarters (vertically) of its image"
  [pig]
  (let [x (:x pig)
        y (:y pig)
        w (:width pig)
        h (:height pig)]
    (rectangle (int (+ x (* w 0.25)))
               y
               (int (* w 0.5))
               (int (* h 0.75)))))
  
(defn grow 
  "Pig gets fatter, up to 2x in both directions."
  [old-pig]
  (let [x (:x old-pig)
        old-size (:size old-pig)
        new-size (* old-size 1.15)]
    (if (< old-size 2)
      (create x new-size)
      old-pig)))
