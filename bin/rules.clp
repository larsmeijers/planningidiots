;; First define templates for the model classes so we can use them
;; in our planning rules. This doesn't create any model objects --
;; it just tells Jess to examine the classes and set up templates
;; using their properties

(import model.*)
(deftemplate Client       	(declare (from-class Client)))
(deftemplate Group		  	(declare (from-class Group)))
(deftemplate Holiday		(declare (from-class Holiday)))
(deftemplate Person    		(declare (from-class Person)))
(deftemplate Theme    		(declare (from-class Theme)))

;; Rules
