(ns mui-app.utils
  (:require
   [reagent-mui.util :as mui-util]))

;; --- Playing with macros

;; (defn a [arg1 arg2])

;; (defmacro react-render-fn
;;   [fn-expr]
;;   ;(assert (fn? (eval fn-expr)) "react-render-fn expected a function")
;;   (cond
;;     (and (seq? fn-expr) (= (str (first fn-expr)) "fn"))
;;     (do
;;       (println "Aynon")
;;       (let [[argslist & [body]] (rest fn-expr)]
;;         (println argslist
;;                  body)))
;;     (and (symbol? fn-expr) (fn? (eval fn-expr)))
;;     (do
;;       (println "function: " fn-expr)
;;       (println (first (:arglists (meta (resolve fn-expr))))))
;;     :else
;;     (assert false "react-render-fn expected a function")))

;; (react-render-fn (fn [] (println "hello!")))
;; (react-render-fn a)
;; (react-render-fn (partial a 3))
;; (react-render-fn 3)
;; (react-render-fn [4])

(defmacro aynon-fn->react-render-fn
  {:arglists '([fn-expr])}
  [fn-expr]
  (cond
    (and (seq? fn-expr) (= (str (first fn-expr)) "fn"))
    (let [[argslist & [body]] (rest fn-expr)]
      (mui-util/react-component
       argslist
       body))
    :else
    (assert false "react-render-fn expected an inline function")))