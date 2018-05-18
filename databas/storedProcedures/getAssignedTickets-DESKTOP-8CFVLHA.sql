CREATE DEFINER=`root`@`localhost` PROCEDURE `getAssignedTickets`()
BEGIN
	/* the column status is a reserved word, must be marked with ` */
	SELECT * FROM ticket WHERE `status` != 'UNASSIGNED';
END