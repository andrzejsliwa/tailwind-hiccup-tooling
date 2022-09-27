(ns tailwind-hiccup-tooling.core-test
  (:require
   [clojure.test :as t :refer [deftest is]]
   [tailwind-hiccup-tooling.core :as tht]))

(deftest no-classes-just-props
  (is (= {:href "#"}
         (tht/tw {:href "#"}))))

(deftest single-keyword-with-classes
  (is (= {:class "group block max-w-xs mx-auto rounded-lg p-6 bg-white"}
         (tht/tw :group.block.max-w-xs.mx-auto.rounded-lg.p-6.bg-white))))

(deftest single-keyword-with-classes-and-custom-props
  (is (= {:class "group block max-w-xs mx-auto rounded-lg p-6 bg-white", :href "#"}
         (tht/tw :group.block.max-w-xs.mx-auto.rounded-lg.p-6.bg-white {:href "#"}))))

(deftest multiple-keywords-with-classes-and-custom-props
  (is (= {:class "group block max-w-xs mx-auto rounded-lg p-6 bg-white shadow-lg space-y-3 hover:text-red", :href "#"}
         (tht/tw :group.block.max-w-xs.mx-auto.rounded-lg.p-6.bg-white
                 :shadow-lg.space-y-3.hover:text-red {:href "#"}))))

(deftest multiple-keywords-with-classes-and-custom-props-and-explicit-class-prop
  (is (= {:class "group block max-w-xs mx-auto rounded-lg p-6 bg-white shadow-lg space-y-3 hover:text-red", :href "#"}
         (tht/tw :group.block.max-w-xs.mx-auto.rounded-lg.p-6.bg-white
                 :shadow-lg.space-y-3 {:href "#" :class "hover:text-red"}))))

(deftest handle-exlamation-mark-as-divide-symbol
  (is (= {:class "group block max-w-xs mx-auto rounded-lg p-6 bg-white shadow-lg space-y-3 w-1/2", :href "#"}
         (tht/tw :group.block.max-w-xs.mx-auto.rounded-lg.p-6.bg-white
                 :shadow-lg.space-y-3.w-1!2 {:href "#"}))))

(deftest handle-angle-brackets-as-square-brackets
  (is (= {:class "group block w-1/2 p-[40px]"}
         (tht/tw :group.block.w-1!2.p-<40px>))))
