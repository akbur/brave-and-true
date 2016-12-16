;; CHAPTER 3 EXERCISES
;; 1. Use the str, vector, list, hash-map, and hash-set functions.
(ns clojure-noob.exercises)

(str "Hello " "there I am " "combining strings.")
(vector 1 2 3 4 5) ; => [1 2 3 4 5]
(list 1 2 3 4 5) ; => (1 2 3 4 5)
(hash-map :a 1 :b 2 :c 3 :d 2) ; => {:c 3, :b 2, :d 2, :a 1}
(hash-set :a 1 :b 2 :c 3 :d 2) ; => #{1 :c 3 2 :b :d :a}

;; 2. Write a function that takes a number and adds 100 to it
(defn inc-by-one-hundred
  "Takes a number and increments it by 100"
  [number]
  (+ number 100))
(inc-by-one-hundred 100) ; => 200

;; 3. Write a function, dec-maker ...
(defn dec-maker
  [number]
  #(- % number))

(def dec9 (dec-maker 9))
(dec9 10) ; => 1

;; 4. Write a function, mapset, that works like map
;; except the return value is a set
(defn mapset
  [f collection]
  (set (map f collection)))
(mapset inc [1 1 2 2 2])

;; 5. Create a function that's similar to symmetrize-body-parts except
;; that it has to work with weird space aliens with radial symmetry.
;; Instead of two eyes, arms, legs, and so on, they have 5

;; TODO

;; 6. Create a functoin that generalizes symmetrize-body-parts and
;; the function above. The new func should take a collection of body parts and
;; the number of matching body parts to add.

;; TODO
