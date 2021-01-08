-- CREATE VIEW mojeRezerwacje1 AS SELECT * FROM 
-- SELECT * FROM uzytkownik u JOIN panel p ON u.uzytkownik_id = p.uzyt_id;

-------------------------------------------------------------
-----------------------------------------
-- select * from get_pokoje(5, CAST('2020-12-31' AS DATE), CAST('2021-01-03' AS DATE));
-- select * from get_pokoje(5,'2020-12-31', '2021-01-03');
INSERT INTO rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(0, 8, NOW(),CAST('2021-01-01' AS DATE),CAST('2021-01-02' AS DATE), 0,5);
INSERT INTO rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(0, 13, NOW(),CAST('2021-01-01' AS DATE),CAST('2021-01-02' AS DATE), 0,5);
-- select * from get_pokoje(5,'2021-01-04', '2021-01-08');

-- SELECT * FROM get_liczba_dni('2021-01-04', '2021-01-08');

-- CREATE OR REPLACE FUNCTION dokonaj_rezerwacji(u_id INT, p_id INT, data_start DATE, data_stop DATE, dzieci INT, dorosli INT, uslugi_opis TEXT, uslugi_cena NUMERIC) RETURNS void AS $$
-- BEGIN
--     INSERT INTO projekt.rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(u_id, p_id, NOW(), data_start, data_stop, dzieci, dorosli);
--     INSERT INTO projekt.dodatkowe_uslugi("nazwa_uslugi", "cena_od_osoby", "rezerwacja_id") VALUES(uslugi_opis, uslugi_cena, latest_rezerwacja_id());
--     COMMIT;
--     RETURN;
-- END;
-- $$ LANGUAGE 'plpgsql';

-- select * from dokonaj_rezerwacji(0, 13, CAST('2021-01-01' AS DATE),CAST('2021-01-02' AS DATE), 0,5, 'TEST - BRAK', 0.0);

------------------------------
-- BEGIN;
--     INSERT INTO projekt.rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(0, 13,NOW(), CAST('2021-01-01' AS DATE),CAST('2021-01-02' AS DATE), 0,5);
--     INSERT INTO projekt.dodatkowe_uslugi("nazwa_uslugi", "cena_od_osoby", "rezerwacja_id") VALUES('TEST - BRAK', 0.0, latest_rezerwacja_id());
-- COMMIT;

-- ------debug
BEGIN;
    INSERT INTO projekt.rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(4, 0,NOW(), CAST('2021-12-31' AS DATE),CAST('2022-01-31' AS DATE), 1,3);
    INSERT INTO projekt.dodatkowe_uslugi("nazwa_uslugi", "cena_od_osoby", "rezerwacja_id") VALUES('TEST - BRAK', 0.0, latest_rezerwacja_id());
COMMIT;

---------------------------------------------
select typ, count(*) from uzytkownicy group by typ;
select typ, count(*) from uzytkownicy group by imie, typ having imie like 'Kamil';
--------------------------------------------------------