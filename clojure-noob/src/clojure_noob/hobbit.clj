(ns clojure-noob.hobbit)

(def asym-hobbit-body-parts [{:name "head" :size 3}
                            {:name "left-eye" :size 1}
                            {:name "left-ear" :size 1}
                            {:name "mouth" :size 1}
                            {:name "nose" :size 1}
                            {:name "neck" :size 2}
                            {:name "left-shoulder" :size 3}
                            {:name "left-upper-arm" :size 3}
                            {:name "chest" :size 10}
                            {:name "back" :size 10}
                            {:name "left-forearm" :size 3}
                            {:name "abdomen" :size 6}
                            {:name "left-kidney" :size 1}
                            {:name "left-hand" :size 2}
                            {:name "left-knee" :size 2}
                            {:name "left-thigh" :size 4}
                            {:name "left-lower-leg" :size 3}
                            {:name "left-achilles" :size 1}
                            {:name "left-foot" :size 2}])

(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

; example using matching-part
(matching-part {:name "left-eye" :size 1})
; => {:name "right-eye", :size 1}
(matching-part {:name "head" :size 3})
; => {:name "head" :size 3}
; (returned as is)

;; given a sequence, continually splits the sequence into a head and tail
;; processes the head, adds it to some result, continues to process tail
(defn symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (loop [remaining-asym-parts asym-body-parts
         final-body-parts []]
    (if (empty? remaining-asym-parts) ; we've processed the entire seq
      final-body-parts  ; can return result
      (let [[part & remaining] remaining-asym-parts]
        ; remaining gets shorter by one element each iteration
        (recur remaining

          (into final-body-parts
            (set [part (matching-part part)])))))))

(symmetrize-body-parts asym-hobbit-body-parts)

;; better symmetrizer with reduce
(defn better-symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set [part (matching-part part)])))
            []
            asym-body-parts))
(better-symmetrize-body-parts asym-hobbit-body-parts)

(defn hit
  [asym-body-parts]
  (let [sym-parts (better-symmetrize-body-parts asym-body-parts)
        body-part-size-sum (reduce + (map :size sym-parts))
        target (rand body-part-size-sum)]
    (loop [[part & remaining] sym-parts
          accumulated-size (:size part)]
    (if (> accumulated-size target)
      part
      (recur remaining (+ accumulated-size (:size (first remaining))))))))

(hit asym-hobbit-body-parts)
