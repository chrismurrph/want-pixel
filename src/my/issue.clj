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
  (let [red (bit-shift-left (bit-and r 0x0FF) 16)
        green (bit-shift-left (bit-and g 0x0FF) 8)
        blue (bit-and b 0x0FF)]
    (bit-or (bit-or red green blue) (unchecked-int 0xFFFFFFFFFF000000))))

(defn existing-rgb [[r g b]]
  (rgb r g b))

;
; Change this to be either my-rgb-1 or my-rgb-2 or existing-rgb
;
(def step-three (partial my-rgb-2))

(defn hexify-colour [[r g b]]
  (format "0x%x 0x%x 0x%x" r g b))

(defn -main
  "Demonstrates that I do not know how to use this library to read, alter and write back images"
  [& args]
  (println "Image width: " w)
  (println "Image height: " h)
  ;
  ; STEP 1
  ;
  (let [first-pixel (get-pixel ant 0 0)
        last-pixel (get-pixel ant (dec w) (dec h))
        ;
        ; STEP 2
        ;
        first-colour (components-rgb first-pixel)
        last-colour (components-rgb last-pixel)
        ;
        ; STEP 3
        ;
        first-rgb (step-three first-colour)
        last-rgb (step-three last-colour)
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
