CREATE DEFINER=`root`@`localhost` PROCEDURE `getPersonnel`()
BEGIN
	SELECT * FROM supportPersonnel;
END