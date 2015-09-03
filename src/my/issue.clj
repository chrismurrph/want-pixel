(ns my.issue
  (:use [mikera.image.core])
  (:use [mikera.image.colours]))

(def ant (load-image "Ant1.png"))
(def h (height ant))
(def w (width ant))

;rgb = 0xFFFF * r + 0xFF * g + b
(defn my-rgb-1 [[r g b]]
  (+ (* 0xFFFF r) (* 0xFF g) b))

;int rgb = ((r&0x0ff)<<16)|((g&0x0ff)<<8)|(b&0x0ff);
(defn my-rgb-2 [[r g b]]
  (let [red (bit-shift-left 16 (bit-and r 0x0FF))
        green (bit-shift-left 8 (bit-and g 0x0FF))
        blue (bit-and b 0x0FF)]
    (bit-or red green blue)))

(defn existing-rgb [[r g b]]
  (rgb r g b))

(defn hexify-colour [[r g b]]
  (format "0x%x 0x%x 0x%x" r g b))

(defn -main
  "Demonstrates that I do not know how to use this library to read, alter and write back images"
  [& args]
  (println "Image width: " w)
  (println "Image height: " h)
  (let [first-pixel (get-pixel ant 0 0)
        last-pixel (get-pixel ant (dec w) (dec h))
        first-colour (components-rgb first-pixel)
        last-colour (components-rgb last-pixel)
        ;
        ; first-colour and last-colour are human readable
        ; We now try to reverse the process as if we were going to write the pixels back to a different image
        ;
        first-rgb (existing-rgb first-colour)
        last-rgb (existing-rgb last-colour)
        ]
    (println "First pixel:" first-pixel (format "( 0x%x"first-pixel)")")
    (println "Last pixel:" last-pixel (format "( 0x%x"last-pixel)")")
    (println "First as colour:" first-colour "(" (hexify-colour first-colour) ")")
    (println "Last as colour:" last-colour "(" (hexify-colour last-colour) ")")
    ;
    ; The output of both of these is exactly the same, showing that `apply rgb` is not the
    ; reverse of `components-rgb`. What is the reverse of `components-rgb`?
    ;
    (println "First, that I want to be the same as first pixel:" first-rgb (format "( 0x%x"first-rgb)")")
    (println "Last, that I want to be the same as last pixel:" last-rgb (format "( 0x%x"last-rgb)")")
    )
  )
