BEGIN;
INSERT INTO projekt.rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(18, 12,NOW(),CAST('2021-8-5' AS DATE),CAST('2021-11-10' AS DATE), 3,2);
INSERT INTO projekt.dodatkowe_uslugi("nazwa_uslugi", "cena_od_osoby", "rezerwacja_id") VALUES('Basen', 15.0, latest_rezerwacja_id());
COMMIT;

BEGIN;
INSERT INTO projekt.rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(12, 17,NOW(),CAST('2021-3-23' AS DATE),CAST('2021-7-23' AS DATE), 2,3);
INSERT INTO projekt.dodatkowe_uslugi("nazwa_uslugi", "cena_od_osoby", "rezerwacja_id") VALUES('Bilard', 10.0, latest_rezerwacja_id());
COMMIT;

BEGIN;
INSERT INTO projekt.rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(13, 14,NOW(),CAST('2020-12-29' AS DATE),CAST('2021-12-29' AS DATE), 5,0);
INSERT INTO projekt.dodatkowe_uslugi("nazwa_uslugi", "cena_od_osoby", "rezerwacja_id") VALUES('Brak', 0.0, latest_rezerwacja_id());
COMMIT;

BEGIN;
INSERT INTO projekt.rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(10, 19,NOW(),CAST('2020-4-7' AS DATE),CAST('2021-8-18' AS DATE), 5,0);
INSERT INTO projekt.dodatkowe_uslugi("nazwa_uslugi", "cena_od_osoby", "rezerwacja_id") VALUES('Basen', 15.0, latest_rezerwacja_id());
COMMIT;

BEGIN;
INSERT INTO projekt.rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(6, 15,NOW(),CAST('2021-8-11' AS DATE),CAST('2022-9-13' AS DATE), 1,3);
INSERT INTO projekt.dodatkowe_uslugi("nazwa_uslugi", "cena_od_osoby", "rezerwacja_id") VALUES('Kregielnia', 25.0, latest_rezerwacja_id());
COMMIT;

BEGIN;
INSERT INTO projekt.rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(9, 0,NOW(),CAST('2021-5-6' AS DATE),CAST('2021-7-30' AS DATE), 3,2);
INSERT INTO projekt.dodatkowe_uslugi("nazwa_uslugi", "cena_od_osoby", "rezerwacja_id") VALUES('Basen', 15.0, latest_rezerwacja_id());
COMMIT;

BEGIN;
INSERT INTO projekt.rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(1, 11,NOW(),CAST('2021-10-14' AS DATE),CAST('2021-12-15' AS DATE), 2,3);
INSERT INTO projekt.dodatkowe_uslugi("nazwa_uslugi", "cena_od_osoby", "rezerwacja_id") VALUES('Brak', 0.0, latest_rezerwacja_id());
COMMIT;

BEGIN;
INSERT INTO projekt.rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(3, 15,NOW(),CAST('2020-5-2' AS DATE),CAST('2021-8-14' AS DATE), 4,1);
INSERT INTO projekt.dodatkowe_uslugi("nazwa_uslugi", "cena_od_osoby", "rezerwacja_id") VALUES('Silownia', 5.0, latest_rezerwacja_id());
COMMIT;

BEGIN;
INSERT INTO projekt.rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(9, 1,NOW(),CAST('2021-8-17' AS DATE),CAST('2022-11-29' AS DATE), 2,0);
INSERT INTO projekt.dodatkowe_uslugi("nazwa_uslugi", "cena_od_osoby", "rezerwacja_id") VALUES('Silownia', 5.0, latest_rezerwacja_id());
COMMIT;

BEGIN;
INSERT INTO projekt.rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(5, 18,NOW(),CAST('2021-6-6' AS DATE),CAST('2022-6-15' AS DATE), 5,0);
INSERT INTO projekt.dodatkowe_uslugi("nazwa_uslugi", "cena_od_osoby", "rezerwacja_id") VALUES('Basen', 15.0, latest_rezerwacja_id());
COMMIT;

BEGIN;
INSERT INTO projekt.rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(13, 8,NOW(),CAST('2020-6-10' AS DATE),CAST('2020-12-28' AS DATE), 0,2);
INSERT INTO projekt.dodatkowe_uslugi("nazwa_uslugi", "cena_od_osoby", "rezerwacja_id") VALUES('Brak', 0.0, latest_rezerwacja_id());
COMMIT;

BEGIN;
INSERT INTO projekt.rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(6, 4,NOW(),CAST('2020-9-21' AS DATE),CAST('2021-9-22' AS DATE), 4,0);
INSERT INTO projekt.dodatkowe_uslugi("nazwa_uslugi", "cena_od_osoby", "rezerwacja_id") VALUES('Silownia', 5.0, latest_rezerwacja_id());
COMMIT;

BEGIN;
INSERT INTO projekt.rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(2, 14,NOW(),CAST('2021-4-3' AS DATE),CAST('2021-7-22' AS DATE), 1,4);
INSERT INTO projekt.dodatkowe_uslugi("nazwa_uslugi", "cena_od_osoby", "rezerwacja_id") VALUES('Basen', 15.0, latest_rezerwacja_id());
COMMIT;

BEGIN;
INSERT INTO projekt.rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(4, 8,NOW(),CAST('2021-8-8' AS DATE),CAST('2021-11-23' AS DATE), 1,2);
INSERT INTO projekt.dodatkowe_uslugi("nazwa_uslugi", "cena_od_osoby", "rezerwacja_id") VALUES('Brak', 0.0, latest_rezerwacja_id());
COMMIT;

BEGIN;
INSERT INTO projekt.rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(6, 17,NOW(),CAST('2021-12-14' AS DATE),CAST('2022-12-25' AS DATE), 2,2);
INSERT INTO projekt.dodatkowe_uslugi("nazwa_uslugi", "cena_od_osoby", "rezerwacja_id") VALUES('Brak', 0.0, latest_rezerwacja_id());
COMMIT;

BEGIN;
INSERT INTO projekt.rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(4, 11,NOW(),CAST('2021-1-11' AS DATE),CAST('2022-11-28' AS DATE), 0,0);
INSERT INTO projekt.dodatkowe_uslugi("nazwa_uslugi", "cena_od_osoby", "rezerwacja_id") VALUES('Bilard', 10.0, latest_rezerwacja_id());
COMMIT;

BEGIN;
INSERT INTO projekt.rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(2, 14,NOW(),CAST('2021-8-16' AS DATE),CAST('2021-8-23' AS DATE), 4,1);
INSERT INTO projekt.dodatkowe_uslugi("nazwa_uslugi", "cena_od_osoby", "rezerwacja_id") VALUES('Kregielnia', 25.0, latest_rezerwacja_id());
COMMIT;

BEGIN;
INSERT INTO projekt.rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(0, 3,NOW(),CAST('2020-5-27' AS DATE),CAST('2020-11-27' AS DATE), 3,1);
INSERT INTO projekt.dodatkowe_uslugi("nazwa_uslugi", "cena_od_osoby", "rezerwacja_id") VALUES('Basen', 15.0, latest_rezerwacja_id());
COMMIT;

BEGIN;
INSERT INTO projekt.rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(10, 11,NOW(),CAST('2021-1-8' AS DATE),CAST('2021-9-13' AS DATE), 1,4);
INSERT INTO projekt.dodatkowe_uslugi("nazwa_uslugi", "cena_od_osoby", "rezerwacja_id") VALUES('Basen', 15.0, latest_rezerwacja_id());
COMMIT;

BEGIN;
INSERT INTO projekt.rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(8, 7,NOW(),CAST('2020-8-23' AS DATE),CAST('2020-12-27' AS DATE), 5,0);
INSERT INTO projekt.dodatkowe_uslugi("nazwa_uslugi", "cena_od_osoby", "rezerwacja_id") VALUES('Brak', 0.0, latest_rezerwacja_id());
COMMIT;

BEGIN;
INSERT INTO projekt.rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(17, 2,NOW(),CAST('2021-8-6' AS DATE),CAST('2021-10-21' AS DATE), 4,1);
INSERT INTO projekt.dodatkowe_uslugi("nazwa_uslugi", "cena_od_osoby", "rezerwacja_id") VALUES('Bilard', 10.0, latest_rezerwacja_id());
COMMIT;

BEGIN;
INSERT INTO projekt.rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(18, 12,NOW(),CAST('2020-10-25' AS DATE),CAST('2021-10-25' AS DATE), 2,2);
INSERT INTO projekt.dodatkowe_uslugi("nazwa_uslugi", "cena_od_osoby", "rezerwacja_id") VALUES('Silownia', 5.0, latest_rezerwacja_id());
COMMIT;

BEGIN;
INSERT INTO projekt.rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(15, 14,NOW(),CAST('2021-5-11' AS DATE),CAST('2021-12-21' AS DATE), 5,0);
INSERT INTO projekt.dodatkowe_uslugi("nazwa_uslugi", "cena_od_osoby", "rezerwacja_id") VALUES('Kregielnia', 25.0, latest_rezerwacja_id());
COMMIT;

BEGIN;
INSERT INTO projekt.rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(11, 3,NOW(),CAST('2020-10-4' AS DATE),CAST('2021-12-29' AS DATE), 4,0);
INSERT INTO projekt.dodatkowe_uslugi("nazwa_uslugi", "cena_od_osoby", "rezerwacja_id") VALUES('Silownia', 5.0, latest_rezerwacja_id());
COMMIT;

BEGIN;
INSERT INTO projekt.rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(5, 11,NOW(),CAST('2020-7-23' AS DATE),CAST('2020-11-23' AS DATE), 1,1);
INSERT INTO projekt.dodatkowe_uslugi("nazwa_uslugi", "cena_od_osoby", "rezerwacja_id") VALUES('Kregielnia', 25.0, latest_rezerwacja_id());
COMMIT;

BEGIN;
INSERT INTO projekt.rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(0, 16,NOW(),CAST('2021-6-3' AS DATE),CAST('2021-10-9' AS DATE), 1,3);
INSERT INTO projekt.dodatkowe_uslugi("nazwa_uslugi", "cena_od_osoby", "rezerwacja_id") VALUES('Basen', 15.0, latest_rezerwacja_id());
COMMIT;

BEGIN;
INSERT INTO projekt.rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(18, 2,NOW(),CAST('2021-2-1' AS DATE),CAST('2021-3-14' AS DATE), 3,0);
INSERT INTO projekt.dodatkowe_uslugi("nazwa_uslugi", "cena_od_osoby", "rezerwacja_id") VALUES('Basen', 15.0, latest_rezerwacja_id());
COMMIT;

BEGIN;
INSERT INTO projekt.rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(3, 3,NOW(),CAST('2021-5-4' AS DATE),CAST('2021-8-5' AS DATE), 2,2);
INSERT INTO projekt.dodatkowe_uslugi("nazwa_uslugi", "cena_od_osoby", "rezerwacja_id") VALUES('Bilard', 10.0, latest_rezerwacja_id());
COMMIT;

BEGIN;
INSERT INTO projekt.rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(2, 17,NOW(),CAST('2021-7-24' AS DATE),CAST('2022-7-28' AS DATE), 1,0);
INSERT INTO projekt.dodatkowe_uslugi("nazwa_uslugi", "cena_od_osoby", "rezerwacja_id") VALUES('Bilard', 10.0, latest_rezerwacja_id());
COMMIT;

BEGIN;
INSERT INTO projekt.rezerwacje("uzytkownik_id","pokoj_id","data_rezerwacji","od_kiedy","do_kiedy","liczba_dzieci","liczba_doroslych") VALUES(10, 8,NOW(),CAST('2020-4-23' AS DATE),CAST('2020-4-26' AS DATE), 0,0);
INSERT INTO projekt.dodatkowe_uslugi("nazwa_uslugi", "cena_od_osoby", "rezerwacja_id") VALUES('Brak', 0.0, latest_rezerwacja_id());
COMMIT;
