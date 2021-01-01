public class GetUslugi{
    private String opis;
    private int cena;
    private Boolean visible;
    public GetUslugi(String o, int c){
        opis = o;
        cena = c;
        visible = false;
    }
    void setVis(Boolean f){visible = f;}
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