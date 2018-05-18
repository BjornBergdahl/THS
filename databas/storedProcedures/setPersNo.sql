CREATE DEFINER=`root`@`localhost` PROCEDURE `setPersNo`(IN ticketNoIn INT, IN PersNo INT)
BEGIN
UPDATE ticket SET staffNo = PersNo WHERE tktNo = ticketNoIn;
END