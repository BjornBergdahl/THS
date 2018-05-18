CREATE DEFINER=`root`@`localhost` PROCEDURE `addComment`(IN ticketNoIn INT, IN textIn VARCHAR(180))
BEGIN
	INSERT INTO `comment`(tktNocom, `text`) VALUES (ticketNoIn, textIn);
END