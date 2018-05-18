CREATE DEFINER=`root`@`localhost` PROCEDURE `getDoneTickets`()
BEGIN
	/* the column status is a reserved word, must be marked with ` */
	SELECT * FROM ticket WHERE `status` = 'DONE';
END