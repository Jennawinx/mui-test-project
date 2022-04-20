(ns mui-app.core
  (:require
   [reagent.core :as r]
   [reagent.dom :as d]
   [reagent-mui.material.button :as button]
   [reagent-mui.material.autocomplete :as autocomplete]
   [reagent-mui.material.text-field :as text-field]
   [reagent-mui.util :as mui-util]))

;; -------------------------
;; Utils

#_(defmacro react-component
  "Helper for creating anonymous React components with Reagent"
  {:arglists '([[props] & body])}
  [bindings & body]
  (assert (vector? bindings) "react-component requires a vector for its bindings")
  (let [argsyms (repeatedly (count bindings) #(gensym "arg"))]
    `(fn [~@argsyms]
       (let [~@(interleave bindings (for [sym argsyms]
                                      (list 'reagent-mui.util/js->clj' sym)))]
         (reagent.core/as-element (do ~@body))))))




;; -------------------------
;; Views


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

(defn home-page []
  [:div
   [:h2 "Welcome to Reagent"]
   [button/button 
    {:on-click #(js/alert "Ouch! Don't poke me!")}
    "Material Button!"]
   [autocomplete/autocomplete
    {:disable-portal true
     :id             "test-autocomplete"
     :options        (clj->js top-100-films)

     :render-input
     (mui-util/react-component
      [props]
      [text-field/text-field (merge props {:label "Autocomplete"})])

     :render-option
     (mui-util/react-component
      [props option]
      [:li props
       (:flag option) " " (:label option)])}]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (d/render [home-page] (.getElementById js/document "app")))

(defn ^:export init! []
  (mount-root))
