;; First define templates for the model classes so we can use them
;; in our planning rules. This doesn't create any model objects --
;; it just tells Jess to examine the classes and set up templates
;; using their properties

(import model.*)
(deftemplate Group		 				 	(declare (from-class Group)))
(deftemplate Holiday						(declare (from-class Holiday)))
(deftemplate Person    						(declare (from-class Person)))
(deftemplate Theme    						(declare (from-class Theme)))
(deftemplate Client extends Person       	(declare (from-class Client)))

;; rules


;; if holiday is of the same theme and still empty, assign a person to it.
;;   ?m<-(problem(name Jess))
;;   (problem(name Jess) (OBJECT ?m))
;;   ?h <(Holiday {numberOfParticipants == 0}) 
-(defrule FillEmptyHolidays    
   (Holiday(numberOfParticipants 0) (OBJECT ?h)) ;; probere rechtstreekse cast naar object ?
   (Client(prefferedHoliday h.holidayTheme) (OBJECT ?c))
     
   ?hfact <-(Holiday {numberOfParticipants == 0}) 
   ?cfact <-(Client {prefferedHoliday == hfact.holidayTheme})
   =>
;   (bind ?f (?h))

   (call ?c name "hoi")
   (printout t ?cfact.name crlf)
   (printout t ?hfact.holidayTheme crlf)
)

;; queries

;; functions

;; facts