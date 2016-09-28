; corn feature: pig's objective

(ns piggy.features.corn
    (:require [play-clj.core :refer :all]
              [play-clj.g2d :refer :all]
              [piggy.helper :as h]))

(def ^:const corn-init-x 650)

(defn create []
  (-> (texture "corn6.png")
      (assoc :x corn-init-x :y h/bottom :width 100 :height 100)))
  
(defn flip
  "Switch the corn between its two positions"
  [corn]
  (let [new-corn-x (if (= (:x corn) h/left) corn-init-x h/left)]
    (assoc corn :x new-corn-x)))
