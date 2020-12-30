CREATE TABLE "panel" (
  "uzyt_id" int PRIMARY KEY,
  "login" varchar,
  "haslo" varchar
);

CREATE TABLE "uzytkownik" (
  "uzytkownik_id" int PRIMARY KEY,
  "imie" varchar,
  "nazwisko" varchar,
  "e_mail" text,
  "numer" text
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
  "status_czy_oplacone" boolean,
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
  "status_czy_zakwaterowany" boolean
);

CREATE TABLE "czarna_lista" (
  "info_id" serial PRIMARY KEY,
  "uzytkownik_id" int,
  "powod" varchar
);

ALTER TABLE "rezerwacje" ADD FOREIGN KEY ("uzytkownik_id") REFERENCES "uzytkownik" ("uzytkownik_id");

ALTER TABLE "oplata" ADD FOREIGN KEY ("rezerwacja_id") REFERENCES "rezerwacje" ("rezerwacja_id");

ALTER TABLE "dodatkowe_uslugi" ADD FOREIGN KEY ("rezerwacja_id") REFERENCES "rezerwacje" ("rezerwacja_id");

ALTER TABLE "rezerwacje" ADD FOREIGN KEY ("pokoj_id") REFERENCES "pokoj" ("pokoj_id");

ALTER TABLE "pokoj" ADD FOREIGN KEY ("kategoria_id") REFERENCES "kategoria" ("kategoria_id");

ALTER TABLE "zakwaterowani_goscie_info" ADD FOREIGN KEY ("rezerwacja_id") REFERENCES "rezerwacje" ("rezerwacja_id");

ALTER TABLE "panel" ADD FOREIGN KEY ("uzyt_id") REFERENCES "uzytkownik" ("uzytkownik_id");

ALTER TABLE "czarna_lista" ADD FOREIGN KEY ("uzytkownik_id") REFERENCES "uzytkownik" ("uzytkownik_id");


INSERT INTO "uzytkownik" VALUES (0, 'ADMIN', 'ADMIN', 'TEST@TEST.COM', 999999999);
INSERT INTO "panel" VALUES (0, 'admin', 'admin');
CREATE VIEW uzytkownicy AS SELECT * FROM uzytkownik u JOIN panel p ON u.uzytkownik_id = p.uzyt_id;
CREATE VIEW pokojeView AS SELECT pokoj_id, numer_pokoju, pietro, liczba_miejsc, p.kategoria_id, nazwa_kategorii, cena_od_osoby FROM pokoj p JOIN kategoria k ON p.kategoria_id = k.kategoria_id;
-- CREATE VIEW mojeRezerwacje1 AS SELECT * FROM 
SELECT * FROM uzytkownik u JOIN panel p ON u.uzytkownik_id = p.uzyt_id;

-------------------------------------------------------------

CREATE OR REPLACE FUNCTION numeric_check(TEXT) RETURNS BOOLEAN AS $$
DECLARE x NUMERIC;
BEGIN
    x = $1::NUMERIC;
    RETURN TRUE;
    EXCEPTION WHEN others THEN
    RETURN FALSE;
END;
$$
STRICT
LANGUAGE plpgsql IMMUTABLE;

-------------------------------------------------------------

CREATE OR REPLACE FUNCTION register_validator() RETURNS TRIGGER AS
$$
DECLARE
    -- check_rec RECORD;
    flag INTEGER;
BEGIN
    flag := 0;
    -- SELECT e_mail, numer INTO check_rec FROM uzytkownik WHERE uzytkownik_id = NEW.uzytkownik_id;
    IF NEW.e_mail LIKE '%@%' THEN
        flag := flag + 1;
    END IF;
    IF projekt.numeric_check(NEW.numer) THEN
        flag := flag + 1;
    END IF;
    IF flag = 2 THEN
        RETURN NEW;
    ELSE
        RETURN NULL;
    END IF;

END;
$$LANGUAGE 'plpgsql';

CREATE TRIGGER trigger_register_validator BEFORE INSERT ON uzytkownik FOR EACH ROW EXECUTE PROCEDURE register_validator();

------------------------------------------------------------

INSERT INTO kategoria VALUES(0, 'Dla osob specjalnej troski', 50);
INSERT INTO kategoria VALUES(1, 'Pakiet podstawowy', 40);
INSERT INTO kategoria VALUES(2, 'Pakiet premium', 100);

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

select * from get_pokoje(5, CAST('2020-12-31' AS DATE), CAST('2021-01-03' AS DATE));
select * from get_pokoje(5,'2020-12-31', '2021-01-03');
INSERT INTO rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(0, 8, NOW(),CAST('2021-01-01' AS DATE),CAST('2021-01-02' AS DATE), 0,5);
INSERT INTO rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(0, 13, NOW(),CAST('2021-01-01' AS DATE),CAST('2021-01-02' AS DATE), 0,5);
select * from get_pokoje(5,'2021-01-04', '2021-01-08');