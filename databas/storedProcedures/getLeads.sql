CREATE DEFINER=`root`@`localhost` PROCEDURE `getLeads`()
BEGIN
	SELECT * FROM processLead;
END