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

; 1st determine 1 to 1 guidance
; determine client client score
; select highest score
; plan individual
; repeat from 3


(defrule determineOneToOne
    "Determine which indivduals have the maximum 2 per group constraint"
    ?clients <-(Client {needsOneToOneGuidance == FALSE && ( hasDiet == TRUE || hasAllergy == TRUE)})
    =>
    (call ?clients.OBJECT setNeedsOneToOneGuidance TRUE )
    (printout t "I JUST SET ONE TO ONE FOR:" ?clients.name crlf)
)

(defrule determineHardnessScore
    "Determine individuals hardness to place in a group"
    ?clients <- (Client {needsOneToOneGuidance == TRUE && hasHardnessScore == 0})
    =>
   	(bind ?sum (+ ?clients.independenceLevel( + ?clients.mobilityLevel( + ?clients.sensibilityForStress (+ ?clients.carePerDay (+  80))))))	
    (call ?clients.OBJECT setHasHardnessScore ?sum)
    (printout t " HARNESS IS " ?clients.hasHardnessScore crlf)
	(printout t " HARNESS FOR " ?clients.name crlf)
    ;(call ?clients.OBJECT setHasHardnessScore ?score)
    )


; IQ binnen | marge gemiddelde group
; socialskills | marge gemiddelde group
; communicative skills | marge gemiddeld group
; presencelevel in a group | coresponding to tolerance of stress in a group

; independence level | group gemiddeld zo hoog mogelijk
; mobility level | group gemiddeld zo hoog 
; sensibility for stress | afweging op basis van gemiddeld presence level of group

; care per day | indicator van niveau

;; THEME BASED PREFERENCES
; musical taste | muziekweek preferentie
; horseriding skills | paardeweek preferentie
; fishing seriousness | vis preferentie
; sailing iq | sailing preferentie


;; HARD CONSTRAINTS
; needsoneToOneguidance | max 2 per group
; diet | max 2 per group
; allergy | max 2 per group

; man vrouw verhouding ??


;; if holiday is of the same theme and still empty, assign a person to it.
-(defrule FillEmptyHolidays    
   ?hfact <-(Holiday {numberOfParticipants < maxParticipants }) 
   ?cfact <-(Client {isPlanned == FALSE && prefferedHoliday == hfact.holidayTheme })
   =>
   (call ?hfact.OBJECT addParticipant ?cfact.OBJECT)
  ; (printout t ?hfact.numberOfParticipants crlf)
  ; (printout t ?cfact.isPlanned crlf)
)

;; queries

;; functions

;; facts