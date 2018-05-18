CREATE DEFINER=`root`@`localhost` PROCEDURE `addProcessLead`(IN pLeadNo INT, IN firstname VARCHAR(45), IN lastname VARCHAR(45))
BEGIN
INSERT INTO processlead(processLeadNo, fname, lname)
VALUES (pLeadNo, firstname, lastname);
END