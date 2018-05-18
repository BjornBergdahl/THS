CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllTickets`()
BEGIN
	SELECT * FROM ticket;
END