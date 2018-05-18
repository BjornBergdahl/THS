CREATE DEFINER=`root`@`localhost` PROCEDURE `getAttestedTickets`()
BEGIN
	/* the column status is a reserved word, must be marked with ` */
	SELECT * FROM ticket WHERE `status` = 'ATTESTED';
END