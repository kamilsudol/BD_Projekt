public class ComboRoomInsert{
    private int id;
    private int cena_od_osoby;
    private int pietro;
    private int numer_pokoju;
    private String kategoria;
    private int miejsca;
    private Boolean flag;

    public ComboRoomInsert(String sth){
        flag = false;
        kategoria = sth;
        id = -1;
        cena_od_osoby = 0;
        pietro = 0;
        numer_pokoju = 0;
        miejsca = 0;
    }

    public ComboRoomInsert(){
        flag = true;
        id = 0;
        cena_od_osoby = 0;
        pietro = 0;
        numer_pokoju = 0;
        kategoria = "";
        miejsca = 0;
    }

    public int getId(){return id;}

    public int getCena(){return cena_od_osoby;}

    public int getPietro(){return pietro;}

    public int getNumer(){return numer_pokoju;}

    public String getKategoria(){return kategoria;}

    public int getMiejsca(){return miejsca;}

    public void setNew(int new_id, int new_pietro, int new_numer, String new_kategoria, int new_cena, int new_miejsca){
        id = new_id;
        cena_od_osoby = new_cena;
        pietro = new_pietro;
        numer_pokoju = new_numer;
        kategoria = new_kategoria;
        miejsca = new_miejsca;
    }

    @Override
    public String toString(){
        if(flag){
            return "Kategoria: "+kategoria+"  Liczba miejsc: "+miejsca+"  Numer pokoju: "+numer_pokoju+"  Pietro: "+pietro+"  Cena od osoby: "+cena_od_osoby+"zl";
        }else{
            return kategoria;
        }
    }
}