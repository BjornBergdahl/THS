CREATE DEFINER=`root`@`localhost` PROCEDURE `addTask`(IN tktNoTskIn INT, IN nameIn VARCHAR(45), IN timeBudgetMinutesIn INT, IN timeSpentMinutesIn INT)
BEGIN
	INSERT INTO task(tktNoTsk, `name`, timeBudgetMinutes, timeSpentMinutes)
    VALUES (tktNoTskIn, nameIn, timeBudgetMinutesIn, timeSpentMinutesIn);
END