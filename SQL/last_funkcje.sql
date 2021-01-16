CREATE OR REPLACE FUNCTION numeric_check(TEXT) RETURNS BOOLEAN AS $$
DECLARE 
    x NUMERIC;
BEGIN
    x = $1::NUMERIC;
    RETURN TRUE;
    EXCEPTION WHEN others THEN
    RETURN FALSE;
END;
$$LANGUAGE 'plpgsql';

-------------------------------------------------------------
CREATE OR REPLACE FUNCTION zalogujCheck(log TEXT, pass TEXT) RETURNS INT AS $$
DECLARE 
    id INT;
    rec RECORD;
BEGIN
    SELECT uzyt_id, haslo INTO rec FROM projekt.panel WHERE login = log;
    IF rec.haslo = pass THEN
        id := rec.uzyt_id;
    ELSE
        id := -1;
    END IF;
    RETURN id;
END;
$$LANGUAGE 'plpgsql';
-------------------------------------------------------------
CREATE OR REPLACE FUNCTION get_pokoje(liczba_osob INT, data_start DATE, data_stop DATE) RETURNS TABLE(id_pokoju INTEGER) AS $$
DECLARE 
    kursor_pokoj CURSOR FOR SELECT * FROM projekt.pokoj WHERE liczba_osob <= liczba_miejsc;
    rec RECORD;
    query_from_rezerwacje TEXT;
    rec_check RECORD;
BEGIN
    OPEN kursor_pokoj;
    LOOP
          FETCH kursor_pokoj INTO rec;
          EXIT WHEN NOT FOUND;

          query_from_rezerwacje := 'SELECT pokoj_id FROM projekt.rezerwacje WHERE (pokoj_id = $1 AND (($2>od_kiedy AND $2<do_kiedy) OR ($3>od_kiedy AND $3<do_kiedy) OR (od_kiedy>$2 AND do_kiedy<$3))) LIMIT 1';
          EXECUTE query_from_rezerwacje INTO rec_check USING rec.pokoj_id, data_start, data_stop;
          IF rec_check IS NULL THEN
              id_pokoju := rec.pokoj_id;
              RETURN NEXT;
          END IF;
    END LOOP;
    CLOSE kursor_pokoj;
END;
$$LANGUAGE 'plpgsql';

-------------------------
CREATE OR REPLACE FUNCTION oplataZaplac(id int) RETURNS void AS $$
BEGIN
    UPDATE projekt.oplata SET status_czy_oplacone = 'Oplacone' WHERE oplata_id = id;
END;
$$LANGUAGE 'plpgsql';
-------------------------------------------
CREATE OR REPLACE FUNCTION oplataRezygnuj(id int) RETURNS void AS $$
DECLARE
    rec RECORD;
BEGIN
    SELECT * INTO rec FROM projekt.rezerwacje WHERE rezerwacja_id = (SELECT rezerwacja_id FROM projekt.oplata WHERE oplata_id = id);
    INSERT INTO projekt.rezygnacja_z_rezerwacji_info("rezerwacja_id", "uzytkownik_id") VALUES(rec.rezerwacja_id, rec.uzytkownik_id);
END;
$$LANGUAGE 'plpgsql';
------------------------------------------- 
CREATE OR REPLACE FUNCTION ZbanowanyCheck(id int) RETURNS int AS $$
DECLARE
    rec RECORD;
    flag INT;
BEGIN
    SELECT COUNT(*) AS c INTO rec FROM projekt.czarna_lista WHERE uzytkownik_id = id;
    flag := rec.c;
    RETURN flag;
END;
$$LANGUAGE 'plpgsql';
----------------------------------------
CREATE OR REPLACE FUNCTION StartUpdate() RETURNS void AS $$
DECLARE
    rec RECORD;
    kursor_check CURSOR FOR SELECT * FROM projekt.OplataRezerwacje;
BEGIN
    OPEN kursor_check;
    LOOP
        FETCH kursor_check INTO rec;
        EXIT WHEN NOT FOUND;

        IF rec.od_kiedy - CAST(NOW() AS DATE) <= 0 THEN
            IF rec.status_czy_oplacone LIKE 'Oplacone' THEN
                UPDATE projekt.zakwaterowani_goscie_info SET status_czy_zakwaterowany='Zakwaterowany' WHERE rezerwacja_id = rec.rezerwacja_id;
            ELSIF rec.status_czy_oplacone LIKE 'Nieoplacone' THEN
                INSERT INTO projekt.czarna_lista("uzytkownik_id","powod") VALUES(rec.uzytkownik_id, 'Nieoplacenie rezerwacji w terminie.');
                DELETE FROM projekt.rezerwacje WHERE rezerwacja_id = rec.rezerwacja_id;
            END IF;
        ELSIF rec.do_kiedy - CAST(NOW() AS DATE) <= 0 AND rec.status_czy_oplacone LIKE 'Oplacone' THEN
            UPDATE projekt.zakwaterowani_goscie_info SET status_czy_zakwaterowany='Wykwaterowany' WHERE rezerwacja_id = rec.rezerwacja_id;
        END IF;
    END LOOP;
    CLOSE kursor_check;
    RETURN;
END;
$$LANGUAGE 'plpgsql';
-------------------------------------
CREATE OR REPLACE FUNCTION Autoryzacja(id int) RETURNS int AS $$
DECLARE
    rec RECORD;
    flag int;
BEGIN
    SELECT typ INTO rec FROM projekt.uzytkownik WHERE uzytkownik_id = id;
    IF rec.typ LIKE 'HeadAdmin' OR rec.typ LIKE 'Pracownik' THEN
        flag := 1;
    ELSE
        flag := 0;
    END IF;
    RETURN flag;
END;
$$LANGUAGE 'plpgsql';
