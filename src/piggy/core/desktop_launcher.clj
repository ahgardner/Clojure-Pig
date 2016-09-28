(ns piggy.core.desktop-launcher
  (:require [piggy.core :refer :all])
  (:import [com.badlogic.gdx.backends.lwjgl LwjglApplication]
           [org.lwjgl.input Keyboard])
  (:gen-class))

(defn -main
  []
  (LwjglApplication. piggy-game "piggy" 800 600)
  (Keyboard/enableRepeatEvents true))
