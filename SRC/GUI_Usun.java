import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Klasa GUI_Usun
 * Klasa realizuajaca usuwanie wybranego uzytkownika.
 * Funkcjonalnosc ta jest dostepna tylko dla HeadAdmina
 */

public class GUI_Usun{
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
    
    public GUI_Usun(Polaczenie p, GUI_Login mainWindow, int id){
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

        title = new JLabel("Prosze wybrac uzytkownika, ktory ma zostac usuniety z bazy danych:",SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.PLAIN, 17));

        panelUp.add(title);

        uzytkownicy = new JComboBox<>();
        wypelnij();
        panelUp.add(uzytkownicy);


        zatwierdz = new JButton("Zatwierdz"); //Przycisk realizujacy potwierdzenie rzadanego dzialania
        zatwierdz.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(chosen_id != -1){
                    GUI_Rezerwacja_Result m = new GUI_Rezerwacja_Result(a, mainWindow, login_id, a.Usun(chosen_id));
                    mainWindow.frame.getContentPane().removeAll();
                    mainWindow.frame.add(m.rezerwacjaPanel, BorderLayout.CENTER);
                    mainWindow.frame.setTitle("BD PROJEKT - Usun uzytkownika - Rezultat");
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
     * Metoda wypelniajaca dropliste wyboru uzytkownikow dostepnych w bazie danych.
     */

    public void wypelnij(){
        ArrayList<ComboUserInsert> tmp = a.getAllUsers();
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