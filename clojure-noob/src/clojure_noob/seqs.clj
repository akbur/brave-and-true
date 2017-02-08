(ns clojure-noob.seqs)
;; Chapter 4

;; Programming to Abstractions
;; Clojure defines functions like map and reduce in terms of the sequence
;; abstraction, not in terms of specific data structures
;; Think of abstractions as named collections of operations
;; Map doesn't care about how lists, vectors, sets and maps are implemented
;; It only cares about whether it can perform sequence operations on them

;; Treating Lists, Vectors, Sets and Maps as Sequences

;; Sequence - a collection of elements organized in linear order
;; Map, reduce and other sequence functions "take a seq"

(defn titleize
  [topic]
  (str topic " for the Brave and True"))

(map titleize ["Hamsters" "Ragnarok"])
; => ("Hamsters for the Brave and True" "Ragnarok for the Brave and True")
(map titleize '("Empathy" "Decorating"))
; => ("Empathy for the Brave and True" "Decorating for the Brave and True")
(map titleize #{"Elbows" "Soap Carving"})
; => ("Elbows for the Brave and True" "Soap Carving for the Brave and True")
(map #(titleize (second %)) {:uncomfortable-thing "Winking"})
; => ("Winking for the Brave and True")

; first, rest, and cons ...

;; indirection is a generic term for the mechanisms a language employs
;; so that one name can have multiple , related meanings
;; polymorphism is one way that Clojure provides indirection
;; polymorphic functions dispatch to different function bodies based on
;; the type of the argument supplied

(seq '(1 2 3))
(seq [1 2 3])
(seq #{1 2 3})
(seq {:name "Bill Compton" :occupation "Dead mopey guy"})
; => ([:name "Bill Compton"] [:occupation "Dead mopey guy"])

;; seq always returns a value that looks and behaves like a list
(into {} (seq {:a 1 :b 2 :c 3}))

;; Seq function Examples

;; MAP
(map inc [1 2 3])
(map str ["a" "b" "c"] ["A" "B" "C"]) ; => ("aA" "bB" "cC")

(def human-consumption   [8.1 7.3 6.6 5.0])
(def critter-consumption [0.0 0.2 0.3 1.1])
(defn unify-diet-data
  [human critter]
  {:human human
  :critter critter})
(map unify-diet-data human-consumption critter-consumption)
; => ({:human 8.1, :critter 0.0} {:human 7.3, :critter 0.2} {:human 6.6, :critter 0.3} {:human 5.0, :critter 1.1})

(def sum #(reduce + %))
(def avg #(/ (sum %) (count %)))
(defn stats
  [numbers]
  (map #(% numbers) [sum count avg]))
(stats [3 4 10])
(stats [80 1 44 13 6])

(def identities
  [{:alias "Batman" :real "Bruce Wayne"}
   {:alias "Spider-Man" :real "Peter Parker"}
   {:alias "Santa" :real "Your mom"}
   {:alias "Easter Bunny" :real "Your dad"}])

(map :real identities)

;; REDUCE
;; showing a couple other uses that aren't as obvious

;; To transform a map's values
(reduce (fn [new-map [key val]]
  (assoc new-map key (inc val)))
  {}
  {:max 30 :min 10})
; => {:max 31, :min 11}

;; To filter out keys from a map based on their value
(reduce (fn [new-map [key val]]
  (if (> val 4)
    (assoc new-map key val)
    new-map))
    {}
    {:human 4.1
      :critter 3.9})

;; TAKE, DROP, TAKE-WHILE, DROP-WHILE
(take 3 [1 2 4 5 6 64 3 2 1 3 5 ])
; => (1 2 4)

(drop 3 [1 2 4 5 6 64 3 2 1 3 5 ])
; => (5 6 64 3 2 1 3 5)

;; take-while drop-while takes a predicate function (a function whose
;; return value is evaluated for truth or falsity) to determine when
;; it should stop taking or dropping
(def food-journal
  [{:month 1 :day 1 :human 5.3 :critter 2.3}
   {:month 1 :day 2 :human 5.1 :critter 2.0}
   {:month 2 :day 1 :human 4.9 :critter 2.1}
   {:month 2 :day 2 :human 5.0 :critter 2.5}
   {:month 3 :day 1 :human 4.2 :critter 3.3}
   {:month 3 :day 2 :human 4.0 :critter 3.8}
   {:month 4 :day 1 :human 3.7 :critter 3.9}
   {:month 4 :day 2 :human 3.7 :critter 3.6}])

;; ex: retrieve just Jan and Feb data
(take-while #(< (:month %) 3) food-journal)

;; ex: retrieve data after Feb
(drop-while #(< (:month %) 3) food-journal)

;; use together to get data for Feb and March only
(take-while #(< (:month %) 4)
  (drop-while #(< (:month %) 2) food-journal))

;; FILTER AND SOME

;; use filter to return all elements of a sequence that test true
;; for a predicate function
(filter #(< (:human %) 5) food-journal)

;; sometimes take-while can be more efficient (such as when data is sorted)
;; because filter will process all your data

;; some tests whether a collection contains any values that test true
;; for a predicate function
(some #(> (:critter %) 5) food-journal)
; => nil
(some #(> (:critter %) 3) food-journal)
; => true

;; if you want to return the entry:
(some #(and (> (:critter %) 3) %) food-journal)
; => {:month 3 :day 1 :human 4.2 :critter 3.3}

;; SORT AND SORT BY

;; sort by ascending order
(sort [3 1 2]) ; => (1 2 3)

;; apply a function to determine sort order
(sort-by count ["aaa" "c" "bb"]) ; => ("c" "bb" "aaa")

;; CONCAT appends the members of one sequence to the end of another
(concat [1 2] [3 4]) ; => (1 2 3 4)

;; LAZY SEQS
;; "Many functions including map and filter return a lazy seq.
;; A lazy seq is a seq whose members aren't computed until you try to
;; access them. Computing a seqs members is called realizing the seq.
;; Deferring the computation until the moment it's needed makes your
;; programs more efficient, and has the suprising benefit of allowing
;; you to construct infinite sequences.

;; Example Vampire Database
(def vampire-database
  {0 {:makes-blood-puns? false, :has-pulse? true  :name "McFishwich"}
   1 {:makes-blood-puns? false, :has-pulse? true  :name "McMackson"}
   2 {:makes-blood-puns? true,  :has-pulse? false :name "Damon Salvatore"}
   3 {:makes-blood-puns? true,  :has-pulse? true  :name "Mickey Mouse"}})

(defn vampire-related-details
  [social-security-number]
  (Thread/sleep 1000)
  (get vampire-database social-security-number))

(defn vampire?
  [record]
  (and (:makes-blood-puns? record)
       (not (:has-pulse? record))
       record))

(defn identify-vampire
  [social-security-numbers]
  (first (filter vampire?
                 (map vampire-related-details social-security-numbers))))

(time (vampire-related-details 0))

;; You can think of a lazy sequence as containing two parts:
;; A recipe for how to realize the elements of a sequence, and the elements
;; that have been realized so far
(time (def mapped-details (map vampire-related-details (range 0 1000000))))
; => "Elapsed time: 0.062384 msecs"

(time (first mapped-details))
; => "Elapsed time: 32094.256557 msecs"
; => {:makes-blood-puns? false, :has-pulse? true, :name "McFishwich"}

;; the reason this takes 32 seconds, rather than one second, is because
;; Clojure chunks its lazy sequences. Thankfully, lazy seqs only need to
;; be realized once.

(time (first mapped-details))
; => "Elapsed time: 0.045982 msecs"
; => {:makes-blood-puns? false, :has-pulse? true, :name "McFishwich"}

(time (identify-vampire (range 0 1000000)))
; => "Elapsed time: 32111.883753 msecs"
; => {:makes-blood-puns? true, :has-pulse? false, :name "Damon Salvatore"}

;; INFINITE SEQUENCES
(concat (take 8 (repeat "na")) ["Batman!"])

(take 3 (repeatedly #(rand-int 10)))

(defn even-numbers
  ([] (even-numbers 0))
  ([n] (cons n (lazy-seq (even-numbers (+ n 2))))))

(take 10 (even-numbers))
(take 10 (even-numbers 6))

;; to understand the recursion in the example above it's helfpul
;; to remember that cons returns a new list with an element appended
;; the the given list

(cons 0 '(2 4 6))
; => (0 2 4 6)

;; The Collection Abstraction
;; Where the sequence abstraction is about operating on members
;; individually, the collection abstraction is about the data
;; structure as a whole
;; ex: count, empty?, every? is about the whole
(empty? []) ;=> true
(empty? ["no!"]) ; => false

;; INTO
;; most seq functions return a seq rather than the original data
;; strucutre, and into lets you convert back
(map identity {:sunlight-reaction "Glitter!"})
; => ([:sunlight-reaction "Glitter!"])

(into {} (map identity {:sunlight-reaction "Glitter!"}))
; => {:sunlight-reaction "Glitter!"}

;; works with vectors too
(map identity [:garlic :sesame-oil :fried-eggs])
; => (:garlic :sesame-oil :fried-eggs)
(into [] (map identity [:garlic :sesame-oil :fried-eggs]))
; => [:garlic :sesame-oil :fried-eggs]

;; and set
(map identity [:garlic-clove :garlic-clove])
; => (:garlic-clove :garlic-clove)
(into #{} (map identity [:garlic-clove :garlic-clove]))
; => #{:garlic-clove}

;; the first argument does not have to be empty
(into {:favorite-emotion "gloomy"} [[:sunlight-reaction "Glitter!"]])
; => {:favorite-emotion "gloomy" :sunlight-reaction "Glitter!"}

(into ["cherry"] ["pine" "spruce"])
;; great at taking two collections and
;; adding all the elements from the second to the first

;; CONJ
;; also adds elements to a collection but in a diff way
(into [0] [1]) ; => [0 1]
(conj [0] [1]) ; => [0 [1]]
(conj [0] 1)   ; => [0 1]

(conj [0] 1 2 3) ; => [0 1 2 3]
 (conj {:time "midnight"} [:place "ye olde cemetarium"])
; => {:place "ye olde cemetarium" :time "midnight"}

;; defining conj in terms of into
(defn my-conj
  [target & additions]
  (into target additions))

;; FUNCTION FUNCTIONS

;; APPLY
;; apply explodes a seqable data structure so it can be passed
;; to a function that expects a rest parameter
(max 0 1 2) ; => 2
(max [0 1 2]) ; => [0 1 2] (not what we want)
(apply max [0 1 2]) ; => 2

;; defning into in terms of conj
(defn my-into
  [target additions]
  (apply conj target additions))

(my-into [0] [1 2 3]) ; => [0 1 2 3]
;; This call to my-into is equivalent to calling:
(conj [0] 1 2 3)

;; Pattern not uncommon. You'll often see 2 funcs that do the same
;; thing except one takes a rest parameter (conj) and one takes
;; a seqable data structure (into)

;; PARTIAL
;; partial takes a func and any number or arguments. It then returns a new func.
;; When you call the returned func, it calls the original func with the original
;; arguments you supplied it along with the new arguments
(def add10 (partial + 10))
(add10 3)

(def add-missing-elements
  (partial conj ["water" "earth" "air"]))

(add-missing-elements "unobtanium" "adamantium")

;; here's how you might define it
(defn my-partial
  [partialized-fn & args]
  (fn [& more-args]
    (apply partialized-fn (into args more-args))))

(def add20 (my-partial + 20))

(add20 3)

;; in this example, the value of add20 is the anonymous func returned by my-partial
;; defined like this:
(fn [& more-args]
  (apply + (into [20] more-args)))

(defn lousy-logger
  [log-level message]
  (condp = log-level
    :warn (clojure.string/lower-case message)
    :emergency (clojure.string/upper-case message)))

(def warn (partial lousy-logger :warn))

(warn "Red light ahead")

;; COMPLEMENT
(defn identify-humans
  [social-security-numbers]
  (filter #(not (vampire? %))
          (map vampire-related-details social-security-numbers)))

(def not-vampire? (complement vampire?))
(defn identify-humans
  [social-security-numbers]
  (filter not-vampire?
    (map vampire-related-details social-security-numbers)))

;; here's how you might implement COMPLEMENT
(defn my-complement
  [fun]
  (fn [&args]
    (not (apply fun args))))

(def my-pos? (complement neg?))
(my-pos? 1)
(my-pos? -1)
