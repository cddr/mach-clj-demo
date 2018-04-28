# mach-clj-demo

mach: Define build dependencies
clj (deps.edn): Declare a CLASSPATH and invoke Clojure(script) programs

Oliver Caldwell wrote a nice article describing how to use Clojure's
new "deps.edn" file together with a Makefile to repeatably build,
test and package Clojure programs without the use of conventional
tooling like lein or boot.

But Make is not the only game in town. "Mach is a remake of Make, striving to
keep the good parts". So lets cut to the chase...

```
;; deps.edn
{:deps
 {org.clojure/clojure {:mvn/version "1.9.0"}}

 :aliases
 {:test
  {:extra-paths ["test"]
   :extra-deps {com.cognitect/test-runner
                {:git/url "https://github.com/cognitect-labs/test-runner.git"
                 :sha "5fb4fc46ad0bf2e0ce45eba5b9117a2e89166479"}}
   :main-opts ["-m" "cognitect.test-runner"]}

 :uberjar
  {:extra-deps
   {pack/pack.alpha
    {:git/url "https://github.com/juxt/pack.alpha.git"
     :sha     "6ef0e459eb3a6eaaf0fbc937d46d2854692629b7"}}
   :main-opts ["-m" "mach.pack.alpha.capsule" "-d" "deps.edn" "mach-demo.jar"]}}}


;; Machfile.edn
{cpcache {product ".cpcache/"
          novelty (mach.core/modified-since product "deps.edn")
          update! #$ ["clj" "-e" "'(System/exit 0)'"]}

 test {update! #$ ["clj" "-Atest"]}

 uberjar {product "fzk.jar"
          novelty (mach.core/modified-since product ["src" "deps.edn"])
          update! #$ ["clj" "-Auberjar"]}}
```

addition that allows a
bit more flexibility over the concept of novelty (i.e. when a target should
be rebuilt)

edn file that declares a project's dependencies
you can get a REPL

The need for build tooling is avoided by using `clj` and it's various
options instead just launching `clj` executable

. It made we wonder whether it would
be possible to port the Makefile to a Machfile and use juxt's mach
instead.

```
{cpcache {product ".cpcache/"
          novelty (mach.core/modified-since product "deps.edn")
          update! #$ ["clj" "-e" "'(System/exit 0)'"]}}
```