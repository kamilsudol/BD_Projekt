/**
 *  Pomocnicza klasa ComboInsert dedykowana dla JComboBox,
 *  przechhowujaca odpowiednia wartosc.
 */

public class ComboInsert{
    private String string_name;
    private String name;
    private int value;
    public ComboInsert(String n, int v){
        string_name = n;
        name = n;
        value = v;
    }

    @Override
    public String toString(){
        return string_name;
    }
    public int getVal(){
        return value;
    }
    public String getName(){
        return name;
    }
}