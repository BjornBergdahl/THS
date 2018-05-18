CREATE DEFINER=`root`@`localhost` PROCEDURE `setTicketCategory`(IN ticketNoIn INT, IN categoryIn VARCHAR(45))
BEGIN
	UPDATE ticket SET category = categoryIn WHERE tktNo = ticketNoIn;
END