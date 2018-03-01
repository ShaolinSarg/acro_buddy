(ns acro-buddy.home
  (:require [reagent.core :as r]
            [domina :as dom]
            [clojure.string :refer [blank? trim]]
            [shoreleave.remotes.http-rpc :refer [remote-callback]]))

(def results (r/atom []))

(defn acronym-list []
  [:div.row
   (for [{:keys [name description]} @results]
     ^{:key name} [:div.row
                   [:div.col-md-3 name]
                   [:div.col-md-9 description]])])

(defn handle-search [criteria]
  (let [acronym (trim (:acronym @criteria))]
    (when-not (blank? acronym)
      (remote-callback :describe-acronym
                       [acronym]
                       #(reset! results %)))))

(defn search-form []
  (let [criteria (r/atom {:acronym ""})]
    (fn []
      [:div.row
       [:div.col-md-4.offset-md-2
        [:input.form-control {:type "text"
                              :placeholder "Search Hackronym"
                              :value (:acronym @criteria)
                              :on-change #(swap! criteria assoc :acronym (-> %
                                                                             .-target
                                                                             .-value))}]]
       [:div.col-md-4
        [:input.btn.btn-primary {:type "button"
                                 :value "search"
                                 :on-click #(handle-search criteria)}]]
       [acronym-list]])))


(defn home-components []
  [:div
   [search-form]])

(defn ^:export init []
  (r/render [home-components] (dom/by-id "content")))
