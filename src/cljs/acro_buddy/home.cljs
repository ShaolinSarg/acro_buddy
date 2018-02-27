(ns acro-buddy.home
  (:require [reagent.core :as r]
            [domina :as dom]
            [clojure.string :refer [blank? trim]]
            [shoreleave.remotes.http-rpc :refer [remote-callback]]))

(defn acronym-list [results]
  [:div
   (for [{:keys [name description]} @results]
     ^{:key name} [:div
                   [:span.acroName name]
                   [:span.acroDesc description]])])

(defn handle-search [criteria results]
  (let [acronym (trim (:acronym @criteria))]
    (when-not (blank? acronym)
      (remote-callback :describe-acronym-remote
                       [acronym]
                       #(reset! results %)))))

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
                 :on-click #(handle-search criteria results)}]]
       [acronym-list results]])))


(defn home-components []
  [:div
   [search-form]])

(defn ^:export init []
  (r/render [home-components] (dom/by-id "content")))
