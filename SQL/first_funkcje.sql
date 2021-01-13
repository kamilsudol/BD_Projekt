--------------------------
CREATE OR REPLACE FUNCTION get_liczba_dni(data_start DATE, data_stop DATE) RETURNS int AS $$
DECLARE
    x INT;
BEGIN
    x := data_stop - data_start + 1;
    RETURN x;
END;
$$LANGUAGE 'plpgsql';
----------------------------
CREATE OR REPLACE FUNCTION latest_rezerwacja_id() RETURNS int AS $$
DECLARE 
    x INT;
    rec RECORD;
BEGIN
    SELECT MAX(rezerwacja_id) AS latest INTO rec FROM projekt.rezerwacje;
    x := rec.latest;
    RETURN x;
END;
$$LANGUAGE 'plpgsql';
-------------------------------
CREATE OR REPLACE FUNCTION latest_uzytkownik_id() RETURNS int AS $$
DECLARE 
    x INT;
    rec RECORD;
BEGIN
    SELECT MAX(uzytkownik_id) AS latest INTO rec FROM projekt.uzytkownik;
    x := rec.latest;
    RETURN x;
END;
$$LANGUAGE 'plpgsql';