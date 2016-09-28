; Piggy functions requiring the screen, or complex interaction among the entities.

(ns piggy.actions
  (:require [piggy.helper :as h]
            [piggy.features [axe :as axe] [pig :as pig] [corn :as corn]
              [score :as score] [speed :as speed] [help :as help]
              [best-score :as best-score] [lion :as lion]]
            [play-clj.core :refer :all])) 

; A simple-minded way to keep track of entities -
;   a short label for each index

(def ^:const *pig 0)
(def ^:const *axe 1)
(def ^:const *corn 2)
(def ^:const *score 3)
(def ^:const *best-score 4)
(def ^:const *speed 5)
(def ^:const *lion 6)
(def ^:const *help 7)

(defn restart-game
  "Create the entity array.  The order must correlate with the above indices.
   Also initialize status & prefs"
  [screen]
  (let [best-score (best-score/value)]
    (update! screen :state :running)
    (update! screen :best-score best-score)
    (let [pig (pig/create h/left 1.0)
          axe (axe/create)
          corn (corn/create)
          score-label (score/create 0)
          best-score-label (best-score/create best-score)
          speed-label (speed/create 0)
          lion (lion/create)
          help (help/create)]
      [pig axe corn score-label best-score-label speed-label lion help])))

(defn end-game
  "Stop the game"
  [screen entities]
  (update! screen :state :stopped)
  (best-score/setvalue (:score (entities *score)))
  (h/play-oink)
  entities)
 
(defn eat-corn
  "Pig has reached corn. Make a sound, increment score, move corn"
  [entities]
  (h/play-crunch)
  (let [new-score-ent (score/increment (entities *score))
        new-corn (corn/flip (entities *corn))
        new-pig (pig/grow (entities *pig))]
    (assoc entities *score new-score-ent *corn new-corn *pig new-pig)))  

(defn do-moves [entities]
  "New version of pig movement"
  entities)
  
(defn move-pig
  "Move the pig left or right"
  [entities direction]
  (let [pig (entities *pig)
        corn (entities *corn)
        speed (:speed (entities *speed))
        new-pig (h/move-ent pig speed direction)
        new-ents (assoc entities *pig new-pig)]
    (if (h/boxes-overlap? (pig/body-box new-pig) (h/box corn))
      (eat-corn new-ents)
      new-ents)))

(defn manipulate-axe
  "Move the axe, seeing if it has dropped on the pig"
  [screen entities]
   (let [axe (entities *axe)
         pig (entities *pig)
         new-axe (axe/move axe)
         new-ents (assoc entities *axe new-axe)]
     (if (h/boxes-overlap? (axe/head-box new-axe) (pig/body-box pig))
       (end-game screen new-ents)
       new-ents)))             

(defn manipulate-lion
  "Give the lion a chance to pounce on the pig" 
  [screen entities]
  (if (lion/ready? (:speed (entities *speed)) (:size (entities *pig)))
    (let [new-lion (lion/pounce (entities *lion) (entities *pig))
          new-ents (assoc entities *lion new-lion)]
      (end-game screen new-ents))
    entities))

(defn modify-speed
  "Change the speed at which the pig moves"
  [entities amount]
  (let [speed-ent (entities *speed)
        new-speed-ent (speed/change speed-ent amount)]
    (assoc entities *speed new-speed-ent)))

(defn check-for-help
  [screen entities]
  (when (help/is-help? (entities *help) (:input-x screen)
                    (- (height screen) (:input-y screen)))
    (help/show)))
  