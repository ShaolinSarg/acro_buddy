(ns acro-buddy.core
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :refer [not-found files resources]]
            [compojure.handler :refer [site]]))

(defroutes handler
  (GET "/" [] "hello from Acro Buddy")
  (files "/" {:root "target"})
  (resources "/" {:root "target"})
  (not-found "Page not found"))

(def app (-> (var handler)
             (site)))
