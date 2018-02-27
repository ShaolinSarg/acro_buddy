(ns acro-buddy.core
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.route :refer [not-found files resources]]
            [compojure.handler :refer [site]]
            [cheshire.core :as json :refer [generate-string]]
            [acro-buddy.matching :refer [describe-acronym]]
            [ring.util.response :as resp]
            [shoreleave.middleware.rpc :refer [wrap-rpc]]))

(defroutes handler
  (GET "/" [] (resp/redirect "/home.html"))

  (GET "/acronym/:acro" [acro]
    {:status 200
     :headers {"Content-Type" "application/json; charset=utf-8"}
     :body (json/generate-string (describe-acronym acro))})

  (files "/" {:root "target"})
  (resources "/" {:root "target"})
  (not-found "Page not found"))

(def app (-> (var handler)
             (wrap-rpc)
             (site)))
