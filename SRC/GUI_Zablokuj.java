import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Klasa GUI_Zablokuj
 * Klasa realizuajaca blokowanie wybranego uzytkownika
 * wraz z podaniem przyczyny.
 * Funkcjonalnosc ta jest dostepna tylko dla administratorow
 * hotelu.
 */

public class GUI_Zablokuj{
    public int login_id;
    public GUI_Login mainWindow;
    public Polaczenie a;
    public JPanel panelCenter;
    public JPanel panelDown;
    public JPanel panelUp;
    public JTextField powod;
    public JButton zatwierdz;
    public JButton menuButton;
    public JLabel title;
    public JLabel pow;
    public JComboBox<ComboUserInsert> uzytkownicy;
    public int chosen_id;

    /**
     * Konstruktor domyslny, przyjmujacy polaczenie do bazy, odniesienie do okna glownego
     * oraz id obecnie zalogowanego uzytkownika.
     * @param p
     * @param mainWindow
     * @param id
     */
    
    public GUI_Zablokuj(Polaczenie p, GUI_Login mainWindow, int id){
        login_id = id;
        this.mainWindow = mainWindow;
        a = p;

        panelCenter = new JPanel();
        panelDown = new JPanel();
        panelUp = new JPanel();

        panelCenter.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        panelCenter.setLayout(new GridLayout(0,1));

        panelUp.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        panelUp.setLayout(new GridLayout(0,1));

        panelDown.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        panelDown.setLayout(new GridLayout(0,1));

        //Segment realizujacy wybor uzytkownika

        title = new JLabel("Prosze wybrac uzytkownika, ktory ma zostac przeniesiony na czarna liste:",SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.PLAIN, 17));

        panelUp.add(title);

        uzytkownicy = new JComboBox<>();
        wypelnij();
        panelUp.add(uzytkownicy);

        //Segment podania powodu blokady

        pow = new JLabel("Prosze podac powod przeniesienia uzytkownika na czarna liste:",SwingConstants.CENTER);
        pow.setFont(new Font("Arial", Font.PLAIN, 17));
        panelCenter.add(pow);

        powod = new JTextField(255);
        panelCenter.add(powod);

        zatwierdz = new JButton("Zatwierdz"); //Przycisk realizujacy potwierdzenie rzadanego dzialania
        zatwierdz.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(chosen_id != -1){
                    String reason = powod.getText();
                    GUI_Rezerwacja_Result m = new GUI_Rezerwacja_Result(a, mainWindow, login_id, a.Zbanuj(chosen_id, reason));
                    mainWindow.frame.getContentPane().removeAll();
                    mainWindow.frame.add(m.rezerwacjaPanel, BorderLayout.CENTER);
                    mainWindow.frame.setTitle("BD PROJEKT - Zablokuj uzytkownika - Rezultat");
                    mainWindow.frame.validate();
                }
            }
        });
        panelCenter.add(zatwierdz);

        menuButton = new JButton("Powrot do menu"); //Przycisk realizujacy powrot do okna menu.
        menuButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Menu_Admin m = new GUI_Menu_Admin(a, mainWindow, login_id);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.add(m.menuPanel, BorderLayout.CENTER);
                mainWindow.frame.setTitle("BD PROJEKT - Menu - Administrator");
                mainWindow.frame.validate();
            }
        });
        panelDown.add(menuButton);
    }

    /**
     * Metoda wypelniajaca dropliste wyboru uzytkownikow dostepnych do zablokowania.
     */

    public void wypelnij(){
        ArrayList<ComboUserInsert> tmp = a.getUsers();
        for(int i = 0 ; i < tmp.size(); i++){
            uzytkownicy.addItem(tmp.get(i));
        }
        uzytkownicy.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Object item = uzytkownicy.getSelectedItem();
                chosen_id = ((ComboUserInsert)item).id;
            }
        });
    }
}