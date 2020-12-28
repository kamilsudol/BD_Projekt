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
  "numer" numeric
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

CREATE OR REPLACE FUNCTION isnumeric(text) RETURNS BOOLEAN AS $$
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
    IF isnumeric(NEW.numer) THEN
        flag := flag + 1;
        NEW.numer := CAST(NEW.numer AS NUMERIC);
    END IF;
    IF flag = 2 THEN
        RETURN NEW;
    ELSE
        RETURN NULL;
    END IF;

END;
$$LANGUAGE 'plpgsql';

CREATE TRIGGER trigger_register_validator BEFORE INSERT ON uzytkownik FOR EACH ROW EXECUTE PROCEDURE register_validator();