CREATE DEFINER=`root`@`localhost` PROCEDURE `getWorkerTickets`()
BEGIN
	/* the column status is a reserved word, must be marked with ` */
	SELECT * FROM ticket WHERE `status` = 'WORKER';
END