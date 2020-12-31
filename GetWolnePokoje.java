import java.util.*;

public class GetWolnePokoje{
    public Polaczenie a;
    public ArrayList<Integer> lista_wolnych_pokoi;
    public ArrayList<ComboRoomInsert> pokoje_info;
    
    public GetWolnePokoje(Polaczenie p){
        a = p;
        lista_wolnych_pokoi = new ArrayList<>();
        pokoje_info = new ArrayList<>();
    }

    public void dostepnePokoje(int liczba_osob, String od_kiedy, String do_kiedy){
        lista_wolnych_pokoi = a.getWolnePokoje(liczba_osob, od_kiedy, do_kiedy);
    }

    public ArrayList<ComboRoomInsert> dostepnePokojeInfo(){
        for(int i = 0; i < lista_wolnych_pokoi.size(); i++){
            pokoje_info.add(a.getPokojInfo(lista_wolnych_pokoi.get(i)));
        }
        return pokoje_info;
    }
}