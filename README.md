# tailwind-hiccup-tooling

## Getting started

Add tailwind-hiccup-tooling as a dependency, eg. for tools.deps projects

```clojure
;; deps.edn
{:paths [,,,]
 :deps {,,, rgm/tailwind-hiccup-tooling {:mvn/version "0.1.0"} ,,,}
```

Setting up the css build can be a little complex. See the [basic usage
example][basic-example].

## API

Inspired by [`rgm/tailwind-hiccup`][tailwind-hiccup], this library provides a function `tw` that gives ability of
writing multiline hiccup classes (well integrated with `Tailwind CSS`)

```clojure
(require '[tailwind-hiccup-tooling.core :refer [tw]]

(defn MyLink
  [link-text]
  [:a.some-other-class
    (tw :group.block.max-w-xs.mx-auto.rounded-lg.p-6.bg-white
        :shadow-lg.space-y-3.hover:text-red
        {:on-click #(js/alert "surprise!")
         :class "hover:text-red"})
    link-text])
```

[Tailwind CSS]: https://tailwindcss.com