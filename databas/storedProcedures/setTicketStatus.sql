CREATE DEFINER=`root`@`localhost` PROCEDURE `setTicketStatus`(IN ticketNoIn INT, IN statusIn VARCHAR(45))
BEGIN
UPDATE ticket SET `status` = statusIn WHERE tktNo = ticketNoIn;
END