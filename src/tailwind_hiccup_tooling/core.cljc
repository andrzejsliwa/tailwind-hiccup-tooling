(ns tailwind-hiccup-tooling.core
  (:require [clojure.string :as s]))

(defn- replace-several [content & replacements]
  (let [replacement-list (partition 2 replacements)]
    (reduce #(apply s/replace %1 %2) content replacement-list)))
(defn- remove-nils [kw-classes] (remove nil? kw-classes))
(defn- has-props [kw-classes] (map? (last kw-classes)))
(defn- extract-props [has-props kw-classes] (if has-props (last kw-classes) {}))
(defn- keyword-to-str [k] (str (name k)))
(defn- generate-classes [has-props kw-classes]
  (->> (if has-props
         (butlast kw-classes)
         kw-classes)
       (map keyword-to-str)
       (s/join ".")))
(defn- replace-specials [str]
  (replace-several str #"\." " " #"!" "/" #"<" "[" #">" "]"))

(defn tw
  "Merge tailwind class collections in to props with keyword versions. Allows to split hiccup
   classes in multiple lines/keywords."
  [& kc]
  (let [keyword-classes (remove-nils kc)
        has-props       (has-props keyword-classes)
        props           (extract-props has-props keyword-classes)
        class_prop      (:class props)
        joined-classes  (generate-classes has-props keyword-classes)
        tw-classes      (replace-specials joined-classes)]
    (merge-with merge (if (s/blank? tw-classes)
                        {}
                        {:class (if class_prop
                                  (str tw-classes " " class_prop)
                                  tw-classes)})
                (dissoc props :class))))

(comment
  ;; simple usage as multiline keyword tailwind / css classes
  (tw :group.block.max-w-xs.mx-auto.rounded-lg.p-6.bg-white
      :shadow-lg.space-y-3.hover:text-red
      {:on-click #(println "click! ")})
  ;; => {:class
  ;;     "group block max-w-xs mx-auto rounded-lg p-6 bg-white shadow-lg space-y-3 hover:text-red",
  ;;     :on-click
  ;;     #function[tailwind-hiccup-tooling.core/eval8043/fn--8044]}


  ;; simple example of using < and > as squar brackets
  [:a.some-other-class (tw :bg-white.w-<100px>)]
  ;; => [:a.some-other-class
  ;;     {:class "bg-white w-[100px]"}]


  ;; somple example of using conditional logic
  [:a.some-class (tw :group.block
                     (when false :w-1!2)
                     (when true :p-<40px>)
                     {:href "#"})]
  ;; => [:a.some-class
  ;;     {:class "group block p-[40px]",
  ;;      :href "#"}]

  ;; end of comment 
  nil)
;; => nil




