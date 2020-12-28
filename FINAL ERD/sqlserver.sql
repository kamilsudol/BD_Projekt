CREATE TABLE [panel] (
  [uzyt_id] int PRIMARY KEY,
  [login] nvarchar(255),
  [haslo] nvarchar(255)
)
GO

CREATE TABLE [uzytkownik] (
  [uzytkownik_id] int PRIMARY KEY,
  [imie] nvarchar(255),
  [nazwisko] nvarchar(255),
  [e_mail] text,
  [numer] numeric
)
GO

CREATE TABLE [rezerwacje] (
  [rezerwacja_id] serial PRIMARY KEY,
  [uzytkownik_id] int,
  [pokoj_id] int,
  [data_rezerwacji] date,
  [od_kiedy] date,
  [do_kiedy] date,
  [liczba_dzieci] int,
  [liczba_doroslych] int
)
GO

CREATE TABLE [pokoj] (
  [pokoj_id] int PRIMARY KEY,
  [numer_pokoju] int,
  [pietro] int,
  [kategoria_id] int,
  [liczba_miejsc] int
)
GO

CREATE TABLE [kategoria] (
  [kategoria_id] int PRIMARY KEY,
  [nazwa_kategorii] text,
  [cena_od_osoby] numeric
)
GO

CREATE TABLE [oplata] (
  [oplata_id] serial PRIMARY KEY,
  [rezerwacja_id] int,
  [status_czy_oplacone] boolean,
  [kwota] numeric
)
GO

CREATE TABLE [dodatkowe_uslugi] (
  [dodatkowe_uslugi_id] serial PRIMARY KEY,
  [nazwa_uslugi] text,
  [cena_od_osoby] numeric,
  [rezerwacja_id] int
)
GO

CREATE TABLE [zakwaterowani_goscie_info] (
  [info_id] serial PRIMARY KEY,
  [rezerwacja_id] int,
  [status_czy_zakwaterowany] boolean
)
GO

CREATE TABLE [czarna_lista] (
  [info_id] serial PRIMARY KEY,
  [uzytkownik_id] int,
  [powod] nvarchar(255)
)
GO

ALTER TABLE [rezerwacje] ADD FOREIGN KEY ([uzytkownik_id]) REFERENCES [uzytkownik] ([uzytkownik_id])
GO

ALTER TABLE [rezerwacje] ADD FOREIGN KEY ([rezerwacja_id]) REFERENCES [oplata] ([rezerwacja_id])
GO

ALTER TABLE [dodatkowe_uslugi] ADD FOREIGN KEY ([rezerwacja_id]) REFERENCES [rezerwacje] ([rezerwacja_id])
GO

ALTER TABLE [rezerwacje] ADD FOREIGN KEY ([pokoj_id]) REFERENCES [pokoj] ([pokoj_id])
GO

ALTER TABLE [pokoj] ADD FOREIGN KEY ([kategoria_id]) REFERENCES [kategoria] ([kategoria_id])
GO

ALTER TABLE [rezerwacje] ADD FOREIGN KEY ([rezerwacja_id]) REFERENCES [zakwaterowani_goscie_info] ([rezerwacja_id])
GO

ALTER TABLE [uzytkownik] ADD FOREIGN KEY ([uzytkownik_id]) REFERENCES [panel] ([uzyt_id])
GO

ALTER TABLE [uzytkownik] ADD FOREIGN KEY ([uzytkownik_id]) REFERENCES [czarna_lista] ([uzytkownik_id])
GO
