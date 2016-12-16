;; declare a namespace
(ns clojure-noob.core
  (:gen-class))

;; entry point to the program
;; -main function runs when you execute 'lein run' at the command line
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "I'm a little teapot!"))

;; CONTROL FLOW
;; IF

(if true
  "By Zeus's hammer!"
  "By Aquaman's trident!")
  ; => "By Zeus's hammer!"

(if false
  "By Zeus's hammer!"
  "By Aquaman's trident!")
  ; => "By Aquaman's trident!"

(if false
  "By Odin's elbow!")
  ; => nil
  ; else branch is optional, if left out and false - returns nil

;; DO
;; do operators lets you wrap up multiple forms in parenthesis

(if true
  (do (println "Success!")
      "By Zeus's hammer!")
  (do (println "Failure!")
      "By Aquaman's trident!"))
  ; => Success!
  ; => "By Zeus's hammer!"

; lets you do multiple things in each of the if expression's branches
; useful for side effects

;; WHEN
;; like a combination of if and do, but with no else branch

(when true
  (println "Success!")
  "abra cadabra")
; => Success!
; => "abra cadabra"

;; BOOLEANS

(nil? 1) ; => false
(nil? nil) ; => true

;; FALSEY - nil & false
;; TRUTHY - everything else!

(if "bears eat beets"
  "bears beets Battlestar Galactica")
; => "bears beets Battlestar Galactica"

(if nil
  "This won't be the result because nil is falsey"
  "nil is falsey")

;; testing for equality
(= 1 1) ; => true
(= nil nil) ; => true
(= 1 2) ; => false

;; AND / OR

;; or returns either the first truthy value or the last value
(or false nil :large_I_mean_venti :why_cant_I_just_say_large)
; => :large_I_mean_venti

(or (= 0 1) (= "yes" "no"))
; => false

(or nil)
; => nil

;; and returns either the first falsey value
  ;; or if no values are falsey, the last truthy value
(and :free_wifi :hot_coffee) ; => :hot_coffee
(and :feelin_super_cool nil false) ; => nil

;; NAMING VALUES WITH DEF
;; you use def to bind a name to a value in Clojure
(def failed-protagonist-names
  ["Larry Potter" "Doreen the Explorer" "The Incredible Bulk"])

failed-protagonist-names
; => ["Larry Potter" "Doreen the Explorer" "The Incredible Bulk"]

; Use the term bind rather than assign. Other languages typically
; encourage you to preform multiple assignments to the same variable
; In Clojure, you'll rarely need to alter a name/value association
; (there's a set of tools for dealing with change)

(defn error-message
  [severity]
  (str "OH GOD! IT'S A DISASTER! WE'RE "
    (if (= severity :mild)
      "MILDLY INCONVENIENCED"
      "DOOMED!!!!")))

(error-message :mild)

;; DATA STRUCTURES
;; Numbers

93 ; integer
1.2 ; float
1/5 ; ratio

;; Strings
"Lord Voldemort"
"\"He who must not be named\""
"\"Great cow of Moscow!\" - Hermes Conrad"

; only double quotes for Strings
; no string interpolation - only concatenation via the str function
(def name "Chewbacca")
(str "\"Uggllglglglglglll\" - " name)

;; MAPS
{} ; empty map
{:first-name "Charlie"
 :last-name "McFishwich"}

; here we associate 'string-key' with the + function
{"string-key" +}

; Maps can be nested
{:name {:first "John" :middle "Jacob" :last "Jingleheimerschmidt"}}
; map values can be of any type

; Besides using map literals you can use the hash-map function
(hash-map :a 1 :b 2)
; => {:b 2, :a 1}

; You can look up values in maps with the get function
(get {:a 0 :b 1} :b)
; => 1

(get {:a 0 :b {:c "ho hum"}} :b)
; => {:c "ho hum"}

; get will return nil if it doesn't find your key
; and you can give it a default value to return
(get {:a 0 :b 1} :c)
; => nil

(get {:a 0 :b 1} :c "unicorns?")
; => "unicorns?"

; the get-in function lets you look up values in nested maps
(get-in {:a 0 :b {:c "ho hum"}} [:b :c])
; => "ho hum"

;; another way to look up a value in a map
({:name "The Human Coffeepot"} :name)
; => "The Human Coffeepot"

;; another cool thing you can do is use keywords as functions
;; to look up their values
;; KEYWORDS

(:a {:a 1 :b 2 :c 3})
; => 1

(:d {:a 1 :b 2 :c 3} "No gnome knows homes like Noah knows")
; => "No gnome knows homes like Noah knows"

;; VECTORS
[3 2 1]

;; zero indexed
(get [3 2 1] 0)

(get ["a" {:name "Pugsley Winterbottom"} "c"] 1)
; => {:name "Pugsley Winterbottom"}

(vector "creepy" "full" "moon")
; =>  ["creepy" "full" "moon"]

; use conj to add at the END
(conj [1 2 3] 4)

;; LISTS
;; similar to vectors in that they're linear collections of values
;; some differences. you can't retrieve list elements with 'get'
'(1 2 3 4)
; => (1 2 3 4)

(nth '(:a :b :c) 0)
; => :a
(noth '(:a :b :c) 2)
; => :c

;; retrieving the nth element from a list is slower than retrieving
;; an element from a vector - because traversal

(list 1 "two" {3 4})
; => (1 "two" {3 4})

(conj '(1 2 3) 4)
;; use conj to add at the BEGINNING
; => (4 1 2 3)

;; When to use vector vs list?
;; LIST - to add easily to beginning of sequence or writing a macro
;; VECTOR - other cases

;; SETS
;; collections of unique values - hash sets & sorted sets
;; focus on hash sets - used more often
; multiple instances of a value become one unique value

#{"kurt vonnegut" 20 :icicle}
(hash-set 1 1 2 2)
; => #{1 2}

(conj #{:a :b} :b)
; => #{:a :b}

(set [3 3 3 4 4])
; => #{3 4}

;; check for set membership using contains?
(contains? #{:a :b} :a)
; => true
(contains? #{:a :b} 3)
; => false
(contains? #{nil} nil)
; => true

;; check for set membership using keyword
(:a #{:a :b})
; => :a

;; check for set membership using get
(get #{:a :b} :a)
; => :a

(get #{:a nil} nil)
; => nil

(get #{:a :b} "kurt vonnegut")
; => nil

;; FUNCTIONS
;; Functions that can either take a function as an argument or
;; return a function are called higher-order functions.
;; Languages with higher-order functions are said to support
;; first-class functions because you can treate functions as
;; values in the same way you treat other data types like numbers
;; and vectors

(inc 1.1)
; => 2.1
(map inc [0 1 2 3])
; => [1 2 3 4]

;; Functionn calls are expression that have a function expression as the operator
;; The other two types of expressions are macro calls and special forms
;; Special forms are "special" because they don't always evaluate
;; all of their operands

;; DEFINING FUNCTIONS

;; Function definition parts:
;; defn, function name, docstring describing the function (optional),
;; parameters listed in brackets, function body

(defn too-enthusiastic
  "Return a cheer that might be a bit too enthusiastic"
  [name]
  (str "OH. MY. GOD. " name " YOU ARE MOST DEFINITELY LIKE THE BEST "
  "MAN SLASH WOMAN EVER I LOVE YOU AND WE SHOULD RUN AWAY SOMEWHERE"))

(too-enthusiastic "Zelda")

;; the docstring
(doc map)

;;  Parameters and Arity
;; The number of parameters is the function's arity
(defn no-params
  []
  "I take no parameters")
(defn one-param
  [x]
  (str "I take one parameter: " x))
(defn two-params
  [x y]
  (str "Two parameters! That's nothing! Pah! I will smoosh them "
  "together to spite you! " x y))

;; Functions also support arity overloading
;; (defining a function so a diff body will run depending on the arity)
(defn multi-arity
  ;; 3-arity arguments and body
  ([first-arg second-arg third-arg]
    (do-things first-arg second-arg third-arg))
  ;; 2-arity arguments and body
  ([first-arg second-arg]
    (do-things first-arg second-arg))
  ;; 1-arity arguments and body
  ([first-arg]
    (do-things first-arg)))

;; Arity Overloading is one way to provide default values for args
(defn x-chop
  "Describe the kind of chop you're inflicting on someone"
  ([name chop-type]
    (str "I " chop-type " chop " name "! Take that!"))
  ([name]
    (x-chop name "karate")))

(x-chop "Kanye West" "slap")
; => "I slap chop Kanye West! Take that!"
(x-chop "Kanye East")
; => "I karate chop Kanye East! Take that!"

;; You can also make each arity do something completely unrelated
;; most likely you would not want to write something like this...
(defn weird-arity
  ([]
    "Destiny dressed you this morning, my friend, and now Fear is
    trying to pull off your pants. If you give up, if you give in,
    you're gonna end up naked with Fear just standing there laughing
    at your dangling unmentionables! - the Tick")
  ([number]
    (inc number)))

(weird-arity)
(weird-arity 5)

;; you can also use a 'rest' parameter
;; "put the rest of these arguments in a list with the following name"
(defn codger-communication
  [whippersnapper]
  (str "Get off my lawn, " whippersnapper "!!!"))
(defn codger
  [& whippersnappers]
  (map codger-communication whippersnappers))
(codger "Billy" "Anne Marie" "The Incredible Bulk")
; => ("Get off my lawn, Billy!!!" "Get off my lawn, Anne Marie!!!" "Get off my lawn, The Incredible Bulk!!!")

(defn favorite-things
  [name & things]
  (str "Hi, " name ", here are my favorite things: "
      (clojure.string/join ", " things)))
(favorite-things "Doreen" "gum" "shoes" "kara-te")

;; Destructuring
;; lets you concisely bind names to values within a collection

;; Return the first element of a collection
(defn my-first
  [[first-thing]] ; Notice that first-thing is within a vector
  first-thing)
(my-first ["oven" "bike" "war-axe"])
; => oven

(defn chooser
  [[first-choice second-choice & unimportant-choices]]
  (println (str "Your first choice is: " first-choice))
  (println (str "Your second choice is: " second-choice))
  (println (str "We're ignoring the rest of your choices. "
                "Here they are in case you need to cry over them: "
                (clojure.string/join ", " unimportant-choices))))

(chooser ["Marmalade", "Handsome Jack", "Pigpen", "Aquaman"])

;; You can also destructure maps
(defn announce-treasure-location
  [{lat :lat lng :lng}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng)))
(announce-treasure-location {:lat 28.22 :lng 81.33})

;; And another way
(defn announce-treasure-location-2
  [{:keys [lat lng]}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng)))
(announce-treasure-location-2 {:lat 100 :lng 50})

;; Function Body
;; returns the last form evaluated
(defn illustrative-function
  []
  (+ 1 304)
  30
  :joe)
(illustrative-function) ; => :joe

;; Anonymous Functions!
(map (fn [name] (str "Hi, " name)) ["Darth Vader", "Mr. Magoo"])
((fn [x] (* x 3)) 8)

;; Another, more compact syntax for anonymous functions:
(#(* % 3) 8)
(map #(str "Hi, " %) ["Kris", "Whitman"])

;; and with multiple arguments
(#(str %1 " and " %2) "soup beans" "cornbread")

;; and with a rest parameter
(#(identity %&) 1 "blarg" :yip)

;; use this style for simple anonymous functions
;; use fn for longer, more complex anonymous functions

;; Returning Functions
;; the returned functions are closures
(defn inc-maker
  "Create a custom incrementor"
  [inc-by]
  #(+ % inc-by))

(def inc3 (inc-maker 3))

(inc3 7)
; => 10

;; LET
;; introduces a new scope
(let [x 3]
  x)
(def dalmatian-list
  ["Pongo" "Perdita" "Puppy 1" "Puppy 2"])
(let [dalmatians (take 2 dalmatian-list)]
  dalmatians)
(def x 0)
(let [x 1] x)

(let [[pongo & dalmatians] dalmatian-list]
  [pongo dalmatians])
; => ["Pongo" ("Perdita" "Puppy 1" "Puppy 2")]

;; LOOP
(loop [iteration 0]
  (println (str "Iteration " iteration))
  (if (> iteration 3)
    (println "Goodbye")
    (recur (inc iteration))))

;; you could accomplish the same by:
;; (loop has better performance)
(defn recursive-printer
  ([]
    (recursive-printer 0))
  ([iteration]
    (println iteration)
    (if (> iteration 3)
      (println "Goodbye!")
      (recursive-printer (inc iteration)))))
(recursive-printer)

;; REGULAR EXPRESSIONS
#"regular-expression"
(re-find #"^left-" "left-eye") ; => "left-"
(re-find #"^left-" "cleft-chin") ; => nil
(re-find #"^left-" "wongleblart") ; => nil

;; REDUCE
(reduce + [1 2 3 4]) ; => 10
;; (+ (+ (+ 1 2) 3) 4 )
;; 1. apply the given function to the first two elements of a sequence (+ 1 2)
;; 2. apply the given function to the result and the next element of the sequence
;; (the result of step 1 is 3, and the next element of the seq is 3 so (+ 3 3)
;; 3. repeat step 2 for every remaining element in the sequence
;; reduce also takes an optional initial value
(reduce + 15 [1 2 3 4]) ; => 25

;; a reduce implementation
(defn my-reduce
  ([f initial coll]
  (loop [result initial
        remaining coll]
    (if (empty? remaining)
      result
      (recur (f result (first remaining)) (rest remaining)))))
  ([f [head & tail]]
    (my-reduce f head tail)))
(my-reduce + 15 [1 2 3 4])

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
