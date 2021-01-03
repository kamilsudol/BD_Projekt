public class ComboUserInsert{
    public int id;
    public String imie;
    public String nazwisko;
    public String typ;

    public ComboUserInsert(int i, String im, String naz, String t){
        id = i;
        imie = im;
        nazwisko = naz;
        typ = t;
    }

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