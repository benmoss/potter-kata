(ns kata-potter.core-test
  (:require [clojure.test :refer :all]
            [clojure.test.check :as tc]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.clojure-test :refer [defspec]]
            [kata-potter.core :as kata]))

; (deftest a-test
  ; (testing "FIXME, I fail."
    ; (is (= 0 1))))

(deftest cost
  (testing "a book costs 8"
    (is (= (kata/cost ["a book"]) 8)))
  (testing "two of the same book cost 16"
    (is (= (kata/cost ["a book" "a book"]) 16))))

(deftest possible-totals
  (is (= (kata/possible-totals {1 2}) #{16.0}))
  (is (= (kata/possible-totals {1 1, 2 1}) #{15.20, 16.0}))
  (is (= (kata/possible-totals {1 2, 2 2}) #{32.0, 30.40}))
  (is (= (kata/possible-totals {1 1, 2 1, 3 1, 4 1}) #{32.0 30.4 29.6 25.6})))

(defn no-dups? [coll]
  (= (-> coll set count)
     (count coll)))

(defspec two-distinct-books-discount
  100
  (prop/for-all [books (gen/such-that no-dups? (gen/vector gen/int) 100)]
                (= (kata/cost books) 15.20)))
