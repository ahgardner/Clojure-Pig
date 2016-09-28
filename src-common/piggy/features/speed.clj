; speed feature: display/update the speed

(ns piggy.features.speed
    (:require [play-clj.core :refer :all]
              [play-clj.ui :refer :all]
              [piggy.helper :as h]))

(def ^:const init-pig-speed 10)

(defn create
  "Create readable speed entity"
  [amount]
  (let [init-speed (if (zero? amount) init-pig-speed amount)
        speed-label (assoc (label (str "speed:" init-speed) (color :white)) :x 300 :y h/dashboard-y :speed init-speed)]
    (label! speed-label :set-font-scale 2.0)
    speed-label))

(defn change
  "Change the speed"
  [speed-ent amount]
  (let [curr-speed (:speed speed-ent)
        new-speed (+ curr-speed amount)] 
    (if (neg? new-speed)
      speed-ent
      (create new-speed))))

