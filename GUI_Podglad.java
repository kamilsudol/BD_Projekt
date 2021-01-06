import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Klasa GUI_Podlad
 * Klasa ta realizuje podglad wszystkich tabel znajdujacych
 * sie w projektowej bazie danych.
 * Funkcjonalnosc ta jest dostepna tylko dla administratorow
 * hotelu.
 */

public class GUI_Podglad{
    public int login_id;
    public GUI_Login mainWindow;
    public Polaczenie a;
    public JPanel podgladPanel;
    public JButton uzytkownikButton;
    public JButton rezerwacjeButton;
    public JButton uslugiButton;
    public JButton czarna_listaButton;
    public JButton rezygnacjaButton;
    public JButton zakwaterowaniButton;
    public JButton pokojeButton;
    public JButton oplataButton;
    public JButton menuButton;

    /**
     * Konstruktor domyslny, przyjmujacy polaczenie do bazy, odniesienie do okna glownego
     * oraz id obecnie zalogowanego uzytkownika.
     * @param p
     * @param mainWindow
     * @param id
     */

    public GUI_Podglad(Polaczenie p, GUI_Login mainWindow, int id){
        login_id = id;
        this.mainWindow = mainWindow;
        a = p;
        
        podgladPanel = new JPanel();
        podgladPanel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        podgladPanel.setLayout(new GridLayout(0,1));

        uzytkownikButton = new JButton("Tabela \'uzytkownicy\'"); //Przycisk realizujacy podglad tabeli uzytkownikow
        uzytkownikButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Podglad_Uzytkownicy m = new GUI_Podglad_Uzytkownicy(a, mainWindow, login_id);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.getContentPane().add(m.podgladPanel, BorderLayout.CENTER);
                mainWindow.frame.getContentPane().add(m.podgladButtonPanel, BorderLayout.SOUTH);
                mainWindow.frame.setTitle("BD PROJEKT - Podglad - Tabela \'uzytkownicy\'");
                mainWindow.frame.validate();
            }
        });
        podgladPanel.add(uzytkownikButton);

        rezerwacjeButton = new JButton("Tabela \'rezerwacje\'"); //Przyckisk realizujacy podglad tabeli rezerwacji
        rezerwacjeButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Podglad_Rezerwacje m = new GUI_Podglad_Rezerwacje(a, mainWindow, login_id);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.getContentPane().add(m.podgladPanel, BorderLayout.CENTER);
                mainWindow.frame.getContentPane().add(m.podgladButtonPanel, BorderLayout.SOUTH);
                mainWindow.frame.setTitle("BD PROJEKT - Podglad - Tabela \'rezerwacje\'");
                mainWindow.frame.validate();
            }
        });
        podgladPanel.add(rezerwacjeButton);

        oplataButton = new JButton("Tabela \'oplaty\'"); //Przycisk realizujacy podglad tabeli oplat
        oplataButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Podglad_Oplaty m = new GUI_Podglad_Oplaty(a, mainWindow, login_id);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.getContentPane().add(m.podgladPanel, BorderLayout.CENTER);
                mainWindow.frame.getContentPane().add(m.podgladButtonPanel, BorderLayout.SOUTH);
                mainWindow.frame.setTitle("BD PROJEKT - Podglad - Tabela \'oplaty\'");
                mainWindow.frame.validate();
            }
        });
        podgladPanel.add(oplataButton);

        uslugiButton = new JButton("Tabela \'dodatkowe uslugi\'"); //Przycisk realizuacy podglad tabeli dodatkowych uslug
        uslugiButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Podglad_Uslugi m = new GUI_Podglad_Uslugi(a, mainWindow, login_id);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.getContentPane().add(m.podgladPanel, BorderLayout.CENTER);
                mainWindow.frame.getContentPane().add(m.podgladButtonPanel, BorderLayout.SOUTH);
                mainWindow.frame.setTitle("BD PROJEKT - Podglad - Tabela \'dodatkowe uslugi\'");
                mainWindow.frame.validate();
            }
        });
        podgladPanel.add(uslugiButton);

        pokojeButton = new JButton("Tabela \'lista pokoi\'"); //Przycisk realizyjacy podglad tabeli pokoi
        pokojeButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Podglad_Pokoje m = new GUI_Podglad_Pokoje(a, mainWindow, login_id);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.getContentPane().add(m.podgladPanel, BorderLayout.CENTER);
                mainWindow.frame.getContentPane().add(m.podgladButtonPanel, BorderLayout.SOUTH);
                mainWindow.frame.setTitle("BD PROJEKT - Podglad - Tabela \'lista pokoi\'");
                mainWindow.frame.validate();
            }
        });
        podgladPanel.add(pokojeButton);

        zakwaterowaniButton = new JButton("Tabela \'zakwaterowani goscie info\'"); //Przycisk realizujacy podglad tabeli zakwaterowanych gosci
        zakwaterowaniButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Podglad_Zakwaterowani m = new GUI_Podglad_Zakwaterowani(a, mainWindow, login_id);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.getContentPane().add(m.podgladPanel, BorderLayout.CENTER);
                mainWindow.frame.getContentPane().add(m.podgladButtonPanel, BorderLayout.SOUTH);
                mainWindow.frame.setTitle("BD PROJEKT - Podglad - Tabela \'zakwaterowani goscie info\'");
                mainWindow.frame.validate();
            }
        });
        podgladPanel.add(zakwaterowaniButton);

        rezygnacjaButton = new JButton("Tabela \'rezygnacja z rezerwacji info\'"); //Przycisk realizujacy podglad tabeli klientow, ktorzy zrezygnowali z rezerwacji
        rezygnacjaButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Podglad_Zakwaterowani m = new GUI_Podglad_Zakwaterowani(a, mainWindow, login_id);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.getContentPane().add(m.podgladPanel, BorderLayout.CENTER);
                mainWindow.frame.getContentPane().add(m.podgladButtonPanel, BorderLayout.SOUTH);
                mainWindow.frame.setTitle("BD PROJEKT - Podglad - Tabela \'rezygnacja z rezerwacji info\'");
                mainWindow.frame.validate();
            }
        });
        podgladPanel.add(rezygnacjaButton);

        czarna_listaButton = new JButton("Tabela \'czarna lista\'"); //Przycisk realizujacy podglad tabeli czarna lista
        czarna_listaButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Podglad_Blacklist m = new GUI_Podglad_Blacklist(a, mainWindow, login_id);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.getContentPane().add(m.podgladPanel, BorderLayout.CENTER);
                mainWindow.frame.getContentPane().add(m.podgladButtonPanel, BorderLayout.SOUTH);
                mainWindow.frame.setTitle("BD PROJEKT - Podglad - Tabela \'czarna lista\'");
                mainWindow.frame.validate();
            }
        });
        podgladPanel.add(czarna_listaButton);

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