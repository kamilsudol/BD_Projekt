CREATE VIEW uzytkownicy AS SELECT * FROM uzytkownik u JOIN panel p ON u.uzytkownik_id = p.uzyt_id;
CREATE VIEW pokojeView AS SELECT pokoj_id, numer_pokoju, pietro, liczba_miejsc, p.kategoria_id, nazwa_kategorii, cena_od_osoby FROM pokoj p JOIN kategoria k ON p.kategoria_id = k.kategoria_id;

CREATE VIEW pokojeRezerwacjeView AS SELECT r.*, p.numer_pokoju, p.pietro, p.liczba_miejsc, p.nazwa_kategorii, p.cena_od_osoby FROM rezerwacje r JOIN pokojeView p ON r.pokoj_id = p.pokoj_id;
CREATE VIEW uslugiPokojeRezerwacjeView AS SELECT r.*, u.dodatkowe_uslugi_id, u.nazwa_uslugi, u.cena_od_osoby AS cena_uslugi FROM pokojeRezerwacjeView r JOIN dodatkowe_uslugi u ON r.rezerwacja_id = u.rezerwacja_id;
CREATE VIEW RezerwacjeInfoView AS SELECT r.*, o.oplata_id, o.status_czy_oplacone, o.kwota FROM uslugiPokojeRezerwacjeView r JOIN oplata o ON r.rezerwacja_id = o.rezerwacja_id;
CREATE VIEW RezerwacjeAllInfoView AS SELECT r.*, u.imie, u.nazwisko FROM projekt.RezerwacjeInfoView r JOIN projekt.uzytkownik u ON r.uzytkownik_id = u.uzytkownik_id;

CREATE OR REPLACE VIEW OplataRezerwacje AS SELECT o.*, r.uzytkownik_id, r.od_kiedy, r.do_kiedy FROM projekt.oplata o JOIN projekt.rezerwacje r ON o.rezerwacja_id = r.rezerwacja_id;

----WIDOK zakwaterowani
CREATE OR REPLACE VIEW rezerwPokojView AS SELECT r.*, p.numer_pokoju FROM projekt.pokoj p JOIN projekt.rezerwacje r ON p.pokoj_id = r.pokoj_id;
CREATE OR REPLACE VIEW zakwaterowaniView AS SELECT z.info_id, z.status_czy_zakwaterowany, u.imie, u.nazwisko, r.* FROM projekt.uzytkownik u, projekt.zakwaterowani_goscie_info z JOIN projekt.rezerwPokojView r ON z.rezerwacja_id = r.rezerwacja_id WHERE r.uzytkownik_id = u.uzytkownik_id;

-----WIDOK zablokowani
CREATE OR REPLACE VIEW zablokowaniView AS SELECT u.*, z.info_id, z.powod FROM projekt.uzytkownicy u JOIN projekt.czarna_lista z ON u.uzyt_id = z.uzytkownik_id;
-----Widok rezygnacje
CREATE OR REPLACE VIEW UzytRezerwView AS SELECT r.*, u.imie, u.nazwisko FROM projekt.rezerwacje r JOIN projekt.uzytkownik u ON u.uzytkownik_id = r.uzytkownik_id;
CREATE OR REPLACE VIEW rezygnacjeView AS SELECT r.*, x.info_id FROM projekt.rezygnacja_z_rezerwacji_info x JOIN projekt.UzytRezerwView r ON x.rezerwacja_id = r.rezerwacja_id;
