(ns acro-buddy.home
  (:require [reagent.core :as r]
            [domina :as dom]
            [clojure.string :refer [blank? trim]]))

(defn acronym-list [results]
  [:div
   (for [{:keys [name description]} @results]
     [:div
      ^{:key name} [:span (str name " - " description)]])])

(defn handle-search [criteria]
  (let [acronym (trim (:acronym @criteria))]
    (when-not (blank? acronym)
      [{:name "HMRC" :description "this is work"}])))

(defn search-form []
  (let [criteria (r/atom {:acronym ""})
        results (r/atom [])]
    (fn []
      [:div
       [:form
        [:input {:type "text"
                 :placeholder "Search Hackronym here"
                 :value (:acronym @criteria)
                 :on-change #(swap! criteria assoc :acronym (-> % .-target .-value))}]
        [:input {:type "button"
                 :value "search"
                 :on-click #(reset! results (handle-search criteria))}]]
       [acronym-list results]])))


(defn home-components []
  [:div
   [search-form]])

(defn ^:export init []
  (r/render [home-components] (dom/by-id "content")))
