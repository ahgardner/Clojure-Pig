; best score feature

(ns piggy.features.best-score
    (:require [play-clj.core :refer :all]
              [play-clj.ui :refer :all]
              [clojure.string :as strings]
              [clojure.java.io :as io]
              [piggy.helper :as h]))

(def pref-file "piggy.prefs")

(defn create
  "Create/return the readable score entity"
  [count]
  (let [score-label (assoc (label (str "best:" count) (color :white)) :x 150 :y h/dashboard-y :score count)]
    (label! score-label :set-font-scale 2.0)
    score-label))

(defn value []
  (let [file-exists (.exists (io/as-file pref-file))]
    (if file-exists
      (let [hsstr (slurp pref-file)]
        (read-string (second (strings/split hsstr #"="))))
      0)))

(defn setvalue [new-score]
  (let [old-hi-score (value)]
    (if (> new-score old-hi-score)
      (spit pref-file (str "hi-score=" new-score)))))

