; axe feature: hovers over path, sometimes drops

(ns piggy.features.axe
    (:require [play-clj.core :refer :all]
              [play-clj.g2d :refer :all]
              [play-clj.math :refer :all]
              [piggy.helper :as h]))

(def ^:const axe-init-x 333)
(def ^:const axe-init-y 500)
(def ^:const init-axe-speed 10)
(def ^:const axe-fall-odds 30)
(def axe-fall-speed 150)

(defn create []
  (-> (texture "axe2.png")
      (assoc :x axe-init-x :y axe-init-y :width 100 :height 100)))

(defn head-box
  "Axe head is approx. the upper right corner of its image"
  [axe]
  (let [x (:x axe)
        y (:y axe)]
    (rectangle (+ x 50) (+ y 50) 50 50)))

(defn do-drop
  [axe]
  (let [current-y (:y axe)
        next-y (- current-y axe-fall-speed)]
    (assoc axe :y (max next-y h/bottom))))
      
(defn do-random-moves
  "Apply random sideways move and drop"
  [axe]
  (let [horizontal (* (dec (rand-int 3)) init-axe-speed)
        drop (zero? (rand-int axe-fall-odds))]
    (if drop
      (do-drop axe)
      (assoc axe :x (+ (:x axe) horizontal)))))         
                   
(defn move
  "Move the axe, either dropping, returning from a drop, or randomly"
  [axe]
  (condp = (:y axe)
    h/bottom  (assoc axe :y axe-init-y)    ; hit bottom - reset
    axe-init-y (do-random-moves axe)       ; at top - randomize
    (do-drop axe)))                        ; dropping - continue
