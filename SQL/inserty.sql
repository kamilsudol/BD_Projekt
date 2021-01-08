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

INSERT INTO "uzytkownik"("imie","nazwisko","e_mail","numer","typ") VALUES ('ADMIN', 'ADMIN', 'TEST@TEST.COM', 999999999, 'HeadAdmin');
INSERT INTO "panel" VALUES (latest_uzytkownik_id(), 'admin', 'admin');