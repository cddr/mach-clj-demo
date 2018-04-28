(ns mach-demo.main-test
  (:require
    [clojure.test :refer :all]
    [mach-demo.main :as m]))

(deftest test-mach-demo
  (testing "yolo"
    (is (= "Yolo!\n"
           (with-out-str
             (m/-main))))))
