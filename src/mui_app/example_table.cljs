(ns mui-app.example-table
  (:require
  ;;  [mui-app.utils :as my-utils]
   [reagent.core :as r]
   [reagent.dom :as d]
   [reagent-mui.util :as mui-util]
   [reagent-mui.material.paper :as paper]
   [reagent-mui.material.table :as table]
   [reagent-mui.material.table-cell :as table-cell]
   [reagent-mui.material.table-container :as table-container]
   [reagent-mui.material.table-head :as table-head]
   [reagent-mui.material.table-body :as table-body]
   [reagent-mui.material.table-row :as table-row]))

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
    :component (mui-util/reactify-component (fn [props] [paper/paper props]))}
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
