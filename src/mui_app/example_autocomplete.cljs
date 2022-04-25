(ns mui-app.example-autocomplete
  (:require
   [reagent.core :as r]
   [reagent.dom :as d]
   [reagent-mui.material.autocomplete :as autocomplete]
   [reagent-mui.material.text-field :as text-field]
   [reagent-mui.util :as mui-util]))

(def top-100-films
  [{:label "Denmark"
    :code  "dk"
    :flag  "\uD83C\uDDE9\uD83C\uDDF0"}
   {:label "Finland"
    :code  "fi"
    :flag  "\uD83C\uDDEB\uD83C\uDDEE"}
   {:label "France"
    :code  "fr"
    :flag  "\uD83C\uDDEB\uD83C\uDDF7"}
   {:label "Germany"
    :code  "de"
    :flag  "\uD83C\uDDE9\uD83C\uDDEA"}
   {:label "Iceland"
    :code  "is"
    :flag  "\uD83C\uDDEE\uD83C\uDDF8"}
   {:label "Italy"
    :code  "it"
    :flag  "\uD83C\uDDEE\uD83C\uDDF9"}
   {:label "Norway"
    :code  "no"
    :flag  "\uD83C\uDDF3\uD83C\uDDF4"}
   {:label "Spain"
    :code  "es"
    :flag  "\uD83C\uDDEA\uD83C\uDDF8"}
   {:label "Sweden"
    :code  "se"
    :flag  "\uD83C\uDDF8\uD83C\uDDEA"}
   {:label "United Kingdom"
    :code  "gb"
    :flag  "\uD83C\uDDEC\uD83C\uDDE7"}])

(defn auto-complete-example []
  [autocomplete/autocomplete
   {:disable-portal true
    :id             "test-autocomplete"
    :options        (clj->js top-100-films)

    #_#_#_#_:render-input
          (my-utils/aynon-fn->react-render-fn
           (fn [props]
             [text-field/text-field (merge props {:label "Autocomplete"})]))

        :render-option
      (my-utils/aynon-fn->react-render-fn
       (fn [props option]
         [:li props
          (:flag option) " " (:label option)]))

    :render-input
    (mui-util/react-component
     [props]
     [text-field/text-field (merge props {:label "Autocomplete"})])

    :render-option
    (mui-util/react-component
     [props option]
     [:li props
      (:flag option) " " (:label option)])}])