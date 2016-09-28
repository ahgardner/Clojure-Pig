; Arthur Gardner - Clojure practise code - Piggy game - March 2016

; Inspired by https://gist.github.com/Misophistful/9892203

; V2     3-10-16 Added speed control and the hidden lion
;                Refactored code into three files
; V2.0.1 3-14-16 Added high score saved in a file
; V2.0.2 3-21-16 Further refactoring and idiomatizing
;        3-22-16 Added help button
; V2.1   3-31-16 Growing pig
;                Adjust calculation of when two things touch
;                Fixed flash of help button by making it a texture
; V2.2   4-14-16 Further refactoring (1 clj file per entity)
;                Adjusted the action.  Axe falls visibly.
;                Changed help not to depend on files.

; How to play: see help.clj.

; This file contains the play-clj framework, as shown in the tutorial.

(ns piggy.core
  (:require [piggy.actions :as act]
            [piggy.helper :as h]
            [play-clj.core :refer :all])) 

; The play-clj core follows
        
(defscreen main-screen

  :on-show
  (fn [screen entities]
    (update! screen :renderer (stage) :trap true)
    (add-timer! screen :axe 1 0.1)
    (add-timer! screen :lion 1 1)
    (act/restart-game screen))
        
  :on-render
  (fn [screen entities]
    (let [new-ents (act/do-moves entities)]
      (when (:trap screen) 
        (do 
          (h/pp-log screen)
          (update! screen :trap false)))
      (clear!)
      (render! screen new-ents)))
    
  :on-key-down
  (fn [screen entities]
    (let [game-over (= (:state screen) :stopped)]
      (condp = (:key screen)
         (key-code :dpad-up) (when (not game-over)
                             (act/modify-speed entities 1))
         (key-code :dpad-down) (when (not game-over)
                             (act/modify-speed entities -1))
         (key-code :dpad-right) (when (not game-over)
                             (act/move-pig entities :right))
         (key-code :dpad-left) (when (not game-over)
                             (act/move-pig entities :left))
         (key-code :escape) (act/restart-game screen)
         entities)))
        
  :on-timer
  (fn [screen entities]
    (if (= (:state screen) :stopped)
      entities
      (case (:id screen)
      
        :axe (act/manipulate-axe screen entities)
                    
        :lion (act/manipulate-lion screen entities)
              
        entities)))

  :on-touch-down
  (fn [screen entities]
    (act/check-for-help screen entities)
    entities))
 
(defgame piggy-game
  :on-create
  (fn [this]
    (set-screen! this main-screen)))