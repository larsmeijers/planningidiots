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

(deftemplate candidate						(slot client))

;facts
(deffacts candidates
    "In the initial state of the knowledge base, there are no candidates"
    (candidate (client nil))
 )

;; queries
(defquery findUnplannedCandidates
    "Finds all unplanned clients"
    
    (Client(isPlanned FALSE))
)

;; functions
(deffunction selectCandidate()
    "Returns unplanned client with highest hardness-score.
    Should only be called when uplannend candidates are present"
    
    ; find all unplanned candidates
    (bind ?candidates (run-query findUnplannedCandidates))
    ; set first unplanned candidate as having the highest score
    (bind ?firstToken (call ?candidates next))
    (bind ?highest (?firstToken fact 1))
    (bind ?highestVal ?highest.hasHardnessScore)
    ; loop over all unplanned candidates
    (while (?candidates hasNext)
        (bind ?fact ((call ?candidates next) fact 1 ))
        (bind ?factHighestVal ?fact.hasHardnessScore)
        ; update the highest candidate when a higher value than the current highest is found
        (if (> ?factHighestVal ?highestVal) then
            (bind ?highest ?fact)
            (bind ?highestVal ?factHighestVal)
        )
    )
    ; DEBUG: print new highest name
    (bind ?naam ?highest.name)
    (printout t ?naam " has highest value: " ?highestVal crlf)
    (return ?highest)
 )

;; rules set-up
; 1: determine 1 to 1 guidance for every client
; 2: determine client hardness-to-plan score for very client
; 3: select unplanned client with highest score
; 4: plan individual according to rules
; 5: repeat from 3 until every client has been a candidate



;; Vraag Floris: hadden we er bewust voor gekozen om geen link te leggen tussen mobilityLevel en OneToOneGuidance?
; bijv. : hasDiet == TRUE || hasAllergy == TRUE || mobilityLevel == 1
(defrule determineOneToOne
    "Determine which indivduals have the maximum 2 per group constraint"
    (declare (salience 900))
    
    ?clients <-(Client {needsOneToOneGuidance == FALSE && ( hasDiet == TRUE || hasAllergy == TRUE)})  
    =>
    (call ?clients.OBJECT setNeedsOneToOneGuidance TRUE )
    (printout t "I JUST SET ONE TO ONE FOR:" ?clients.name crlf)
)

(defrule determineHardnessScore-1to1
    "Determine individuals hardness to place in a group for one-to-one-guidance clients"
    (declare (salience 800))
    
    ?clients <- (Client {needsOneToOneGuidance == TRUE && hasHardnessScore == 0})  
    =>
   	(bind ?sum (+ ?clients.independenceLevel( + ?clients.mobilityLevel( + ?clients.sensibilityForStress (+ ?clients.carePerDay (+  80))))))	
    (call ?clients.OBJECT setHasHardnessScore ?sum)
    (printout t " HARNESS IS " ?clients.hasHardnessScore crlf)
	(printout t " HARNESS FOR " ?clients.name crlf)
    ;(call ?clients.OBJECT setHasHardnessScore ?score)
    )

(defrule determineHardnessScore
    "Determine individuals hardness to place in a group for none one-to-one-guidance clients"
     (declare (salience 800))
    
    ?clients <- (Client {needsOneToOneGuidance == FALSE && hasHardnessScore == 0})
    =>
   	(bind ?sum (+ ?clients.independenceLevel( + ?clients.mobilityLevel( + ?clients.sensibilityForStress (+ ?clients.carePerDay)))))	
    (call ?clients.OBJECT setHasHardnessScore ?sum)
    (printout t " HARNESS IS " ?clients.hasHardnessScore crlf)
	(printout t " HARNESS FOR " ?clients.name crlf)
    ;(call ?clients.OBJECT setHasHardnessScore ?score)
    )

(defrule selectCandidate
    "Selects highest unplanned client as candidate when no client is selected yet"
    (declare (salience 600))
    (Client {isPlanned == FALSE})
    ?candidateFact <- (candidate {client == nil})
    =>
    (printout t "Ik moet een kandi selecteren" crlf)
    (bind ?candidate (selectCandidate))
    (modify ?candidateFact (client ?candidate))
    )
    
    
(defrule printCandidate
        "prints"
    (candidate {client != nil})
    ?candidateFact <- (candidate (client ?cand))
    =>
    (printout t "Kandidaat: " ?cand.name crlf)
    ;(modify ?candidateFact (client nil))
    )

    
; IQ binnen | marge gemiddelde group
; socialskills | marge gemiddelde group -> 5 = goed 1 = slecht
; communicative skills | marge gemiddeld group -> 5 = goed 1 = slecht
; presencelevel in a group | coresponding to tolerance of stress in a group -> 5 = zeer aanwezig 1 = rustig
; age | marge gemiddeld groep

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


-(defrule 3rule
    "IQ, communicative skills and age"
   (declare (salience -800))

   (candidate {client != nil})
   ?candidateFact <- (candidate (client ?cfact))
   ?hfact <-(Holiday {numberOfParticipants < maxParticipants 
    && holidayTheme == cfact.preferredHoliday && oneToOneCount < 2 
    && (maxAverageIQ >= cfact.iq && minAverageIQ <= cfact.iq)
    && (maxAvgCommunicationlvl >= cfact.communicativeSkill && minAvgCommunicationlvl <= cfact.communicativeSkill)
    && (maxAvgAge >= cfact.age && minAvgAge <= cfact.age)
    && (numberOfParticipants != 11 || expectedGender == cfact.sex)}) 
   =>
   (modify ?candidateFact (client nil))
   (call ?hfact.OBJECT addParticipant ?cfact.OBJECT)
   (printout t ?hfact.numberOfParticipants crlf)
   (printout t "rule3 " ?cfact.isPlanned crlf)
)

-(defrule 4rule
    "IQ and communicative skills."
   (declare (salience -800))

   (candidate {client != nil})
   ?candidateFact <- (candidate (client ?cfact))
   ?hfact <-(Holiday {numberOfParticipants < maxParticipants 
    && holidayTheme == cfact.preferredHoliday && oneToOneCount < 2 
    && (maxAverageIQ >= cfact.iq && minAverageIQ <= cfact.iq)
    && (maxAvgCommunicationlvl >= cfact.communicativeSkill && minAvgCommunicationlvl <= cfact.communicativeSkill)
    && (numberOfParticipants != 11 || expectedGender == cfact.sex)}) 
   =>
   (modify ?candidateFact (client nil))
   (call ?hfact.OBJECT addParticipant ?cfact.OBJECT)
   (printout t ?hfact.numberOfParticipants crlf)
   (printout t "rule4 " ?cfact.isPlanned crlf)
)

-(defrule 5rule
    "The rule that adheres to the mimimum requirement and looks at the candidates IQ when adding a participant."
   (declare (salience -800))

   (candidate {client != nil})
   ?candidateFact <- (candidate (client ?cfact))
   ?hfact <-(Holiday {numberOfParticipants < maxParticipants 
    && holidayTheme == cfact.preferredHoliday && oneToOneCount < 2 
    && (maxAverageIQ >= cfact.iq && minAverageIQ <= cfact.iq)
    && (numberOfParticipants != 11 || expectedGender == cfact.sex)}) 
   =>
   (modify ?candidateFact (client nil))
   (call ?hfact.OBJECT addParticipant ?cfact.OBJECT)
   (printout t ?hfact.numberOfParticipants crlf)
   (printout t "rule5 " ?cfact.isPlanned crlf)
)

-(defrule minimumReqRule
    "The rule that adheres to the mimimum requirement when adding a participant."
   (declare (salience -900))

   (candidate {client != nil})
   ?candidateFact <- (candidate (client ?cfact))
   ?hfact <-(Holiday {numberOfParticipants < maxParticipants 
    && holidayTheme == cfact.preferredHoliday && oneToOneCount < 2 
    && (numberOfParticipants != 11 || expectedGender == cfact.sex)}) 
   =>
   (modify ?candidateFact (client nil))
   (call ?hfact.OBJECT addParticipant ?cfact.OBJECT)
   (printout t ?hfact.numberOfParticipants crlf)
   (printout t "minrule " ?cfact.isPlanned crlf)
)