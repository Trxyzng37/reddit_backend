CREATE FUNCTION SECURITY.is_id_in_tables (@i INT)
    RETURNS BIT AS
BEGIN
    DECLARE @in_table1 BIT = 0;
    DECLARE @in_table2 BIT = 0;
    DECLARE @exist BIT = 0;

    IF EXISTS (SELECT 1 FROM USER_DATA.users WHERE uid = @i) SET @in_table1 = 1;
    IF EXISTS (SELECT 1 FROM USER_DATA.oath2_user WHERE uid = @i) SET @in_table2 = 1;
    IF (@in_table1 = 1 OR @in_table2 = 1) SET @exist = 1;

RETURN @exist;
END