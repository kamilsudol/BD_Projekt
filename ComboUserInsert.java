/**
 * Klasa ComboUserInsert
 * Pomocnicza klasa realizuajaca przechowywanie danych uzytkownikow,
 * dedykowana dla JComboBox.
 */

public class ComboUserInsert{
    public int id;
    public String imie;
    public String nazwisko;
    public String typ;

    /**
     * Konstruktor ustawiajacy podane wartosci.
     * @param i
     * @param im
     * @param naz
     * @param t
     */

    public ComboUserInsert(int i, String im, String naz, String t){
        id = i;
        imie = im;
        nazwisko = naz;
        typ = t;
    }

    /**
     * Konstruktor bezparametrowy.
     */

    public ComboUserInsert(){
        id = -1;
        imie = "";
        nazwisko = "";
        typ = "";
    }

    public String toString(){
        return "Imie:   " +imie+ "   Nazwisko:   "+nazwisko+"   Typ konta:   "+typ;
    }
}