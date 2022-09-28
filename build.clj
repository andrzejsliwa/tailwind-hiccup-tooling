(ns build
  (:require [clojure.tools.build.api :as b]
            [org.corfield.build :as bb]))

(def lib 'io.github.andrzejsliwa/tailwind-hiccup-tooling)

(defn- the-version [patch] (format "0.2.%s" patch))

(def version (the-version (b/git-count-revs nil)))

(def snapshot (the-version "999-SNAPSHOT"))

(defn test "Run all the tests." [opts]
  (reduce (fn [opts alias]
            (bb/run-tests (assoc opts :aliases [alias])))
          opts
          [:1.10])
  opts)

(defn ci "Run the CI pipeline of tests (and build the JAR)." [opts]
  (-> opts
      (assoc :lib lib :version (if (:snapshot opts) snapshot version) :src-pom "template/pom.xml")
      (bb/run-tests)
      (bb/clean)
      (bb/jar)))

(defn install "Install the JAR locally." [opts]
  (-> opts
      (assoc :lib lib :version (if (:snapshot opts) snapshot version) :src-pom "template/pom.xml")
      (bb/install)))

(defn deploy "Deploy the JAR to Clojars." [opts]
  (-> opts
      (assoc :lib lib :version (if (:snapshot opts) snapshot version) :src-pom "template/pom.xml")
      (bb/deploy)))

(defn print-tag "Print actual tag for current version" [_]
  (println (format "v%s" version)))