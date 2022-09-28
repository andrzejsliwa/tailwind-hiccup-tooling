(ns tailwind-hiccup-tooling.core-test
  (:require
   [clojure.test :as t :refer [deftest is]]
   [tailwind-hiccup-tooling.core :refer [tw]]))

(deftest no-classes-just-props
  (is (= {:href "#"}
         (tw {:href "#"}))))

(deftest single-keyword-with-classes
  (is (= {:class "group block max-w-xs mx-auto rounded-lg p-6 bg-white"}
         (tw :group.block.max-w-xs.mx-auto.rounded-lg.p-6.bg-white))))

(deftest single-keyword-with-classes-and-custom-props
  (is (= {:class "group block max-w-xs mx-auto rounded-lg p-6 bg-white", :href "#"}
         (tw :group.block.max-w-xs.mx-auto.rounded-lg.p-6.bg-white {:href "#"}))))

(deftest multiple-keywords-with-classes-and-custom-props
  (is (= {:class "group block max-w-xs mx-auto rounded-lg p-6 bg-white shadow-lg space-y-3 hover:text-red", :href "#"}
         (tw :group.block.max-w-xs.mx-auto.rounded-lg.p-6.bg-white
             :shadow-lg.space-y-3.hover:text-red {:href "#"}))))

(deftest multiple-keywords-with-classes-and-custom-props-and-explicit-class-prop
  (is (= {:class "group block max-w-xs mx-auto rounded-lg p-6 bg-white shadow-lg space-y-3 hover:text-red", :href "#"}
         (tw :group.block.max-w-xs.mx-auto.rounded-lg.p-6.bg-white
             :shadow-lg.space-y-3 {:href "#" :class "hover:text-red"}))))

(deftest handle-exlamation-mark-as-divide-symbol
  (is (= {:class "group block max-w-xs mx-auto rounded-lg p-6 bg-white shadow-lg space-y-3 w-1/2", :href "#"}
         (tw :group.block.max-w-xs.mx-auto.rounded-lg.p-6.bg-white
             :shadow-lg.space-y-3.w-1!2 {:href "#"}))))

(deftest handle-angle-brackets-as-square-brackets
  (is (= {:class "group block w-1/2 p-[40px]"}
         (tw :group.block.w-1!2.p-<40px>))))

(deftest ignore-nils
  (is (= {:class "group block p-[40px]" :href "#"}
         (tw :group.block (when false :w-1!2) (when true :p-<40px>) {:href "#"}))))
