(ns leap.vector
  (:import [com.leapmotion.leap Vector]))

(defprotocol LVector
  (angle [v1 v2] "The angle in radians between two vectors")
  (cross [v1 v2] "The cross product between two vectors")
  (distance [v1 v2] "The distance between points specified by two vectors")
  (divide [v s] "Divides a vector by a scalar")
  (dot [v1 v2] "The dot product of two vectors")
  (magnitude [v] "The magnitude of a vector")
  (magnitude-sq [v] "The magnitude of a vector squared")
  (minus [v1 v2] "Subtracts one vector from another")
  (normalize [v] "The normalized value of the vector")
  (opposite [v] "A vector pointing in the opposite direction")
  (pitch [v] "The pitch angle in radians")
  (plus [v1 v2] "Adds vectors")
  (roll [v] "The roll angle in radians")
  (times [v s] "Multiplies a vector by a scalar")
  (yaw [v] "The yaw angle in radians"))

(declare vect)

(defrecord LeapVector [x y z data]
  LVector
  (angle [{^Vector v1 :data} {^Vector v2 :data}] (.angleTo v1 v2))
  (cross [{^Vector v1 :data} {^Vector v2 :data}] (vect (.cross v1 v2)))
  (distance [{^Vector v1 :data} {^Vector v2 :data}] (.distanceTo v1 v2))
  (divide [{^Vector v :data} s] (vect (.divide v s)))
  (dot [{^Vector v1 :data} {^Vector v2 :data}] (.dot v1 v2))
  (magnitude [{^Vector v :data}] (.magnitude v))
  (magnitude-sq [{^Vector v :data}] (.magnitudeSquared v))
  (minus [{^Vector v1 :data} {^Vector v2 :data}] (vect (.minus v1 v2)))
  (normalize [{^Vector v :data}] (vect (.normalize v)))
  (opposite [{^Vector v :data}] (vect (.opposite v)))
  (pitch [{^Vector v :data}] (.pitch v))
  (plus [{^Vector v1 :data} {^Vector v2 :data}] (vect (.plus v1 v2)))
  (roll [{^Vector v :data}] (.roll v))
  (times [{^Vector v :data} s] (.times v s))
  (yaw [{^Vector v :data}] (.yaw v)))

(defn vect
  "Creates a LeapVector"
  ([^Vector v] (->LeapVector (.getX v) (.getY v) (.getZ v) v))
  ([^double x ^double y ^double z] (->LeapVector x y z (Vector. x y z))))

(def backward (vect (Vector/backward)))
(def down (vect (Vector/down)))
(def forward (vect (Vector/forward)))
(def left (vect (Vector/left)))
(def right (vect (Vector/right)))
(def up (vect (Vector/up)))
(def x-axis (vect (Vector/xAxis)))
(def y-axis (vect (Vector/yAxis)))
(def z-axis (vect (Vector/zAxis)))
(def zero (vect (Vector/zero)))

