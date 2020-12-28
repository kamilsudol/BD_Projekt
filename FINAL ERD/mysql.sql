CREATE TABLE `panel` (
  `uzyt_id` int PRIMARY KEY,
  `login` varchar(255),
  `haslo` varchar(255)
);

CREATE TABLE `uzytkownik` (
  `uzytkownik_id` int PRIMARY KEY,
  `imie` varchar(255),
  `nazwisko` varchar(255),
  `e_mail` text,
  `numer` numeric
);

CREATE TABLE `rezerwacje` (
  `rezerwacja_id` serial PRIMARY KEY,
  `uzytkownik_id` int,
  `pokoj_id` int,
  `data_rezerwacji` date,
  `od_kiedy` date,
  `do_kiedy` date,
  `liczba_dzieci` int,
  `liczba_doroslych` int
);

CREATE TABLE `pokoj` (
  `pokoj_id` int PRIMARY KEY,
  `numer_pokoju` int,
  `pietro` int,
  `kategoria_id` int,
  `liczba_miejsc` int
);

CREATE TABLE `kategoria` (
  `kategoria_id` int PRIMARY KEY,
  `nazwa_kategorii` text,
  `cena_od_osoby` numeric
);

CREATE TABLE `oplata` (
  `oplata_id` serial PRIMARY KEY,
  `rezerwacja_id` int,
  `status_czy_oplacone` boolean,
  `kwota` numeric
);

CREATE TABLE `dodatkowe_uslugi` (
  `dodatkowe_uslugi_id` serial PRIMARY KEY,
  `nazwa_uslugi` text,
  `cena_od_osoby` numeric,
  `rezerwacja_id` int
);

CREATE TABLE `zakwaterowani_goscie_info` (
  `info_id` serial PRIMARY KEY,
  `rezerwacja_id` int,
  `status_czy_zakwaterowany` boolean
);

CREATE TABLE `czarna_lista` (
  `info_id` serial PRIMARY KEY,
  `uzytkownik_id` int,
  `powod` varchar(255)
);

ALTER TABLE `rezerwacje` ADD FOREIGN KEY (`uzytkownik_id`) REFERENCES `uzytkownik` (`uzytkownik_id`);

ALTER TABLE `rezerwacje` ADD FOREIGN KEY (`rezerwacja_id`) REFERENCES `oplata` (`rezerwacja_id`);

ALTER TABLE `dodatkowe_uslugi` ADD FOREIGN KEY (`rezerwacja_id`) REFERENCES `rezerwacje` (`rezerwacja_id`);

ALTER TABLE `rezerwacje` ADD FOREIGN KEY (`pokoj_id`) REFERENCES `pokoj` (`pokoj_id`);

ALTER TABLE `pokoj` ADD FOREIGN KEY (`kategoria_id`) REFERENCES `kategoria` (`kategoria_id`);

ALTER TABLE `rezerwacje` ADD FOREIGN KEY (`rezerwacja_id`) REFERENCES `zakwaterowani_goscie_info` (`rezerwacja_id`);

ALTER TABLE `uzytkownik` ADD FOREIGN KEY (`uzytkownik_id`) REFERENCES `panel` (`uzyt_id`);

ALTER TABLE `uzytkownik` ADD FOREIGN KEY (`uzytkownik_id`) REFERENCES `czarna_lista` (`uzytkownik_id`);
