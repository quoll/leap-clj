(ns leap.demo
  (:require [clojure.core.async :refer :all]
            [leap.core :refer :all])
  (:import [com.leapmotion.leap Controller Listener]))

(defn show-direction
  "Print which way your palm is pointing"
  [^Controller c seconds]
  (let [events (chan)
        listener (proxy [Listener] []
                   (onConnect [c] (println "Connected"))
                   (onDisconnect [c] (println "Disconnected"))
                   (onExit [c] (println "Exiting"))
                   (onFrame [^Controller c]
                     (if-let [f (frame-info (.frame c))]
                       (if-let [h (first (:all (:hands f)))]
                         (when-let [d (dirn (:palm-normal h))] (>!! events d))))))
        end-chan (timeout (* seconds 1000))]
    (.addListener c listener)
    (loop [last-dir nil]
      (when-let [x (alt!! [events end-chan] ([v] v))]
        (when (not= last-dir x) (println x))
        (recur x)))
    (.removeListener c listener)))

(def c (Controller.))

(defn -main [& args]
  (println "Showing palm direction for 20 seconds...")
  (show-direction c 20))
