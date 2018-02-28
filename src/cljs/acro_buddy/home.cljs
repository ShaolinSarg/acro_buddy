(ns acro-buddy.home
  (:require [reagent.core :as r]
            [domina :as dom]
            [clojure.string :refer [blank? trim]]
            [shoreleave.remotes.http-rpc :refer [remote-callback]]))

(def results (r/atom []))

(defn acronym-list []
  [:div
   (for [{:keys [name description]} @results]
     ^{:key name} [:div
                   [:span.acroName name]
                   [:span.acroDesc description]])])

(defn handle-search [criteria]
  (let [acronym (trim (:acronym @criteria))]
    (when-not (blank? acronym)
      (remote-callback :describe-acronym-remote
                       [acronym]
                       #(reset! results %)))))

(defn search-form []
  (let [criteria (r/atom {:acronym ""})]
    (fn []
      [:div
       [:form
        [:input {:type "text"
                 :placeholder "Search Hackronym here"
                 :value (:acronym @criteria)
                 :on-change #(swap! criteria assoc :acronym (-> % .-target .-value))}]
        [:input {:type "button"
                 :value "search"
                 :on-click #(handle-search criteria)}]]
       [acronym-list]])))


(defn home-components []
  [:div
   [search-form]])

(defn ^:export init []
  (r/render [home-components] (dom/by-id "content")))
