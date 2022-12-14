[![Clojars Project](https://img.shields.io/clojars/v/io.github.andrzejsliwa/tailwind-hiccup-tooling.svg)](https://clojars.org/io.github.andrzejsliwa/tailwind-hiccup-tooling)

[![Release Version](https://github.com/andrzejsliwa/tailwind-hiccup-tooling/actions/workflows/test-and-release.yml/badge.svg)](https://github.com/andrzejsliwa/tailwind-hiccup-tooling/actions/workflows/test-and-release.yml)
[![Develop & Snapshot](https://github.com/andrzejsliwa/tailwind-hiccup-tooling/actions/workflows/test-and-snapshot.yml/badge.svg)](https://github.com/andrzejsliwa/tailwind-hiccup-tooling/actions/workflows/test-and-snapshot.yml)
# tailwind-hiccup-tooling

## Getting started

Add tailwind-hiccup-tooling as a dependency, eg. for tools.deps projects

```clojure
;; deps.edn
{:paths [,,,]
 :deps {,,, io.github.andrzejsliwa/tailwind-hiccup-tooling {:mvn/version "0.2.31"} ,,,}
```

Setting up the css build can be a little complex. See the [basic usage
example][basic-example].

## API

Inspired by [tailwind-hiccup](https://github.com/rgm/tailwind-hiccup), this library provides a function `tw` that gives ability of
writing multiline hiccup classes (well integrated with `Tailwind CSS`)

```clojure
(require '[tailwind-hiccup-tooling.core :refer [tw]])

(defn MyLink
  [link-text]
  [:a.some-other-class
    (tw :group.block.max-w-xs.mx-auto.rounded-lg.p-6.bg-white
        :shadow-lg.space-y-3.hover:text-red
        {:on-click #(js/alert "surprise!")
         :class "hover:text-red"})
    link-text])
```

Additionally you can use symbol `!` as tailwind `/`:

```clojure
[:a.some-other-class (tw :bg-white.w-1!2)]
;; -> [:a.some-other-class {:class "bg-white w-1/2"}]
```

And `<>` as `[]`:

```clojure
[:a.some-other-class (tw :bg-white.w-<100px>)]
;; -> [:a.some-other-class {:class "bg-white w-[100px]"}]
```

You can use it also conditionally:

```clojure
[:a.some-class (tht/tw :group.block
                       (when false :w-1!2)
                       (when true :p-<40px>)
                       {:href "#"})]
;; -> [:a.some-class {:class "group block p-[40px]", :href "#"}]
```

[Tailwind CSS]: https://tailwindcss.com
