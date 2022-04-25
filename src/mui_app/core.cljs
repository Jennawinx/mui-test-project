(ns mui-app.core
  (:require
   [reagent.core :as r]
   [reagent.dom :as d]
   
   [mui-app.example-autocomplete :as example-autocomplete]
   [mui-app.example-table :as example-table]

   [reagent-mui.material.button :as button]))

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

;; -------------------------
;; Views

(defn home-page []
  [:div
   [:h2 "Welcome to Reagent"]
   [button/button
    {:on-click #(js/alert "Ouch! Don't poke me!")}
    "Material Button!"]
   [example-autocomplete/auto-complete-example]
   [:h3 "Some table"]
   [example-table/example-table]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (d/render [home-page] (.getElementById js/document "app")))

(defn ^:export init! []
  (mount-root))
