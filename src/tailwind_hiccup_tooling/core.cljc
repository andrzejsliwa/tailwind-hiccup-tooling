(ns tailwind-hiccup-tooling.core
  (:require [clojure.string :as s]))
  
(defn tw 
  "Merge tailwind class collections in to props with keyword versions. Allows to split hiccup
   classes in multiple lines/keywords.
   
   Examples:
   (tw :group.block.max-w-xs.mx-auto.rounded-lg.p-6.bg-white
       :shadow-lg.space-y-3.hover:text-red
       {:on-click #(println \"click!\")}) =>
   {:class \"group block max-w-xs mx-auto rounded-lg p-6 bg-white shadow-lg space-y-3 hover:text-red\" 
    :on-click #object[Function]}"
  [& keyword-classes]
  (let [has-props  (map? (last keyword-classes))
        props      (if has-props (last keyword-classes) {})
        class_prop (:class props)
        joined-classes (->> (if has-props
                              (butlast keyword-classes)
                              keyword-classes)
                            (map #(str (name %)))
                            (s/join "."))
        
        tw-classes (s/replace (s/replace joined-classes #"\." " ") #"!" "/")]
    (merge-with merge (if (s/blank? tw-classes) 
                        {} 
                        {:class (if class_prop
                                 (str tw-classes " " class_prop)
                                 tw-classes)}) 
                (dissoc props :class))))  
