; Help button feature

(ns piggy.features.help
    (:require [play-clj.core :refer :all]
              [play-clj.g2d :refer :all]
              [seesaw.core :as ss]
              [seesaw.swingx :as ssx]
              [piggy.helper :as h]))

(def help-text
"How to play:
   
Move pig using left/right arrows.

Eat corn to get a point.

Axe falls randomly and ends game if you are there.

You can increase your speed with up/down. Effective speeds are 10-50. This helps you avoid the axe. However, you are then more likely to be noticed by the lion.

The pig grows a bit each time it eats. A larger pig makes the lion hesitate about pouncing.
     
Press esc to restart the game.
   
Close this window to resume the game.")

(defn create [] "The help button entity"
  (-> (texture "help.png")
      (assoc :x 745 :y 545 :width 50 :height 50)))

(defn is-help?
  [help x y]
  "x, y indicate where in the screen the mouse pointer is."
  (let [hx (:x help)
        hy (:y help)
        hx2 (+ x 50)
        hy2 (+ y 50)]
    (and (>= x hx) (<= x hx2) (>= y hy) (<= y hy2))))

(defn show []
  (-> (ss/frame :title    "Piggy Help"
           :on-close :dispose
           :content  (ssx/label-x :text       help-text
                            :wrap-lines? true
                            :size       [400 :by 300]
                            :border     5
                            :background "#888"
                            :foreground :blue))
    ss/pack!
    ss/show!))
