public class ComboRoomInsert{
    private int id;
    private int cena_od_osoby;
    public ComboRoomInsert(){
        id = 0;
        cena_od_osoby = 0;
    }

    public int getId(){return id;}

    public int getCena(){return cena_od_osoby;}

    @Override
    public String toString(){return "";}
}