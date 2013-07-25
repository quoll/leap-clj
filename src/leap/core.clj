(ns leap.core
  (:require [leap.vector :refer :all]
            [clojure.core.async :refer :all])
  (:import [com.leapmotion.leap Controller Listener Frame Finger FingerList
                                Gesture Gesture$State Gesture$Type GestureList
                                Hand HandList InteractionBox Vector
                                Pointable PointableList Pointable$Zone
                                Tool ToolList]))

(defn zone [^Pointable p]
  (let [^Pointable$Zone z (.touchZone p)]
    ({Pointable$Zone/ZONE_HOVERING :hovering
      Pointable$Zone/ZONE_NONE :none
      Pointable$Zone/ZONE_TOUCHING :touching} z :none)))

(defn pointable [^Pointable p]
  (if (.isValid p)
    {:id (.id p)
     :direction (vect (.direction p))
     ; :frame (.frame p)
     ; :hand (.hand p)
     :length (double (.length p))
     :stabilized-tip-position (.stabilizedTipPosition p)
     :time-visible (.timeVisible p)
     :tip-position (vect (.tipPosition p))
     :tip-velocity (vect (.tipVelocity p))
     :touch-distance (double (.touchDistance p))
     :touchZone (zone p)
     :width (.width p)}))

(defn fingers [^FingerList fl]
  {:all (keep pointable (for [i (range 0 (.count fl))] (.get fl i)))
   :leftmost (pointable (.leftmost fl))
   :rightmost (pointable (.rightmost fl))
   :frontmost (pointable (.frontmost fl))})

(defn tools [^ToolList tl]
  {:all (keep pointable (for [i (range 0 (.count tl))] (.get tl i)))
   :leftmost (pointable (.leftmost tl))
   :rightmost (pointable (.rightmost tl))
   :frontmost (pointable (.frontmost tl))})

(defn pointables [^PointableList pl]
  {:all (keep pointable (for [i (range 0 (.count pl))] (.get pl i)))
   :leftmost (pointable (.leftmost pl))
   :rightmost (pointable (.rightmost pl))
   :frontmost (pointable (.frontmost pl))})

(defn hand [^Hand h]
  (if (.isValid h)
    {:direction (vect (.direction h))
     :fingers (fingers (.fingers h))
     ; :frame (.frame h)
     :id (.id h)
     :palm-normal (vect (.palmNormal h))
     :palm-position (vect (.palmPosition h))
     :palm-velocity (vect (.palmVelocity h))
     :pointables (pointables (.pointables h))
     :sphere-center (vect (.sphereCenter h))
     :sphere-radius (double (.sphereRadius h))
     :stabilized-palm-position (vect (.stabilizedPalmPosition h))
     :time-visible (double (.timeVisible h))
     :tools (tools (.tools h)) }))

(defn hands [^HandList hl]
  {:all (keep hand (for [i (range 0 (.count hl))] (.get hl i)))
   :frontmost (hand (.frontmost hl))
   :leftmost (hand (.leftmost hl))
   :rightmost (hand (.rightmost hl))})

(defn gesture-state [^Gesture g]
  (let [^Gesture$State gs (.state g)]
    ({Gesture$State/STATE_INVALID :invalid
      Gesture$State/STATE_START :start
      Gesture$State/STATE_STOP :stop
      Gesture$State/STATE_UPDATE :update} gs :invalid)))

(defn gesture-type [^Gesture g]
  (let [^Gesture$Type gt (.type g)]
    ({Gesture$Type/TYPE_CIRCLE :circle
      Gesture$Type/TYPE_INVALID :invalid
      Gesture$Type/TYPE_KEY_TAP :key-tap
      Gesture$Type/TYPE_SCREEN_TAP :screen-tap
      Gesture$Type/TYPE_SWIPE :swipe} gt :invalid)))

(defn gesture [^Gesture g]
  (if (.isValid g)
    {:duration (.duration g)
     :durationSeconds (double (.durationSeconds g))
     ; :frame (.frame g)
     :hands (hands (.hands g))
     :id (.id g)
     :pointables (pointables (.pointables g))
     :state (gesture-state g)
     :type (gesture-type g)}))

(defn gestures [^GestureList gl]
   (keep gesture (for [i (range 0 (.count gl))] (.get gl i))))

(defn interaction-box [^Frame f]
 (let [ib (.interactionBox f)]
   (if (.isValid f)
     {:center (vect (.center ib))
      :depth (double (.depth ib))
      :height (double (.height ib))
      :width (double (.width ib)) })))

(defn fingers-f [^Frame f] (fingers (.fingers f)))
(defn hands-f [^Frame f] (hands (.hands f)))
(defn pointables-f [^Frame f] (pointables (.pointables f)))
(defn gestures-f [^Frame f] (gestures (.gestures f)))
(defn tools-f [^Frame f] (tools (.tools f)))

(defn frame-info [^Frame f]
  (if (.isValid f)
    {:fps (double (.currentFramesPerSecond f))
     :fingers (fingers-f f)
     :gestures (gestures-f f)
     :hands (hands-f f)
     :id (.id f)
     :interaction-box (interaction-box f)
     :pointables (pointables-f f)
     :timestamp (.timestamp f)
     :tools (tools-f f)
     :data f}))

(defn dirn
  "Get a direction string. This is very naive, as it calculates along each vector twice"
  [v]
  (cond (< (angle v up) 0.4) "up"
        (< (angle v down) 0.4) "down"
        (< (angle v left) 0.4) "left"
        (< (angle v right) 0.4) "right"
        (< (angle v forward) 0.4) "forward"
        (< (angle v backward) 0.4) "backward"
        :default nil))

