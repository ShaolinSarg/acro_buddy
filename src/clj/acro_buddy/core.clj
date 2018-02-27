(ns acro-buddy.core
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.route :refer [not-found files resources]]
            [compojure.handler :refer [site]]
            [acro-buddy.data :refer [acronym-data]]
            [cheshire.core :as json :refer [generate-string]]
            [ring.util.response :as resp]))

(defroutes handler
  (GET "/" [] (resp/redirect "/home.html"))
  (GET "/acronym/:acro" [acro] (json/generate-string (get @acronym-data acro)))
  (files "/" {:root "target"})
  (resources "/" {:root "target"})
  (not-found "Page not found"))

(def app (-> (var handler)
             (site)))
