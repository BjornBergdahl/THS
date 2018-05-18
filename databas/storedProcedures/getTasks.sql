CREATE DEFINER=`root`@`localhost` PROCEDURE `getTasks`(IN ticketNo INT)
BEGIN
	SELECT * FROM task WHERE tktNoTsk = ticketNo;
END