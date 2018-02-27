(ns acro-buddy.matching
  (:require [acro-buddy.data :refer [acronym-data]]))

(defn describe-acronym
  "returns the names and descriptions for a given acornym"
  [acronym]
  (get @acronym-data acronym []))
