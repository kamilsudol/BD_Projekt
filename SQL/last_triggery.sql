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
-----------------------------
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
-----------------------------------------
CREATE OR REPLACE FUNCTION RezygnacjaChecker() RETURNS TRIGGER AS $$
DECLARE
    rec RECORD;
BEGIN
    SELECT COUNT(*) AS c INTO rec FROM projekt.rezygnacja_z_rezerwacji_info WHERE uzytkownik_id = NEW.uzytkownik_id;
    IF rec.c >= 5 THEN
        INSERT INTO projekt.czarna_lista("uzytkownik_id","powod") VALUES(NEW.uzytkownik_id, 'Ciagle rezygnowanie ze skladanych rezerwacji.');
    END IF;
    DELETE FROM projekt.rezerwacje WHERE rezerwacja_id = NEW.rezerwacja_id;
    RETURN NEW;
END;
$$LANGUAGE 'plpgsql';

CREATE TRIGGER trigger_RezygnacjaChecker AFTER INSERT ON projekt.rezygnacja_z_rezerwacji_info FOR EACH ROW EXECUTE PROCEDURE RezygnacjaChecker();
---------------------------
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
-----------------------------
CREATE OR REPLACE FUNCTION DeleteRezerwacje() RETURNS TRIGGER AS $$
BEGIN
    DELETE FROM projekt.oplata WHERE rezerwacja_id = OLD.rezerwacja_id;
    DELETE FROM projekt.dodatkowe_uslugi WHERE rezerwacja_id = OLD.rezerwacja_id;
    RETURN OLD;
END;
$$LANGUAGE 'plpgsql';

CREATE TRIGGER trigger_DeleteRezerwacje BEFORE DELETE ON projekt.rezerwacje FOR EACH ROW EXECUTE PROCEDURE DeleteRezerwacje();