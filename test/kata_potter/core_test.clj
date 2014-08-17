(ns kata-potter.core-test
  (:require [clojure.test :refer :all]
            [clojure.test.check :as tc]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.clojure-test :refer [defspec]]
            [kata-potter.core :as kata]))

(deftest grab-a-set
  (is (= (kata/grab-a-set {1 1 2 2} 2) #{1 2}))
  (is (= (kata/grab-a-set {1 1} 2) nil)))

(defn decimalize [x]
  (into #{} (map #(int (* 10 %)) x)))

(deftest possible-totals
  (is (= (decimalize (kata/possible-totals {1 2})) #{160}))
  (is (= (decimalize (kata/possible-totals {1 1, 2 1})) #{152, 160}))
  (is (= (decimalize (kata/possible-totals {1 2, 2 2})) #{320, 304}))
  (is (= (decimalize (kata/possible-totals {1 1, 2 1, 3 1, 4 1})) #{320 304 296 256}))
  (is (= (decimalize (kata/possible-totals {1 2, 2 2, 3 2, 4 2})) #{640 608 584 512}))
  (is (= (decimalize (kata/possible-totals {1 2, 2 2, 3 2, 4 1, 5 1})) #{512 640 516 584 616})))

(deftest cost
  (is (= (kata/cost [8]) 8.0))
  (is (= (kata/cost [1 1 2 2 3 3 4 5]) (float 51.2))))
