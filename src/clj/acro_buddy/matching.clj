(ns acro-buddy.matching
  (:require [acro-buddy.data :refer [acronym-data]]
            [shoreleave.middleware.rpc :refer [defremote]]))

(defremote describe-acronym
  "returns the names and descriptions for a given acornym"
  [acronym]
  (get @acronym-data acronym []))
