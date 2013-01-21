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
-(defrule FillEmptyHolidays    
   ?hfact <-(Holiday {numberOfParticipants < maxParticipants }) 
   ?cfact <-(Client {isPlanned == FALSE && prefferedHoliday == hfact.holidayTheme })
   =>
   (call ?hfact.OBJECT addParticipant ?cfact.OBJECT)
   (printout t ?hfact.numberOfParticipants crlf)
   (printout t ?cfact.isPlanned crlf)
)

;; queries

;; functions

;; facts