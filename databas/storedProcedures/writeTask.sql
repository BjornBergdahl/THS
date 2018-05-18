CREATE DEFINER=`root`@`localhost` PROCEDURE `writeTask`(IN tskNoIn INT, 
IN nameIn VARCHAR(45),  
IN timeBudgetMinutesIn INT, IN timeSpentMinutesIn INT)
BEGIN
	UPDATE task   
	SET `name`= nameIn,  
    timeBudgetMinutes=timeBudgetMinutesIn, 
    timeSpentMinutes=timeSpentMinutesIn
	WHERE (tskNo = tskNoIn);

/*	UPDATE task   
	SET `name`= nameIn, `text` = textIn
	WHERE (tskNo = tskNoIn);
    -- for some reason it wont take all four fields as one?
    UPDATE task
    SET timeBudgetMinutes=timeBudgetMinutesIn, 
    timeSpentMinutes=timeSpentMinutesIn
    WHERE (tskNo = tskNoIn);*/
END