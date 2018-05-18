CREATE DEFINER=`root`@`localhost` PROCEDURE `getComments`(IN ticketNo INT)
BEGIN
	SELECT * FROM `comment` WHERE tktNoCom = ticketNo;
END