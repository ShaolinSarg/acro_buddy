(ns acro-buddy.matching
  (:require [acro-buddy.data :refer [acronym-data]]
            [shoreleave.middleware.rpc :refer [defremote]]))

(defn describe-acronym
  "returns the names and descriptions for a given acornym"
  [acronym]
  (get @acronym-data acronym []))


(defremote describe-acronym-remote
  "remote proxy for describe-acronym"
  [acronym]
  (describe-acronym acronym))
