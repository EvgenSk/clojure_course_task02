(ns clojure-course-task02.core
  (:gen-class))

(defn raw-dirs-walker [func root]
  (do
    (func root)
    (if (.isDirectory root)
      (->> (file-seq root)
           (rest)
           (map #(raw-dirs-walker func %))))))

(defn dirs-walker [func root]
  (raw-dirs-walker #(func (.getName %)) 
                   (clojure.java.io/file root)))

(defn find-files [file-name path]
  "TODO: Implement searching for a file using his name as a regexp."
  nil)

(defn usage []
  (println "Usage: $ run.sh file_name path"))

(defn -main [file-name path]
  (if (or (nil? file-name)
          (nil? path))
    (usage)
    (do
      (println "Searching for " file-name " in " path "...")
      (dorun (map println (find-files file-name path))))))
