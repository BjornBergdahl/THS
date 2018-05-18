CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteAllTasks`(IN tktNoTskIn INT)
BEGIN
	DELETE FROM task
    WHERE (tktNoTskIn = tktNoTsk);
END