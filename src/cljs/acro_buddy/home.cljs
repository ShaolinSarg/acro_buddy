(ns acro-buddy.home
  (:require [reagent.core :as r]
            [domina :as dom]))

(defn home-components []
  [:div
   [:h1 "Welcome to the Acro buddy"]])

(defn ^:export init []
  (r/render [home-components] (dom/by-id "content")))
