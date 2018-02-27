(set-env!

 :source-paths #{"src/cljs" "src/clj"}
 :resource-paths #{"html"}

 :dependencies '[[org.clojure/clojure             "1.8.0"]
                 [org.clojure/clojurescript       "1.9.946"]
                 [adzerk/boot-cljs                "2.1.4"]
                 [adzerk/boot-reload              "0.5.2"]
                 [adzerk/boot-test                "1.2.0"  :scope "test"]
                 [adzerk/boot-cljs-repl           "0.3.3"]
                 [com.cemerick/piggieback         "0.2.1"  :scope "test"]
                 [weasel                          "0.7.0"  :scope "test"]
                 [org.clojure/tools.nrepl         "0.2.12" :scope "test"]
                 [pandeiro/boot-http              "0.8.3"  :scope "test"]
                 [crisptrutski/boot-cljs-test     "0.3.4"  :scope "test"]
                 [compojure                       "1.6.0"]
                 [javax.servlet/javax.servlet-api "4.0.0"]
                 [domina                          "1.0.3"]
                 [reagent                         "0.8.0-alpha2"]
                 [adzerk/bootlaces                "0.1.13"]
                 [cheshire "5.8.0"]])

(task-options!
 pom {:project 'org.clojars.shaolinsarg/acro-buddy
      :version "0.1.0-SNAPSHOT"
      :description "Whats that acronyn"
      :url "http://github.com/shaolinsarg/acro_buddy"
      :scm {:url "http://github.com/shaolinsarg/acro_buddy"}}
 jar {:manifest {"Main-Class" "acro-buddy.core"}})


(require '[adzerk.boot-cljs :refer [cljs]]
         '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
         '[adzerk.boot-reload :refer [reload]]
         '[adzerk.boot-test :refer [test]]
         '[pandeiro.boot-http :refer [serve]])


(deftask dev []
  (comp (serve :handler 'acro-buddy.core/app
               :resource-root "target"
               :reload true
               :port 3000)
        (watch)
        (speak)
        (reload :ws-host "localhost")
        (cljs-repl)
        (cljs :source-map true
              :optimizations :none)
        (target :dir #{"target"})))

(deftask build []
  (cljs :optimizations :advanced))

(deftask install-jar
  []
  (merge-env! :resource-paths #{"src/cljs" "src/clj"})
  (comp
   (pom)
   (jar)
   (install)
   (target :dir #{"target"})))
