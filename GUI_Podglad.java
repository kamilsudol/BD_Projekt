import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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

    public GUI_Podglad(Polaczenie p, GUI_Login mainWindow, int id){
        login_id = id;
        this.mainWindow = mainWindow;
        a = p;
        
        podgladPanel = new JPanel();
        podgladPanel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        podgladPanel.setLayout(new GridLayout(0,1));

        uzytkownikButton = new JButton("Tabela \'uzytkownicy\'");
        uzytkownikButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Podglad_Uzytkownicy m = new GUI_Podglad_Uzytkownicy(a, mainWindow, login_id);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.getContentPane().add(m.podgladPanel, BorderLayout.CENTER);
                // mainWindow.frame.getContentPane().add(new JSeparator(), BorderLayout.CENTER);
                mainWindow.frame.getContentPane().add(m.podgladButtonPanel, BorderLayout.SOUTH);
                mainWindow.frame.setTitle("BD PROJEKT - Podglad - Tabela \'uzytkownicy\'");
                mainWindow.frame.validate();
            }
        });
        podgladPanel.add(uzytkownikButton);

        rezerwacjeButton = new JButton("Tabela \'rezerwacje\'");
        rezerwacjeButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Podglad_Rezerwacje m = new GUI_Podglad_Rezerwacje(a, mainWindow, login_id);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.getContentPane().add(m.podgladPanel, BorderLayout.CENTER);
                // mainWindow.frame.getContentPane().add(new JSeparator(), BorderLayout.CENTER);
                mainWindow.frame.getContentPane().add(m.podgladButtonPanel, BorderLayout.SOUTH);
                mainWindow.frame.setTitle("BD PROJEKT - Podglad - Tabela \'rezerwacje\'");
                mainWindow.frame.validate();
            }
        });
        podgladPanel.add(rezerwacjeButton);

        oplataButton = new JButton("Tabela \'oplaty\'");
        oplataButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Podglad_Oplaty m = new GUI_Podglad_Oplaty(a, mainWindow, login_id);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.getContentPane().add(m.podgladPanel, BorderLayout.CENTER);
                // mainWindow.frame.getContentPane().add(new JSeparator(), BorderLayout.CENTER);
                mainWindow.frame.getContentPane().add(m.podgladButtonPanel, BorderLayout.SOUTH);
                mainWindow.frame.setTitle("BD PROJEKT - Podglad - Tabela \'oplaty\'");
                mainWindow.frame.validate();
            }
        });
        podgladPanel.add(oplataButton);

        uslugiButton = new JButton("Tabela \'dodatkowe uslugi\'");
        uslugiButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Podglad_Uslugi m = new GUI_Podglad_Uslugi(a, mainWindow, login_id);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.getContentPane().add(m.podgladPanel, BorderLayout.CENTER);
                // mainWindow.frame.getContentPane().add(new JSeparator(), BorderLayout.CENTER);
                mainWindow.frame.getContentPane().add(m.podgladButtonPanel, BorderLayout.SOUTH);
                mainWindow.frame.setTitle("BD PROJEKT - Podglad - Tabela \'dodatkowe uslugi\'");
                mainWindow.frame.validate();
            }
        });
        podgladPanel.add(uslugiButton);

        pokojeButton = new JButton("Tabela \'lista pokoi\'");
        pokojeButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Podglad_Pokoje m = new GUI_Podglad_Pokoje(a, mainWindow, login_id);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.getContentPane().add(m.podgladPanel, BorderLayout.CENTER);
                // mainWindow.frame.getContentPane().add(new JSeparator(), BorderLayout.CENTER);
                mainWindow.frame.getContentPane().add(m.podgladButtonPanel, BorderLayout.SOUTH);
                mainWindow.frame.setTitle("BD PROJEKT - Podglad - Tabela \'lista pokoi\'");
                mainWindow.frame.validate();
            }
        });
        podgladPanel.add(pokojeButton);

        zakwaterowaniButton = new JButton("Tabela \'zakwaterowani goscie info\'");
        zakwaterowaniButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Podglad_Zakwaterowani m = new GUI_Podglad_Zakwaterowani(a, mainWindow, login_id);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.getContentPane().add(m.podgladPanel, BorderLayout.CENTER);
                // mainWindow.frame.getContentPane().add(new JSeparator(), BorderLayout.CENTER);
                mainWindow.frame.getContentPane().add(m.podgladButtonPanel, BorderLayout.SOUTH);
                mainWindow.frame.setTitle("BD PROJEKT - Podglad - Tabela \'zakwaterowani goscie info\'");
                mainWindow.frame.validate();
            }
        });
        podgladPanel.add(zakwaterowaniButton);

        rezygnacjaButton = new JButton("Tabela \'rezygnacja z rezerwacji info\'");
        rezygnacjaButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Podglad_Zakwaterowani m = new GUI_Podglad_Zakwaterowani(a, mainWindow, login_id);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.getContentPane().add(m.podgladPanel, BorderLayout.CENTER);
                // mainWindow.frame.getContentPane().add(new JSeparator(), BorderLayout.CENTER);
                mainWindow.frame.getContentPane().add(m.podgladButtonPanel, BorderLayout.SOUTH);
                mainWindow.frame.setTitle("BD PROJEKT - Podglad - Tabela \'rezygnacja z rezerwacji info\'");
                mainWindow.frame.validate();
            }
        });
        podgladPanel.add(rezygnacjaButton);

        czarna_listaButton = new JButton("Tabela \'czarna lista\'");
        czarna_listaButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Podglad_Blacklist m = new GUI_Podglad_Blacklist(a, mainWindow, login_id);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.getContentPane().add(m.podgladPanel, BorderLayout.CENTER);
                // mainWindow.frame.getContentPane().add(new JSeparator(), BorderLayout.CENTER);
                mainWindow.frame.getContentPane().add(m.podgladButtonPanel, BorderLayout.SOUTH);
                mainWindow.frame.setTitle("BD PROJEKT - Podglad - Tabela \'czarna lista\'");
                mainWindow.frame.validate();
            }
        });
        podgladPanel.add(czarna_listaButton);

        menuButton = new JButton("Powrot do menu");
        menuButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Menu m = new GUI_Menu(a, mainWindow, login_id);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.add(m.menuPanel, BorderLayout.CENTER);
                mainWindow.frame.setTitle("BD PROJEKT - Menu");
                mainWindow.frame.validate();
            }
        });
        podgladPanel.add(menuButton);

    }

}