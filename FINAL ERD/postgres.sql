CREATE TABLE "panel" (
  "uzyt_id" int PRIMARY KEY,
  "login" varchar,
  "haslo" varchar
);

CREATE TABLE "uzytkownik" (
  "uzytkownik_id" serial PRIMARY KEY,
  "imie" varchar,
  "nazwisko" varchar,
  "e_mail" text,
  "numer" text,
  "typ" text
);

CREATE TABLE "rezerwacje" (
  "rezerwacja_id" serial PRIMARY KEY,
  "uzytkownik_id" int,
  "pokoj_id" int,
  "data_rezerwacji" date,
  "od_kiedy" date,
  "do_kiedy" date,
  "liczba_dzieci" int,
  "liczba_doroslych" int
);

CREATE TABLE "pokoj" (
  "pokoj_id" int PRIMARY KEY,
  "numer_pokoju" int,
  "pietro" int,
  "kategoria_id" int,
  "liczba_miejsc" int
);

CREATE TABLE "kategoria" (
  "kategoria_id" int PRIMARY KEY,
  "nazwa_kategorii" text,
  "cena_od_osoby" numeric
);

CREATE TABLE "oplata" (
  "oplata_id" serial PRIMARY KEY,
  "rezerwacja_id" int,
  "status_czy_oplacone" text,
  "kwota" numeric
);

CREATE TABLE "dodatkowe_uslugi" (
  "dodatkowe_uslugi_id" serial PRIMARY KEY,
  "nazwa_uslugi" text,
  "cena_od_osoby" numeric,
  "rezerwacja_id" int
);

CREATE TABLE "zakwaterowani_goscie_info" (
  "info_id" serial PRIMARY KEY,
  "rezerwacja_id" int,
  "status_czy_zakwaterowany" text
);

CREATE TABLE "czarna_lista" (
  "info_id" serial PRIMARY KEY,
  "uzytkownik_id" int,
  "powod" text
);

CREATE TABLE "rezygnacja_z_rezerwacji_info" (
  "info_id" serial PRIMARY KEY,
  "rezerwacja_id" int,
  "uzytkownik_id" int
);

ALTER TABLE "rezerwacje" ADD FOREIGN KEY ("uzytkownik_id") REFERENCES "uzytkownik" ("uzytkownik_id");

ALTER TABLE "oplata" ADD FOREIGN KEY ("rezerwacja_id") REFERENCES "rezerwacje" ("rezerwacja_id");

ALTER TABLE "dodatkowe_uslugi" ADD FOREIGN KEY ("rezerwacja_id") REFERENCES "rezerwacje" ("rezerwacja_id");

ALTER TABLE "rezerwacje" ADD FOREIGN KEY ("pokoj_id") REFERENCES "pokoj" ("pokoj_id");

ALTER TABLE "pokoj" ADD FOREIGN KEY ("kategoria_id") REFERENCES "kategoria" ("kategoria_id");

ALTER TABLE "zakwaterowani_goscie_info" ADD FOREIGN KEY ("rezerwacja_id") REFERENCES "rezerwacje" ("rezerwacja_id");

ALTER TABLE "panel" ADD FOREIGN KEY ("uzyt_id") REFERENCES "uzytkownik" ("uzytkownik_id");

ALTER TABLE "czarna_lista" ADD FOREIGN KEY ("uzytkownik_id") REFERENCES "uzytkownik" ("uzytkownik_id");

ALTER TABLE "rezygnacja_z_rezerwacji_info" ADD FOREIGN KEY ("rezerwacja_id") REFERENCES "rezerwacje" ("rezerwacja_id");


INSERT INTO "uzytkownik"("imie","nazwisko","e_mail","numer","typ") VALUES (0, 'ADMIN', 'ADMIN', 'TEST@TEST.COM', 999999999, 'HeadAdmin');
INSERT INTO "panel" VALUES (latest_uzytkownik_id(), 'admin', 'admin');
CREATE VIEW uzytkownicy AS SELECT * FROM uzytkownik u JOIN panel p ON u.uzytkownik_id = p.uzyt_id;
CREATE VIEW pokojeView AS SELECT pokoj_id, numer_pokoju, pietro, liczba_miejsc, p.kategoria_id, nazwa_kategorii, cena_od_osoby FROM pokoj p JOIN kategoria k ON p.kategoria_id = k.kategoria_id;
-- CREATE VIEW mojeRezerwacje1 AS SELECT * FROM 
-- SELECT * FROM uzytkownik u JOIN panel p ON u.uzytkownik_id = p.uzyt_id;

-------------------------------------------------------------

CREATE OR REPLACE FUNCTION numeric_check(TEXT) RETURNS BOOLEAN AS $$
DECLARE x NUMERIC;
BEGIN
    x = $1::NUMERIC;
    RETURN TRUE;
    EXCEPTION WHEN others THEN
    RETURN FALSE;
END;
$$LANGUAGE 'plpgsql';

-------------------------------------------------------------

CREATE OR REPLACE FUNCTION register_validator() RETURNS TRIGGER AS
$$
DECLARE
    flag INTEGER;
    rec RECORD;
BEGIN
    flag := 0;
    SELECT * INTO rec FROM projekt.uzytkownik u WHERE u.e_mail = NEW.e_mail;

    IF NEW.imie LIKE '' THEN
        RAISE EXCEPTION '||Prosze wypelnic pole imie!||';
        RETURN NULL;
    ELSE
        flag := flag + 1;
    END IF;

    IF NEW.nazwisko LIKE '' THEN
        RAISE EXCEPTION '||Prosze wypelnic pole nazwisko!||';
        RETURN NULL;
    ELSE
        flag := flag + 1;
    END IF;

    IF NEW.e_mail LIKE '' THEN
        RAISE EXCEPTION '||Prosze wypelnic pole e-mail!||';
        RETURN NULL;
    ELSE
        flag := flag + 1;
    END IF;

    IF NEW.e_mail LIKE '%@%' THEN
        flag := flag + 1;
    ELSE
        RAISE EXCEPTION '||Prosze podac poprawny adres e-mail!||';
        RETURN NULL;
    END IF;

    IF rec IS NULL THEN
        flag := flag + 1;
    ELSE
        RAISE EXCEPTION '||Uzytkownik o podanym e-mailu juz istnieje!||';
        RETURN NULL;
    END IF;

    IF NEW.numer LIKE '' THEN
        RAISE EXCEPTION '||Prosze wypelnic pole numer telefonu!||';
        RETURN NULL;
    ELSE
        flag := flag + 1;
    END IF;

    IF projekt.numeric_check(NEW.numer) THEN
        flag := flag + 1;
    ELSE
        RAISE EXCEPTION '||Prosze podac poprawny numer telefonu!||';
        RETURN NULL;
    END IF;
    
    RETURN NEW;

END;
$$LANGUAGE 'plpgsql';

CREATE TRIGGER trigger_register_validator BEFORE INSERT ON projekt.uzytkownik FOR EACH ROW EXECUTE PROCEDURE register_validator();

------------------------------------------------------------
CREATE OR REPLACE FUNCTION panel_validator() RETURNS TRIGGER AS
$$
DECLARE
    flag INTEGER;
    rec RECORD;
BEGIN
    flag := 0;
    SELECT * INTO rec FROM projekt.panel p WHERE p.login = NEW.login;

    IF rec IS NULL THEN
        flag := flag + 1;
    ELSE
        RAISE EXCEPTION '||Uzytkownik o podanym loginie juz istnieje!||';
        RETURN NULL;
    END IF;

    IF NEW.login LIKE '' THEN
        RAISE EXCEPTION '||Prosze wypelnic pole login!||';
        RETURN NULL;
    ELSE
        flag := flag + 1;
    END IF;

    IF NEW.login LIKE '_____' OR NEW.login LIKE '____' OR NEW.login LIKE '___' OR NEW.login LIKE '__' OR NEW.login LIKE '_' THEN
        RAISE EXCEPTION '||Login musi byc dluzszy, niz 5 znakow!||';
        RETURN NULL;
    ELSE
        flag := flag + 1;
    END IF;

    IF NEW.haslo LIKE '' THEN
        RAISE EXCEPTION '||Prosze wypelnic pole haslo!||';
        RETURN NULL;
    ELSE
        flag := flag + 1;
    END IF;

    IF NEW.haslo LIKE '___' OR NEW.haslo LIKE '__' OR NEW.haslo LIKE '_' OR NEW.haslo LIKE '____' OR NEW.haslo LIKE '_____' THEN
        RAISE EXCEPTION '||Haslo musi byc dluzsze, niz 5 znakow!||';
        RETURN NULL;
    ELSE
        flag := flag + 1;
    END IF;

    RETURN NEW;

END;
$$LANGUAGE 'plpgsql';

CREATE TRIGGER trigger_panel_validator BEFORE INSERT ON projekt.panel FOR EACH ROW EXECUTE PROCEDURE panel_validator();

------------------------------------------------------------

INSERT INTO kategoria VALUES(0, 'Dla osob specjalnej troski', 50);
INSERT INTO kategoria VALUES(1, 'Pakiet podstawowy', 40);
INSERT INTO kategoria VALUES(2, 'Pakiet premium', 100);
INSERT INTO kategoria VALUES(3, 'Pakiet ekstra-premium', 150);

INSERT INTO pokoj VALUES(0, 1, 1, 0, 5);
INSERT INTO pokoj VALUES(1, 2, 1, 0, 3);
INSERT INTO pokoj VALUES(2, 3, 1, 0, 2);
INSERT INTO pokoj VALUES(3, 4, 1, 0, 6);
INSERT INTO pokoj VALUES(4, 5, 1, 0, 3);
INSERT INTO pokoj VALUES(5, 6, 2, 1, 3);
INSERT INTO pokoj VALUES(6, 7, 2, 1, 4);
INSERT INTO pokoj VALUES(7, 8, 2, 1, 7);
INSERT INTO pokoj VALUES(8, 9, 2, 1, 5);
INSERT INTO pokoj VALUES(9, 10, 2, 1, 6);
INSERT INTO pokoj VALUES(10, 11, 3, 2, 2);
INSERT INTO pokoj VALUES(11, 12, 3, 2, 4);
INSERT INTO pokoj VALUES(12, 13, 3, 2, 5);
INSERT INTO pokoj VALUES(13, 14, 3, 2, 6);
INSERT INTO pokoj VALUES(14, 15, 3, 2, 3);
INSERT INTO pokoj VALUES(15, 16, 4, 3, 1);
INSERT INTO pokoj VALUES(16, 17, 4, 3, 2);
INSERT INTO pokoj VALUES(17, 18, 4, 3, 3);
INSERT INTO pokoj VALUES(18, 19, 4, 3, 4);
INSERT INTO pokoj VALUES(19, 10, 4, 3, 5);


-----------------------------------------
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

-- select * from get_pokoje(5, CAST('2020-12-31' AS DATE), CAST('2021-01-03' AS DATE));
-- select * from get_pokoje(5,'2020-12-31', '2021-01-03');
INSERT INTO rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(0, 8, NOW(),CAST('2021-01-01' AS DATE),CAST('2021-01-02' AS DATE), 0,5);
INSERT INTO rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(0, 13, NOW(),CAST('2021-01-01' AS DATE),CAST('2021-01-02' AS DATE), 0,5);
-- select * from get_pokoje(5,'2021-01-04', '2021-01-08');

--------------------------
CREATE OR REPLACE FUNCTION get_liczba_dni(data_start DATE, data_stop DATE) RETURNS int AS $$
DECLARE
    x INT;
BEGIN
    x := data_stop - data_start + 1;
    RETURN x;
END;
$$LANGUAGE 'plpgsql';

SELECT * FROM get_liczba_dni('2021-01-04', '2021-01-08');
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

-------------------------
CREATE OR REPLACE FUNCTION platnosc_rezerwacja() RETURNS TRIGGER AS $$
DECLARE
    cena NUMERIC;
    liczba_dni INT;
    rec RECORD;
BEGIN
    liczba_dni := projekt.get_liczba_dni(NEW.od_kiedy, NEW.do_kiedy);
    SELECT liczba_miejsc, cena_od_osoby INTO rec FROM projekt.pokojeView WHERE pokoj_id = NEW.pokoj_id;
    cena := liczba_dni*rec.cena_od_osoby*(NEW.liczba_doroslych + 0.5*NEW.liczba_dzieci + 0.25*(rec.liczba_miejsc - NEW.liczba_dzieci - NEW.liczba_doroslych));
    RAISE NOTICE 'PLATNOSC REZERWACJA: %', cena;
    RAISE NOTICE 'LICZBA DNI: %', liczba_dni;
    INSERT INTO projekt.oplata("rezerwacja_id", "status_czy_oplacone", "kwota") VALUES(NEW.rezerwacja_id, 'Nieoplacone', cena);
    RETURN NEW;
END;
$$LANGUAGE 'plpgsql';

CREATE TRIGGER trigger_platnosc_rezerwacja AFTER INSERT ON projekt.rezerwacje FOR EACH ROW EXECUTE PROCEDURE platnosc_rezerwacja();

-----------------------------
CREATE OR REPLACE FUNCTION platnosc_update() RETURNS TRIGGER AS $$
DECLARE
    rec RECORD;
    r_id INT;
    dodatkowa_kwota NUMERIC;
BEGIN
    r_id := projekt.latest_rezerwacja_id();
    SELECT liczba_doroslych, liczba_dzieci INTO rec FROM projekt.rezerwacje WHERE rezerwacja_id = r_id;
    dodatkowa_kwota := NEW.cena_od_osoby*(rec.liczba_doroslych + 0.5*rec.liczba_dzieci);
    RAISE NOTICE 'CENA USLUGI: %', dodatkowa_kwota;
    UPDATE projekt.oplata SET kwota = kwota + dodatkowa_kwota WHERE rezerwacja_id = r_id;
    RETURN NEW;
END;
$$LANGUAGE 'plpgsql';

CREATE TRIGGER trigger_platnosc_update AFTER INSERT ON projekt.dodatkowe_uslugi FOR EACH ROW EXECUTE PROCEDURE platnosc_update();
-----------------------------

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

CREATE VIEW pokojeRezerwacjeView AS SELECT r.*, p.numer_pokoju, p.pietro, p.liczba_miejsc, p.nazwa_kategorii, p.cena_od_osoby FROM rezerwacje r JOIN pokojeView p ON r.pokoj_id = p.pokoj_id;
-- SELECT * FROM pokojeRezerwacjeView;
CREATE VIEW uslugiPokojeRezerwacjeView AS SELECT r.*, u.dodatkowe_uslugi_id, u.nazwa_uslugi, u.cena_od_osoby AS cena_uslugi FROM pokojeRezerwacjeView r JOIN dodatkowe_uslugi u ON r.rezerwacja_id = u.rezerwacja_id;
-- SELECT * FROM uslugiPokojeRezerwacjeView;

CREATE VIEW RezerwacjeInfoView AS SELECT r.*, o.oplata_id, o.status_czy_oplacone, o.kwota FROM uslugiPokojeRezerwacjeView r JOIN oplata o ON r.rezerwacja_id = o.rezerwacja_id;
-- SELECT * FROM RezerwacjeInfoView;

---------------------------------------------

CREATE OR REPLACE FUNCTION oplataZaplac(id int) RETURNS void AS $$
BEGIN
    UPDATE projekt.oplata SET status_czy_oplacone = 'Oplacone' WHERE oplata_id = id;
END;
$$LANGUAGE 'plpgsql';
-------------------------------------------
CREATE OR REPLACE FUNCTION oplataRezygnuj(id int) RETURNS void AS $$
BEGIN
    UPDATE projekt.oplata SET status_czy_oplacone = 'Nieoplacone - rezygnacja' WHERE oplata_id = id;
END;
$$LANGUAGE 'plpgsql';

------------------------------------------- TO MOZE SIE GRYZC Z AKTUALIZOWANIEM CENY Z USLUG
CREATE OR REPLACE FUNCTION oplataStatus() RETURNS TRIGGER AS $$
DECLARE
    rec RECORD;
BEGIN
    SELECT * INTO rec FROM projekt.rezerwacje r WHERE OLD.rezerwacja_id = r.rezerwacja_id;
    IF OLD.kwota <> NEW.kwota THEN
        RETURN NEW;
    ELSE
        IF OLD.status_czy_oplacone LIKE 'Nieoplacone' THEN
            IF NEW.status_czy_oplacone LIKE 'Oplacone' THEN
                INSERT INTO projekt.zakwaterowani_goscie_info("rezerwacja_id","status_czy_zakwaterowany") VALUES(rec.rezerwacja_id, 'Oczekiwanie na zakwaterowanie');
            ELSIF NEW.status_czy_oplacone LIKE 'Nieoplacone - rezygnacja' THEN
                INSERT INTO projekt.rezygnacja_z_rezerwacji_info("rezerwacja_id", "uzytkownik_id") VALUES(rec.rezerwacja_id, rec.uzytkownik_id);
            END IF;
            RETURN NEW;
        ELSE
            RAISE EXCEPTION '||Nie mozna ponownie zmienic wczesniej zmienionego statusu oplaty rezerwacji!||';
            RETURN NULL;
        END IF;
    END IF;
END;
$$LANGUAGE 'plpgsql';

CREATE TRIGGER trigger_platnosc_status BEFORE UPDATE ON projekt.oplata FOR EACH ROW EXECUTE PROCEDURE oplataStatus();

-----------------------------------------------
CREATE OR REPLACE FUNCTION RezerwacjaNaTydzienPrzed() RETURNS TRIGGER AS $$
BEGIN
    IF NEW.od_kiedy - CAST(NOW() AS DATE) >= 7 THEN
        RETURN NEW;
    ELSE
        RAISE EXCEPTION '||Wybrany poczatek zakwaterowania zaczyna sie wczesniej, niz za 7 dni - prosze wybrac pozniejszy termin!||';
        RETURN NULL;
    END IF;
END;
$$LANGUAGE 'plpgsql';

CREATE TRIGGER trigger_RezerwacjaNaTydzienPrzed BEFORE INSERT ON projekt.rezerwacje FOR EACH ROW EXECUTE PROCEDURE RezerwacjaNaTydzienPrzed();
---------------------------------------------
CREATE OR REPLACE FUNCTION RezerwacjaOsobyValidator() RETURNS TRIGGER AS $$
DECLARE
    rec RECORD;
BEGIN
    SELECT liczba_miejsc AS lm INTO rec FROM projekt.pokoj WHERE pokoj_id = NEW.pokoj_id;
    IF NEW.liczba_dzieci = 0 AND NEW.liczba_doroslych = 0 THEN
        RAISE EXCEPTION '||Nie mozna zrealizowac rezerwacji dla braku zadeklarowanych osob!||';
        RETURN NULL;
    ELSIF NEW.liczba_dzieci + NEW.liczba_doroslych > rec.lm THEN
        RAISE EXCEPTION '||Nie mozna zrealizowac rezerwacji dla liczby osob przewyzszajacej pojemnosc pokoju!||';
        RETURN NULL;
    ELSE
        RETURN NEW;
    END IF;
END;
$$LANGUAGE 'plpgsql';

CREATE TRIGGER trigger_RezerwacjaOsobyValidator BEFORE INSERT ON projekt.rezerwacje FOR EACH ROW EXECUTE PROCEDURE RezerwacjaOsobyValidator();
-----------------------------------------
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
CREATE OR REPLACE VIEW OplataRezerwacje AS SELECT o.*, r.uzytkownik_id, r.od_kiedy, r.do_kiedy FROM projekt.oplata o JOIN projekt.rezerwacje r ON o.rezerwacja_id = r.rezerwacja_id;

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
CREATE OR REPLACE FUNCTION RezygnacjaChecker() RETURNS TRIGGER AS $$
DECLARE
    rec RECORD;
BEGIN
    SELECT COUNT(*) AS c INTO rec FROM projekt.rezygnacja_z_rezerwacji_info WHERE uzytkownik_id = NEW.uzytkownik_id;
    IF rec.c >= 5 THEN
        INSERT INTO projekt.czarna_lista("uzytkownik_id","powod") VALUES(NEW.uzytkownik_id, 'Ciagle rezygnowanie ze skladanych rezerwacji.');
    END IF;
    RETURN NEW;
END;
$$LANGUAGE 'plpgsql';

CREATE TRIGGER trigger_RezygnacjaChecker AFTER INSERT ON projekt.rezygnacja_z_rezerwacji_info FOR EACH ROW EXECUTE PROCEDURE RezygnacjaChecker();
---------------------------
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
-------------------------------
CREATE OR REPLACE FUNCTION CzarnaListaChecker() RETURNS TRIGGER AS $$
DECLARE
    rec RECORD;
BEGIN
    SELECT typ INTO rec FROM projekt.uzytkownik WHERE uzytkownik_id = NEW.uzytkownik_id;
    IF NEW.powod LIKE '' THEN
        RAISE EXCEPTION '||Nie mozna zablokowac uzytkownika bez podania przyczyny!||';
        RETURN NULL;
    ELSIF rec.typ LIKE 'HeadAdmin' OR rec.typ LIKE 'Pracownik' THEN
        RAISE EXCEPTION '||Nie mozna zablokowac administratora!||';
        RETURN NULL;
    END IF;
    RETURN NEW;
END;
$$LANGUAGE 'plpgsql';

CREATE TRIGGER trigger_CzarnaListaChecker BEFORE INSERT ON projekt.czarna_lista FOR EACH ROW EXECUTE PROCEDURE CzarnaListaChecker();
-----------------------------
CREATE OR REPLACE FUNCTION TransakcjaRezerwacjaChecker() RETURNS TRIGGER AS $$
DECLARE
    rec RECORD;
    rec_check RECORD;
    rec_rezerw RECORD;
    query TEXT;
BEGIN
    SELECT * INTO rec_rezerw FROM projekt.rezerwacje WHERE rezerwacja_id = NEW.rezerwacja_id;
    SELECT rezerwacja_id INTO rec FROM projekt.dodatkowe_uslugi WHERE rezerwacja_id = NEW.rezerwacja_id;
    query := 'SELECT COUNT(*) AS c FROM projekt.rezerwacje WHERE (pokoj_id = $1 AND (($2>od_kiedy AND $2<do_kiedy) OR ($3>od_kiedy AND $3<do_kiedy) OR (od_kiedy>$2 AND do_kiedy<$3) OR ($2 = od_kiedy) OR ($3 = do_kiedy)))';
    EXECUTE query INTO rec_check USING rec_rezerw.pokoj_id, rec_rezerw.od_kiedy, rec_rezerw.do_kiedy;
    IF rec IS NOT NULL THEN
        RAISE EXCEPTION '||Prosze sprobowac ponownie za 5 minut!||';
        RETURN NULL;
    END IF;
    IF rec_check.c <> 1 THEN
        RAISE EXCEPTION '||Prosze sprobowac ponownie, pokoj na wybrany termin juz jest niedostepny!||';
        RETURN NULL;
    END IF;
    RETURN NEW;
END;
$$LANGUAGE 'plpgsql';

CREATE TRIGGER trigger_TransakcjaRezerwacjaChecker BEFORE INSERT ON projekt.dodatkowe_uslugi FOR EACH ROW EXECUTE PROCEDURE TransakcjaRezerwacjaChecker();
-----------------------------
CREATE OR REPLACE FUNCTION TransakcjaRejestracjaChecker() RETURNS TRIGGER AS $$
DECLARE
    rec RECORD;
BEGIN
    SELECT uzyt_id INTO rec FROM projekt.panel WHERE uzyt_id = NEW.uzyt_id;
    IF rec IS NOT NULL THEN
        RAISE EXCEPTION '||Prosze sprobowac ponownie za 5 minut!||';
        RETURN NULL;
    END IF;
    RETURN NEW;
END;
$$LANGUAGE 'plpgsql';

CREATE TRIGGER trigger_TransakcjaRejestracjaChecker BEFORE INSERT ON projekt.panel FOR EACH ROW EXECUTE PROCEDURE TransakcjaRejestracjaChecker();

select typ, count(*) from uzytkownicy group by typ;
select typ, count(*) from uzytkownicy group by imie, typ having imie like 'Kamil';

--------------------------------------------------------
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