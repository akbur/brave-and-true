(ns clojure-noob.funcs)

;; Solving a problem with recursion
(defn sum
  ([vals] (sum vals 0))
  ([vals accumulating-total]
   (if (empty? vals)
   accumulating-total
    (sum (rest vals) (+ (first vals) accumulating-total))))

(sum '[2 5 6 9] 9)

;; BUT you should use recur because Clojure does not perform tail
;; call optimization

(defn sum
  ([vals]
   (sum vals 0))
  ([vals accumulating-total]
   (if (empty? vals)
     accumulating-total
     (recur (rest vals) (+ (first vals) accumulating-total)))))

;; Things to do with pure functions

;;COMP
((comp inc *) 2 3)

(def character
              {:name "Smooches McCutes"
               :attributes {:intelligence 10
                            :strength 4
                            :dexterity 5}})
(def c-int (comp :intelligence :attributes))
(def c-str (comp :strength :attributes))
(def c-dex (comp :dexterity :attributes))
(c-int character)
(c-str character)
(c-dex character)

;; could have written...
((fn [c] (:strength (:attributes c))) character)
;; but comp is more elegant - less code more meaning

;; if one of the funcs takes more than 1 arg, wrap in anonymous func
(defn spell-slots
  [char]
  (int (inc (/ (c-int char) 2))))
(spell-slots character)

(def spell-slots-comp (comp int inc #(/ % 2) c-int))

(defn two-comp
  [f g]
  (fn [& args]
    (f (apply g args))))

;;MEMOIZE
(defn sleepy-identity
  "Returns the given value after 1 second"
  [x]
  (Thread/sleep 1000)
  x)
(sleepy-identity "Mr. Fantastico")
; => "Mr. Fantastico" after 1 second
(sleepy-identity "Mr. Fantastico")
; => "Mr. Fantastico" after 1 second


(def memo-sleepy-identity (memoize sleepy-identity))
(memo-sleepy-identity "Mr. Fantastico")
