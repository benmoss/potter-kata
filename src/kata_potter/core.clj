(ns kata-potter.core)

(def discount-for-set
  {5 3/4
   4 4/5
   3 9/10
   2 19/20
   1 1})

(defn total-for-set-size [set-size]
  (let [discount (discount-for-set set-size)]
    (* set-size discount 8)))

(defn grab-a-set [book-freqs set-size]
  (when (>= (count book-freqs) set-size)
    (reduce (fn [acc [title freq]] (if (= (count acc) set-size) acc (conj acc title)))
          #{}
          book-freqs)))

(defn dec-keys [m ks]
  (into {} (map (fn [[k v]] (if (contains? ks k)
                                     [k (dec v)]
                                     [k v]))
                m)))

(defn remove-zeros [m]
  (reduce (fn [acc [k v]] (if (zero? v)
                            acc
                            (conj acc [k v]))) {} m))

(defn apply-or [f coll default]
  (if (seq coll)
    (apply f coll)
    (f default)))

(defn possible-totals [book-freqs]
  (into #{}
        (for [set-size [1 2 3 4 5]
              :when (>= (count book-freqs) set-size)]
          (loop [book-freqs book-freqs
                 total 0]
            (let [book-set (grab-a-set book-freqs set-size)]
              (if (seq book-set)
                (recur (remove-zeros (dec-keys book-freqs book-set))
                       (+ total (total-for-set-size set-size)))
                (+ total (apply-or min (possible-totals book-freqs) 0))))))))


(defn cost [books]
  (let [freqs (frequencies books)
        totals (possible-totals freqs)]
    (when (seq totals)
      (float (apply min totals)))))
