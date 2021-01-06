import java.util.*;

/**
 * Klasa GetWolnePokoje
 * Pomocnicza klasa przechowujaca informacje
 * o dostepnych pokojach na wybrany termin;
 */

public class GetWolnePokoje{
    public Polaczenie a;
    public ArrayList<Integer> lista_wolnych_pokoi;
    public ArrayList<ComboRoomInsert> pokoje_info;

    /**
     * Konstruktor domyslny przyjmujacy polaczenie do bazy.
     * @param p
     */

    public GetWolnePokoje(Polaczenie p){
        a = p;
        lista_wolnych_pokoi = new ArrayList<>();
        pokoje_info = new ArrayList<>();
    }

    /**
     * Metoda ustalajaca liste wolnych pokoi
     * @param liczba_osob
     * @param od_kiedy
     * @param do_kiedy
     */

    public void dostepnePokoje(int liczba_osob, String od_kiedy, String do_kiedy){
        lista_wolnych_pokoi = a.getWolnePokoje(liczba_osob, od_kiedy, do_kiedy);
    }

    /**
     * Metoda przepisujaca informacje do klasy ComboRoomInsert,
     * dedykowana dla JComboBox
     * @return
     */

    public ArrayList<ComboRoomInsert> dostepnePokojeInfo(){
        for(int i = 0; i < lista_wolnych_pokoi.size(); i++){
            pokoje_info.add(a.getPokojInfo(lista_wolnych_pokoi.get(i)));
        }
        return pokoje_info;
    }
}