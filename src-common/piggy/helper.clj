; Helper functions for Piggy game

(ns piggy.helper
  (:require [play-clj.core :refer :all]
            [play-clj.g2d :refer :all]
            [play-clj.math :refer :all]
            [clojure.java.io :as io]
            [clojure.pprint :as pp]))

; Some constants

(def ^:const bottom 75)
(def ^:const left 50)
(def ^:const dashboard-y 5)

; Logging

(defn log "Write a log message" [message]
  (with-open [wrtr (io/writer "piggy.log" :append true)]
             (.write wrtr message)))

(defn pp-log "Log an object" [obj]
  (with-open [wrtr (io/writer "piggy.log" :append true)]
             (pp/pprint obj wrtr)))

; Sounds
 
(defn play-oink [] (sound "oink.wav" :play))

(defn play-crunch [] (sound "crunch.wav" :play))
 
; Game utilities
  
(defn pointLR
  "Point the entity in the given direction" 
  [entity direction]
  (if (not= (:direction entity) direction)
    (do
      (texture! entity :flip true false)
      (assoc entity :direction direction))
    entity))
      
(defn move-ent
  "Move an entity"
  [entity rate direction]
  (case direction
    :left (pointLR (update entity :x - rate) :left)
    :right (pointLR (update entity :x + rate) :right)
    nil))   
    
(defn box 
  "Get an entity's rectangle"
  [entity]
  (rectangle (:x entity) (:y entity) (:width entity) (:height entity)))

(defn boxes-overlap?
  "Do two rectangles overlap?"
  [box1 box2]
  (rectangle! box1 :overlaps box2))


