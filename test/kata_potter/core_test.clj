(ns kata-potter.core-test
  (:require [clojure.test.check :as tc]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [kata-potter.core :refer :all]))

(def uniq-books-cost-8
  (prop/for-all [books (gen/fmap set (gen/vector gen/string))]
                (= (cost books) (* 8 (count books)))))

(tc/quick-check 100 uniq-books-cost-8)
