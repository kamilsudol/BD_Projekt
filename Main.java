public class Main{
    public static void main(String[] args){
        Polaczenie p = new Polaczenie();
        String record = p.getStr();
        GUI_Login f = new GUI_Login(p);
        // GUI_Register f = new GUI_Register(p);
        // f.setStr(record);
        // f.login(p);

    }
}