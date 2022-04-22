(ns mui-app.core
  ;; (:require-macros [mui-app.utils :as my-utils :refer [aynon-fn->react-render-fn]])
  (:require
  ;;  [mui-app.utils :as my-utils]
   [reagent.core :as r]
   [reagent.dom :as d]
   [reagent-mui.material.button :as button]
   [reagent-mui.material.autocomplete :as autocomplete]
   [reagent-mui.material.text-field :as text-field]
   [reagent-mui.util :as mui-util]
   [reagent-mui.material.paper :as paper]
   [reagent-mui.material.table :as table]
   [reagent-mui.material.table-cell :as table-cell]
   [reagent-mui.material.table-container :as table-container]
   [reagent-mui.material.table-head :as table-head]
   [reagent-mui.material.table-body :as table-body]
   [reagent-mui.material.table-row :as table-row]))

;; -------------------------
;; Utils Definition

"For passing in a RENDER FUNCTION to components, use the react-component macro"
"For passing in a REACT COMPONENT to components, use the reactify-component macro"

;; -------------------------
;; Utils Definition

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

#_(defn reactify-component [component]
    (let [reactified (forward-ref [props ref]
                                  (let [clj-props (assoc (js->clj' props) :ref ref)]
                                    (r/as-element [component clj-props])))]
      (set! (.-displayName reactified) (fun-name component))
      reactified))

#_(defn [argslist & [child]]
  (mui-util/react-component argslist child))

;; -------------------------
;; Views


;; Autocomplete - example

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

    #_#_#_#_
    :render-input
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



;; Table - example

(defn create-data [name calories fat carbs protein]
  {:name name :calories calories :fat fat :carbs carbs :protein protein})

(def table-data 
  [(create-data "Frozen yoghurt", 159, 6.0, 24, 4.0),
   (create-data "Ice cream sandwich", 237, 9.0, 37, 4.3),
   (create-data "Eclair", 262, 16.0, 24, 6.0),
   (create-data "Cupcake", 305, 3.7, 67, 4.3),
   (create-data "Gingerbread", 356, 16.0, 49, 3.9)])

(defn example-table []
  [table-container/table-container 
   {#_"Works but this is technically a custom component that uses paper component, not the paper component itself"
    ;; :component (mui-util/react-component [props] [paper/paper props])
    #_"Good :)"
    ;; :component (mui-util/reactify-component paper/paper)
    #_"Also works :)"
    :component (mui-util/reactify-component (fn [props] [paper/paper props]))
    }
   [table/table {:sx         {:min-width 650}
                 :aria-label "simple table"}
    [table-head/table-head
     [table-row/table-row
      [table-cell/table-cell "Dessert (100g serving)"]
      [table-cell/table-cell {:align :right} "Calories"]
      [table-cell/table-cell {:align :right} "Fat (g)"]
      [table-cell/table-cell {:align :right} "Carbs (g)"]
      [table-cell/table-cell {:align :right} "Protein (g)"]]]
    [table-body/table-body
     (for [{:keys [name calories fat carbs protein]} table-data]
       ^{:key name}
       [table-row/table-row
        [table-cell/table-cell {:component :th :scope :row} name]
        [table-cell/table-cell {:align :right} calories]
        [table-cell/table-cell {:align :right} fat]
        [table-cell/table-cell {:align :right} carbs]
        [table-cell/table-cell {:align :right} protein]])]]])

(defn home-page []
  [:div
   [:h2 "Welcome to Reagent"]
   [button/button
    {:on-click #(js/alert "Ouch! Don't poke me!")}
    "Material Button!"]
   [auto-complete-example]
   [:h3 "Some table"]
   [example-table]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (d/render [home-page] (.getElementById js/document "app")))

(defn ^:export init! []
  (mount-root))
