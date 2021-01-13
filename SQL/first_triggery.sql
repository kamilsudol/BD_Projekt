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
------------------------------------------------------------
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