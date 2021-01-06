/**
 * Klasa Main
 * Start projektu
 */

public class Main{
    public static void main(String[] args){
        Polaczenie p = new Polaczenie(); //Nawiazanie polaczenia z baza danych.
        p.BazaUpdate(); //Aktualizacja danych bazy przy kazdym uruchomieniu aplikacji.
        GUI_Login f = new GUI_Login(p);  //Uruchomienia glownego okna aplikacji.
    }
}