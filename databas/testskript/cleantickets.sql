SELECT * FROM mydb.ticket;
UPDATE mydb.ticket SET `status` = 'ASSIGNED', staffNo = NULL WHERE staffNo IS NOT NULL;
SELECT * FROM mydb.ticket;