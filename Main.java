public class Main{
    public static void main(String[] args){
        Polaczenie p = new Polaczenie();
        p.BazaUpdate();
        GUI_Login f = new GUI_Login(p);
        // GUI_Login_Wrapper window = new GUI_Login_Wrapper(p, f);
        // window.start();
    }
}