(ns clojure-course-task02.core
  (:gen-class))

(defn ^{:doc "Recursively walks through folders from root and applies func to each item"}
  raw-dirs-walker [func root]
  (do
    (func root)
    (if (.isDirectory root)
      (->> (file-seq root)
           (rest)
           (map #(raw-dirs-walker func %))))))

(defn ^{:doc "Recursively walks through folders from root and applies func to each item-name"} 
  dirs-walker [func root]
  (raw-dirs-walker #(func (.getName %)) 
                   (clojure.java.io/file root)))

(defn ^{:doc "Checks if regexp matches with name. If yes, returns name, otherwise returns nil"}  
  check-re [regexp name]
  (if (-> (re-pattern regexp) (re-find name))
    name))

(defn find-files [file-name path]
  (let [result (atom [])]
    (do
      (doall
       (dirs-walker #(swap! result conj (check-re file-name %)) 
                    path))
      (remove empty? @result))))

(defn usage []
  (println "Usage: $ run.sh file_name path"))

(defn -main [file-name path]
  (if (or (nil? file-name)
          (nil? path))
    (usage)
    (do
      (println "Searching for " file-name " in " path "...")
      (dorun (map println (find-files file-name path))))))
