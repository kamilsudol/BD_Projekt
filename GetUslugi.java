/**
 * Klasa GetUslugi
 * Pomocnicza klasa przechowujaca informacje o dodatkowych uslugach.
 */

public class GetUslugi{
    private String opis;
    private int cena;
    private Boolean visible;

    /**
     * Konstruktor domyslny przyjmujacy opis uslug oraz ich cene.
     * @param o
     * @param c
     */

    public GetUslugi(String o, int c){
        opis = o;
        cena = c;
        visible = false;
    }

    /**
     * Pomocniczy setter ustawiajacy widocznosc obiektu.
     * @param f
     */

    void setVis(Boolean f){visible = f;}

    /**
     * Getter ceny
     * @return
     */

    int getCena(){
        if(visible){
            return cena;
        }else{
            return 0;
        }
    }

    public String toString(){
        if(visible){
            return opis;
        }else{
            return "";
        }
    }
}