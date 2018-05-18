CREATE DEFINER=`root`@`localhost` PROCEDURE `getUnassignedTickets`()
BEGIN
	/* the column status is a reserved word, must be marked with ` -OK */
	SELECT * FROM ticket WHERE `status` = 'UNASSIGNED';
END
