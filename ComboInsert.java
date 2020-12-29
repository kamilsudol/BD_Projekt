public class ComboInsert{
    private String name;
    private int value;
    public ComboInsert(String n, int v){
        name = n;
        value = v;
    }
    @Override
    public String toString(){
        return name;
    }
    public int getVal(){
        return value;
    }
}