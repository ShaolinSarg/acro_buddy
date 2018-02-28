(ns acro-buddy.matching-test
  (:require [clojure.test :refer [deftest testing is]]
            [acro-buddy.matching :as sut]))

(deftest describe-acronym-tests
  (testing "should return the acronym explanation"
    (testing "when the input matches the details"
      (is (= "Grade 7" (-> (sut/describe-acronym "G7")
                        (first)
                        (:name))))))

  (testing "should return no items"
    (testing "when input doesn not find a match"
      (is (= [] (sut/describe-acronym "asdf"))))))

