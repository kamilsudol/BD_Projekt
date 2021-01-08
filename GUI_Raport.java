import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Klasa GUI_Raport
 * Klasa ta realizuje podglad raportow zrealizowanych 
 * w projektowej bazie danych.
 * Funkcjonalnosc ta jest dostepna tylko dla administratorow
 * hotelu.
 */

public class GUI_Raport{
    public int login_id;
    public GUI_Login mainWindow;
    public Polaczenie a;
    public JPanel podgladPanel;
    public JButton uzytkownicyButton;
    public JButton rezerwacjeButton;
    public JButton zakwaterowaniButton;
    public JButton pokojeButton;
    public JButton czarnalistaButton;
    public JButton rezygnacjeButton;
    public JButton menuButton;

    /**
     * Konstruktor domyslny, przyjmujacy polaczenie do bazy, odniesienie do okna glownego
     * oraz id obecnie zalogowanego uzytkownika.
     * @param p
     * @param mainWindow
     * @param id
     */

    public GUI_Raport(Polaczenie p, GUI_Login mainWindow, int id){
        login_id = id;
        this.mainWindow = mainWindow;
        a = p;
        
        podgladPanel = new JPanel();
        podgladPanel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        podgladPanel.setLayout(new GridLayout(0,1));

        uzytkownicyButton = new JButton("Raport o uzytkownikach"); //Przycisk realizujacy podglad raportu o uzytkownikach
        uzytkownicyButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Raport_Uzytkownicy m = new GUI_Raport_Uzytkownicy(a, mainWindow, login_id);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.getContentPane().add(m.podgladUpPanel, BorderLayout.NORTH);
                mainWindow.frame.getContentPane().add(m.podgladPanel, BorderLayout.CENTER);
                mainWindow.frame.getContentPane().add(m.podgladButtonPanel, BorderLayout.SOUTH);
                mainWindow.frame.setTitle("BD PROJEKT - Podglad raportow - Raport o uzytkownikach");
                mainWindow.frame.validate();
            }
        });
        podgladPanel.add(uzytkownicyButton);

//        rezerwacjeButton = new JButton("Raport o rezerwacjach"); //Przyckisk realizujacy podglad raportu o rezerwacjach
//        rezerwacjeButton.addActionListener(new ActionListener(){
//            public void actionPerformed(ActionEvent e){
//                GUI_Raport_Rezerwacje m = new GUI_Raport_Rezerwacje(a, mainWindow, login_id);
//                mainWindow.frame.getContentPane().removeAll();
//                mainWindow.frame.getContentPane().add(m.podgladUpPanel, BorderLayout.NORTH);
//                mainWindow.frame.getContentPane().add(m.podgladPanel, BorderLayout.CENTER);
//                mainWindow.frame.getContentPane().add(m.podgladButtonPanel, BorderLayout.SOUTH);
//                mainWindow.frame.setTitle("BD PROJEKT - Podglad raportow - Raport o rezerwacjach");
//                mainWindow.frame.validate();
//            }
//        });
//        podgladPanel.add(rezerwacjeButton);

        pokojeButton = new JButton("Raport o pokojach"); //Przycisk realizyjacy podglad raportu o pokojach
        pokojeButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Raport_Pokoje m = new GUI_Raport_Pokoje(a, mainWindow, login_id);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.getContentPane().add(m.podgladUpPanel, BorderLayout.NORTH);
                mainWindow.frame.getContentPane().add(m.podgladPanel, BorderLayout.CENTER);
                mainWindow.frame.getContentPane().add(m.podgladButtonPanel, BorderLayout.SOUTH);
                mainWindow.frame.setTitle("BD PROJEKT - Podglad raportow - Raport o pokojach");
                mainWindow.frame.validate();
            }
        });
        podgladPanel.add(pokojeButton);

//        zakwaterowaniButton = new JButton("Raport o zakwaterowanych gosciach"); //Przycisk realizujacy podglad raportu o zakwaterowanych gosciach
//        zakwaterowaniButton.addActionListener(new ActionListener(){
//            public void actionPerformed(ActionEvent e){
//                GUI_Raport_Zakwaterowani m = new GUI_Raport_Zakwaterowani(a, mainWindow, login_id);
//                mainWindow.frame.getContentPane().removeAll();
//                mainWindow.frame.getContentPane().add(m.podgladUpPanel, BorderLayout.NORTH);
//                mainWindow.frame.getContentPane().add(m.podgladPanel, BorderLayout.CENTER);
//                mainWindow.frame.getContentPane().add(m.podgladButtonPanel, BorderLayout.SOUTH);
//                mainWindow.frame.setTitle("BD PROJEKT - Podglad raportow - Raport o zakwaterowanych gosciach");
//                mainWindow.frame.validate();
//            }
//        });
//        podgladPanel.add(zakwaterowaniButton);
//
//        rezygnacjeButton = new JButton("Raport o rezygnacjach z rezerwacji"); //Przycisk realizujacy podglad raportu o rezygnacjach
//        rezygnacjeButton.addActionListener(new ActionListener(){
//            public void actionPerformed(ActionEvent e){
//                GUI_Raport_Rezygnacje m = new GUI_Raport_Rezygnacje(a, mainWindow, login_id);
//                mainWindow.frame.getContentPane().removeAll();
//                mainWindow.frame.getContentPane().add(m.podgladUpPanel, BorderLayout.NORTH);
//                mainWindow.frame.getContentPane().add(m.podgladPanel, BorderLayout.CENTER);
//                mainWindow.frame.getContentPane().add(m.podgladButtonPanel, BorderLayout.SOUTH);
//                mainWindow.frame.setTitle("BD PROJEKT - Podglad raportow - Raport o rezygnacjach z rezerwacji");
//                mainWindow.frame.validate();
//            }
//        });
//        podgladPanel.add(rezygnacjeButton);
//
//        czarnalistaButton = new JButton("Raport o zablokowanych uzytkownikach"); //Przycisk realizujacy podglad raportu o zablokowanych uzytkownikach
//        czarnalistaButton.addActionListener(new ActionListener(){
//            public void actionPerformed(ActionEvent e){
//                GUI_Raport_Zablokowani m = new GUI_Raport_Zablokowani(a, mainWindow, login_id);
//                mainWindow.frame.getContentPane().removeAll();
//                mainWindow.frame.getContentPane().add(m.podgladUpPanel, BorderLayout.NORTH);
//                mainWindow.frame.getContentPane().add(m.podgladPanel, BorderLayout.CENTER);
//                mainWindow.frame.getContentPane().add(m.podgladButtonPanel, BorderLayout.SOUTH);
//                mainWindow.frame.setTitle("BD PROJEKT - Podglad raportow - Raport o zablokowanych uzytkownikach");
//                mainWindow.frame.validate();
//            }
//        });
//        podgladPanel.add(czarnalistaButton);

        menuButton = new JButton("Powrot do menu"); //Przycisk realizujacy powrot do okna menu
        menuButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Menu_Admin m = new GUI_Menu_Admin(a, mainWindow, login_id);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.add(m.menuPanel, BorderLayout.CENTER);
                mainWindow.frame.setTitle("BD PROJEKT - Menu - Administrator");
                mainWindow.frame.validate();
            }
        });
        podgladPanel.add(menuButton);

    }
}