(ns kata-potter.core)

(def discount-for-set
  {5 0.75
   4 0.80
   3 0.90
   2 0.95
   1 1.00})

(defn possible-totals [book-freqs]
  (into #{}
        (for [set-size [1 2 3 4 5]
              :let [distinct-books-count (count book-freqs)]
              :when (>= distinct-books-count set-size)]
          (loop [book-freqs book-freqs
                 total 0]
            (if (not-any? (fn [[k,v]] (zero? v)) book-freqs)
              (recur (into {} (map (fn [[k,v]] [k (dec v)]) book-freqs))
                     (+ total (* (discount-for-set set-size)
                                 distinct-books-count
                                 8)))
              total)))))


(declare total discount)

(defn discount-groups
  ([books] (discount-groups books [] 5))
  ([books totals set-size]
   (let [freqs (frequencies books)]
     (if (> set-size (count freqs))
       (recur books totals (dec set-size))
       (recur books (conj totals (total books set-size)) (dec set-size))))))



; 2 2 1
; buy 5 separately, 5*8 = 40
; buy one set of 3, one set of 2, 3*8*0.90 + 2*8*0.95 0 =  36.8
; buy two sets of 2, one by itself 4*8*0.95 + 8 = 38.4

; two of same, one other
; 2 at 0.95, one at 1.0

(defn cost [books]
  (let [freqs (frequencies books)
        disc (discount (count freqs))]
    (reduce (fn [total [book number-of-books]]
              (* discount )))))
