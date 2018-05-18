CREATE DEFINER=`root`@`localhost` PROCEDURE `setProcessLeadNo`(IN ticketNoIn INT, IN PLNo INT)
BEGIN
UPDATE ticket SET processLeadNo = PLNo WHERE tktNo = ticketNoIn;
END